package com.beaconfire.dao.hibernate;

import com.beaconfire.dao.AdminApplicationDao;
import com.beaconfire.domain.hibernate.ApplicationHibernate;
import com.beaconfire.domain.hibernate.StudentClassHibernate;
import com.beaconfire.domain.hibernate.WebRegClassHibernate;
import com.beaconfire.domain.jdbc.AdminApplication;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Repository("adminApplicationDaoHibernateImpl")
public class AdminApplicationDaoHibernateImpl implements AdminApplicationDao {

    @Autowired
    protected SessionFactory sessionFactory;

    @Override
    public List<AdminApplication> getAllApplications() {
        System.out.println("8sf8 Hibernate: getAllApplications");
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<ApplicationHibernate> cq = cb.createQuery(ApplicationHibernate.class);
            Root<ApplicationHibernate> root = cq.from(ApplicationHibernate.class);

            // Add a where clause to filter rows with the status 'pending'
            cq.where(cb.equal(root.get("status"), "pending"));

            // Order by creation_time
            cq.orderBy(cb.desc(root.get("creation_time")));

            List<ApplicationHibernate> applications = session.createQuery(cq).getResultList();

            List<AdminApplication> results = new ArrayList<>();

            for(ApplicationHibernate a: applications){
                AdminApplication adminApplication = AdminApplication.builder()
                        .first_name(a.getStudentHibernate().getFirst_name())
                        .last_name(a.getStudentHibernate().getLast_name())
                        .email(a.getStudentHibernate().getEmail())
                        .course_name(a.getWebRegClassHibernate().getCourseHibernate().getName())
                        .class_id(a.getWebRegClassHibernate().getId())
                        .semester_name(a.getWebRegClassHibernate().getSemesterHibernate().getName())
                        .request(a.getRequest())
                        .creation_time(a.getCreation_time().toLocalTime())
                        .status(a.getStatus())
                        .student_id(a.getStudentHibernate().getId())
                        .application_id(a.getId())
                        .build();
                results.add(adminApplication);
            }
            return results.size() == 0 ? null : results;
        }
    }




    @Override
    public void updateApplicationStatus(Integer applicationId, String status, String description) {
        System.out.println("88888 Hibernate: updateApplicationStatus");

        Session session = null;
        Transaction tx = null;
        try {
            session = sessionFactory.openSession();
            tx = session.beginTransaction();

            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<ApplicationHibernate> criteriaQuery = criteriaBuilder.createQuery(ApplicationHibernate.class);
            Root<ApplicationHibernate> applicationRoot = criteriaQuery.from(ApplicationHibernate.class);
            criteriaQuery.select(applicationRoot).where(criteriaBuilder.equal(applicationRoot.get("id"), applicationId));

            ApplicationHibernate application = session.createQuery(criteriaQuery).uniqueResult();

            if (application != null) {
                application.setStatus(status);
                application.setFeedback(description);
                session.update(application);
            }

            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }


    @Override
    public String getRequestByApplicationId(Integer applicationId) {
        System.out.println("88888 Hibernate: getRequestByApplicationId");
        try (Session session = sessionFactory.openSession()) {
            ApplicationHibernate application = session.get(ApplicationHibernate.class, applicationId);
            if (application != null) {
                return application.getRequest();
            }
        }
        return null;
    }


    @Override
    public void addStudentToClassByApplicationId(Integer applicationId) {
        System.out.println("88888 Hibernate: addStudentToClassByApplicationId");

        try (Session session = sessionFactory.openSession()) {
            ApplicationHibernate application = session.get(ApplicationHibernate.class, applicationId);
            if (application != null) {
                Transaction transaction = null;
                try {
                    transaction = session.beginTransaction();

                    StudentClassHibernate studentClass = new StudentClassHibernate();
                    studentClass.setStudentHibernate(application.getStudentHibernate());
                    studentClass.setWebRegClassHibernate(application.getWebRegClassHibernate());
                    studentClass.setStatus("ongoing");

                    session.save(studentClass);

                    WebRegClassHibernate webRegClass = application.getWebRegClassHibernate();
                    webRegClass.setEnrollment_num(webRegClass.getEnrollment_num() + 1);
                    session.update(webRegClass);

                    transaction.commit();
                } catch (Exception e) {
                    if (transaction != null) {
                        transaction.rollback();
                    }
                    e.printStackTrace();
                }
            }
        }
    }


    @Override
    public void withdrawStudentFromClassByApplicationId(Integer applicationId) {
        System.out.println("88888 Hibernate: withdrawStudentFromClassByApplicationId");
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<ApplicationHibernate> cq = cb.createQuery(ApplicationHibernate.class);
            Root<ApplicationHibernate> root = cq.from(ApplicationHibernate.class);
            cq.select(root).where(cb.equal(root.get("id"), applicationId));
            ApplicationHibernate application = session.createQuery(cq).uniqueResult();

            if (application != null) {
                Transaction transaction = null;
                try {
                    transaction = session.beginTransaction();

                    int studentId = application.getStudentHibernate().getId();
                    int classId = application.getWebRegClassHibernate().getId();

                    // Update the status of the student in the class to 'withdraw'
                    CriteriaUpdate<StudentClassHibernate> updateStudentClass = cb.createCriteriaUpdate(StudentClassHibernate.class);
                    Root<StudentClassHibernate> updateRoot = updateStudentClass.from(StudentClassHibernate.class);
                    updateStudentClass.set("status", "withdraw")
                            .where(cb.equal(updateRoot.get("studentHibernate").get("id"), studentId),
                                    cb.equal(updateRoot.get("webRegClassHibernate").get("id"), classId));
                    session.createQuery(updateStudentClass).executeUpdate();

                    // Decrement the enrollment number for the class
                    WebRegClassHibernate webRegClass = application.getWebRegClassHibernate();
                    webRegClass.setEnrollment_num(webRegClass.getEnrollment_num() - 1);
                    session.update(webRegClass);

                    transaction.commit();
                } catch (Exception e) {
                    if (transaction != null) {
                        transaction.rollback();
                    }
                    e.printStackTrace();
                }
            }
        }
    }

}
