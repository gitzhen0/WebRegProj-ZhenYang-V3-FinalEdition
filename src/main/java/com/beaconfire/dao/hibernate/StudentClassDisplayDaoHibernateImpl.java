package com.beaconfire.dao.hibernate;

import com.beaconfire.dao.StudentClassDisplayDao;
import com.beaconfire.domain.hibernate.StudentClassHibernate;
import com.beaconfire.domain.hibernate.StudentHibernate;
import com.beaconfire.domain.hibernate.WebRegClassHibernate;
import com.beaconfire.domain.jdbc.AdminClassToStudentDisplay;
import com.beaconfire.domain.jdbc.AdminHomeDisplay;
import com.beaconfire.domain.jdbc.StudentClassDisplay;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Repository("studentClassDisplayDaoHibernateImpl")
public class StudentClassDisplayDaoHibernateImpl implements StudentClassDisplayDao {

    @Autowired
    protected SessionFactory sessionFactory;

    @Override
    public List<StudentClassDisplay> findPaginated(int page, int limit, HttpSession httpsession) {
        int offset = (page - 1) * limit;
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();


//
//            // Create a CriteriaQuery for WebRegClassHibernate class
//            CriteriaQuery<WebRegClassHibernate> cq = cb.createQuery(WebRegClassHibernate.class);
//            Root<StudentClassHibernate> studentClassRoot = cq.from(StudentClassHibernate.class);
            // Create a CriteriaQuery for WebRegClassHibernate class
            CriteriaQuery<StudentClassHibernate> cq = cb.createQuery(StudentClassHibernate.class);
            Root<StudentClassHibernate> studentClassRoot = cq.from(StudentClassHibernate.class);

            // Set the condition for the studentId
            cq.where(cb.equal(studentClassRoot.get("studentHibernate").get("id"), httpsession.getAttribute("userId")));



            // Create a query, execute it, and return the result
            Query<StudentClassHibernate> query = session.createQuery(cq);




            query.setFirstResult(offset);
            query.setMaxResults(limit);
            List<StudentClassHibernate> tmpList = query.getResultList();

            List<StudentClassDisplay> results = new ArrayList<>();

            for(StudentClassHibernate c: tmpList){
                StudentClassDisplay tmp = StudentClassDisplay.builder()
                        .student_id((Integer) httpsession.getAttribute("userId"))
                        .class_id(c.getId())
                        .course_name(c.getWebRegClassHibernate().getCourseHibernate().getName())
                        .course_code(c.getWebRegClassHibernate().getCourseHibernate().getCode())
                        .department_name(c.getWebRegClassHibernate().getCourseHibernate().getDepartmentHibernate().getName())
                        .school_name(c.getWebRegClassHibernate().getCourseHibernate().getDepartmentHibernate().getSchool())
                        .semester_name(c.getWebRegClassHibernate().getSemesterHibernate().getName())
                        .status(c.getStatus())
                        .build();
                results.add(tmp);
            }
            return results;
        }
    }

    @Override
    public int getTotalPages(int limit, HttpSession httpsession) {
        try (Session session = sessionFactory.openSession()) {
            // Get CriteriaBuilder instance from the session
            CriteriaBuilder cb = session.getCriteriaBuilder();

            // Create a CriteriaQuery for counting the records
            CriteriaQuery<Long> cq = cb.createQuery(Long.class);
            Root<StudentClassHibernate> studentClassRoot = cq.from(StudentClassHibernate.class);

            // Join StudentClassHibernate with WebRegClassHibernate
            Join<StudentClassHibernate, WebRegClassHibernate> classJoin = studentClassRoot.join("webRegClassHibernate");

            // Set the condition for the studentId
            cq.where(cb.equal(studentClassRoot.get("studentHibernate").get("id"), httpsession.getAttribute("userId")));

            // Select the count of the WebRegClassHibernate objects
            cq.select(cb.count(classJoin));

            // Create a query, execute it, and return the result
            Query<Long> query = session.createQuery(cq);
            Integer classCount = query.getSingleResult().intValue();
            return (int) Math.ceil((double) classCount / limit);
        }
    }

    @Override
    public List<AdminClassToStudentDisplay> findStudentsByClassId(int classId) {
        try (Session session = sessionFactory.openSession()) {
            // Get CriteriaBuilder instance from the session
            CriteriaBuilder cb = session.getCriteriaBuilder();

            // Create a CriteriaQuery for StudentHibernate class
            CriteriaQuery<StudentHibernate> cq = cb.createQuery(StudentHibernate.class);
            Root<StudentClassHibernate> studentClassRoot = cq.from(StudentClassHibernate.class);

            // Join StudentClassHibernate with StudentHibernate
            Join<StudentClassHibernate, StudentHibernate> studentJoin = studentClassRoot.join("studentHibernate");

            // Set the condition for the classId
            cq.where(cb.equal(studentClassRoot.get("webRegClassHibernate").get("id"), classId));

            // Select the StudentHibernate objects
            cq.select(studentJoin);

            // Create a query, execute it, and return the result
            Query<StudentHibernate> query = session.createQuery(cq);
            List<StudentHibernate> students = query.getResultList();

            List<AdminClassToStudentDisplay> results = new ArrayList<>();

            for(StudentHibernate s: students){
                AdminClassToStudentDisplay display = AdminClassToStudentDisplay.builder()
                        .student_id(s.getId())
                        .first_name(s.getFirst_name())
                        .last_name(s.getLast_name())
                        .email(s.getEmail())
                        .department_name(s.getDepartmentHibernate().getName())
                        .school_name(s.getDepartmentHibernate().getSchool())
                        .build();
                results.add(display);
            }



            return results;
        }
    }
}
