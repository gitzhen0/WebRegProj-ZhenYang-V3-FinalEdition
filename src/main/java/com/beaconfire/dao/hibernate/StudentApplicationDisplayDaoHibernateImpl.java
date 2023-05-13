package com.beaconfire.dao.hibernate;

import com.beaconfire.dao.StudentApplicationDisplayDao;
import com.beaconfire.domain.hibernate.ApplicationHibernate;
import com.beaconfire.domain.hibernate.StudentClassHibernate;
import com.beaconfire.domain.hibernate.StudentHibernate;
import com.beaconfire.domain.hibernate.WebRegClassHibernate;
import com.beaconfire.domain.jdbc.StudentApplicationDisplay;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Repository("studentApplicationDisplayDaoHibernateImpl")
public class StudentApplicationDisplayDaoHibernateImpl implements StudentApplicationDisplayDao {

    @Autowired
    protected SessionFactory sessionFactory;

    @Override
    public List<StudentApplicationDisplay> getApplicationsByStudentId(int student_id) {
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<ApplicationHibernate> cq = cb.createQuery(ApplicationHibernate.class);
            Root<ApplicationHibernate> applicationRoot = cq.from(ApplicationHibernate.class);

            cq.where(cb.equal(applicationRoot.get("studentHibernate").get("id"), student_id));

            Query<ApplicationHibernate> query = session.createQuery(cq);
            List<ApplicationHibernate> applications = query.getResultList();

            // Convert the list of StudentApplicationHibernate to the list of StudentApplicationDisplay objects
            List<StudentApplicationDisplay> results = new ArrayList<>();

            for(ApplicationHibernate ah: applications){
                StudentApplicationDisplay sad = StudentApplicationDisplay.builder()
                        .application_id(ah.getId())
                        .student_id(ah.getStudentHibernate().getId())
                        .class_id(ah.getWebRegClassHibernate().getId())
                        .course_code(ah.getWebRegClassHibernate().getCourseHibernate().getCode())
                        .course_name(ah.getWebRegClassHibernate().getCourseHibernate().getName())
                        .semester_name(ah.getWebRegClassHibernate().getSemesterHibernate().getName())
                        .creation_time(ah.getCreation_time().toLocalTime())
                        .request(ah.getRequest())
                        .status(ah.getStatus())
                        .feedback(ah.getFeedback())
                        .build();
                results.add(sad);
            }

            return results;
        }
    }

    @Override
    public void addNewApplication(int studentId, int classId, String request) {
        try (Session session = sessionFactory.openSession()) {
            StudentHibernate student = session.get(StudentHibernate.class, studentId);
            WebRegClassHibernate webRegClass = session.get(WebRegClassHibernate.class, classId);

            ApplicationHibernate newApplication = new ApplicationHibernate();
            newApplication.setStudentHibernate(student);
            newApplication.setWebRegClassHibernate(webRegClass);
            newApplication.setRequest(request);
            newApplication.setStatus("pending");
            newApplication.setCreation_time(LocalDateTime.now());

            session.beginTransaction();
            session.save(newApplication);
            session.getTransaction().commit();
        }
    }

    @Override
    public void removeApplication(int studentId, int classId) {
        try (Session session = sessionFactory.openSession()) {
            // Create a query to find the StudentApplicationHibernate instance by studentId and classId
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<ApplicationHibernate> cq = cb.createQuery(ApplicationHibernate.class);
            Root<ApplicationHibernate> applicationRoot = cq.from(ApplicationHibernate.class);
            cq.where(
                    cb.and(
                            cb.equal(applicationRoot.get("studentHibernate").get("id"), studentId),
                            cb.equal(applicationRoot.get("webRegClassHibernate").get("id"), classId)
                    )
            );

            // Execute the query to get the instance
            TypedQuery<ApplicationHibernate> query = session.createQuery(cq);
            ApplicationHibernate application = query.getSingleResult();

            // Remove the instance
            if (application != null) {
                session.beginTransaction();
                session.delete(application);
                session.getTransaction().commit();
            }
        }
    }

    @Override
    public void removeApplicationById(int applicationId) {
        try (Session session = sessionFactory.openSession()) {
            // Get the StudentApplicationHibernate instance by applicationId
            ApplicationHibernate application = session.get(ApplicationHibernate.class, applicationId);

            // Remove the instance
            if (application != null) {
                session.beginTransaction();
                session.delete(application);
                session.getTransaction().commit();
            }
        }
    }

    @Override
    public void removeStudentFromClass(int studentId, int classId) {
        try (Session session = sessionFactory.openSession()) {
            // Create a query to find the StudentClassHibernate instance by studentId and classId
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaDelete<StudentClassHibernate> delete = cb.createCriteriaDelete(StudentClassHibernate.class);
            Root<StudentClassHibernate> root = delete.from(StudentClassHibernate.class);
            delete.where(cb.and(
                    cb.equal(root.get("studentHibernate").get("id"), studentId),
                    cb.equal(root.get("webRegClassHibernate").get("id"), classId)
            ));

            // Execute the delete query
            session.beginTransaction();
            session.createQuery(delete).executeUpdate();
            session.getTransaction().commit();
        }
    }
}
