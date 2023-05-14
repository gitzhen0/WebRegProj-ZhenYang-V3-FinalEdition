package com.beaconfire.dao.hibernate;

import com.beaconfire.dao.DAOinterface.StudentApplicationDisplayDao;
import com.beaconfire.domain.DTO.ClassApplicationResponse;
import com.beaconfire.domain.hibernate.*;
import com.beaconfire.domain.jdbc.SingleApplication;
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
    public ClassApplicationResponse addNewApplication(int studentId, int classId, String request) {
        ClassApplicationResponse car = new ClassApplicationResponse();
        try (Session session = sessionFactory.openSession()) {
            StudentHibernate student = session.get(StudentHibernate.class, studentId);
            WebRegClassHibernate webRegClass = session.get(WebRegClassHibernate.class, classId);

            LocalDateTime now = LocalDateTime.now();

            ApplicationHibernate newApplication = new ApplicationHibernate();
            newApplication.setStudentHibernate(student);
            newApplication.setWebRegClassHibernate(webRegClass);
            newApplication.setRequest(request);
            newApplication.setStatus("pending");
            newApplication.setCreation_time(now);

            session.beginTransaction();
            session.save(newApplication);
            session.getTransaction().commit();

            car.setCourseName(webRegClass.getCourseHibernate().getName());
            car.setCourseCode(webRegClass.getCourseHibernate().getCode());
            car.setCreationTime(now);
            car.setSemester(webRegClass.getSemesterHibernate().getName());
            car.setRequest(request);
            car.setStatus("pending");

        }
        return car;
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

    @Override
    public boolean applicationExists(int studentId, int classId, String request) {
        try (Session session = sessionFactory.openSession()) {
            // Create a query to find the ApplicationHibernate instance by studentId, classId and action
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<ApplicationHibernate> cq = cb.createQuery(ApplicationHibernate.class);
            Root<ApplicationHibernate> applicationRoot = cq.from(ApplicationHibernate.class);
            cq.where(
                    cb.and(
                            cb.equal(applicationRoot.get("studentHibernate").get("id"), studentId),
                            cb.equal(applicationRoot.get("webRegClassHibernate").get("id"), classId),
                            cb.equal(applicationRoot.get("action"), request)
                    )
            );

            // Execute the query to get the instance
            TypedQuery<ApplicationHibernate> query = session.createQuery(cq);
            ApplicationHibernate application = query.getResultStream().findFirst().orElse(null);

            // Return whether the application exists
            return application != null;
        }
    }

    @Override
    public String[] validToRemove(int studentId, int applicationId) {
        try (Session session = sessionFactory.openSession()) {
            // Load the ApplicationHibernate instance by applicationId
            ApplicationHibernate application = session.get(ApplicationHibernate.class, applicationId);

            String status = application.getStatus();

            String[] message = new String[3];
            message[0] = "0 - application is not found";
            message[1] = "1 - this application doesn't belong to the current student";
            message[2] = "2 - application status is not pending";

            if(application != null){
                message[0] = "";
            }

            if(application.getStudentHibernate().getId() == studentId){
                message[1] = "";
            }

            if(status.equals("pending")){
                message[2] = "";
            }


            return message;
        }

    }

    @Override
    public SingleApplication getApplicationById(int id) {
        SingleApplication result = new SingleApplication();
        try (Session session = sessionFactory.openSession()) {

            ApplicationHibernate application = session.get(ApplicationHibernate.class, id);

            result.setStudent(application.getStudentHibernate().getFirst_name() + " " + application.getStudentHibernate().getLast_name());
            result.setFeedback(application.getFeedback());
            result.setCourse_name(application.getWebRegClassHibernate().getCourseHibernate().getName());
            result.setRequest(application.getRequest());
            result.setSemester_name(application.getWebRegClassHibernate().getSemesterHibernate().getName());
            result.setCreation_time(application.getCreation_time());
            result.setStatus(application.getStatus());

        }
        return result;
    }

    public Boolean applicationExistsById(int id){
        try (Session session = sessionFactory.openSession()) {
            ApplicationHibernate hibernate = session.get(ApplicationHibernate.class, id);
            return hibernate != null;
        }
    }

    @Override
    public Boolean applicationIsPendingById(int applicationId) {
        try (Session session = sessionFactory.openSession()) {
            ApplicationHibernate hibernate = session.get(ApplicationHibernate.class, applicationId);
            return hibernate.getStatus().equals("pending");
        }
    }


}
