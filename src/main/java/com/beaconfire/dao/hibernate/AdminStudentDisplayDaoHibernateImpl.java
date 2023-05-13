package com.beaconfire.dao.hibernate;

import com.beaconfire.dao.AdminStudentDisplayDao;
import com.beaconfire.domain.hibernate.CourseHibernate;
import com.beaconfire.domain.hibernate.StudentClassHibernate;
import com.beaconfire.domain.hibernate.StudentHibernate;
import com.beaconfire.domain.jdbc.AdminHomeDisplay;
import com.beaconfire.domain.jdbc.Student;
import com.beaconfire.domain.jdbc.StudentClassDisplay;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Repository("adminStudentDisplayDaoHibernateImpl")
public class AdminStudentDisplayDaoHibernateImpl implements AdminStudentDisplayDao {

    @Autowired
    protected SessionFactory sessionFactory;
    @Override
    public AdminHomeDisplay getStudent(int studentId) {
        try(Session session = sessionFactory.openSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<StudentHibernate> cq = cb.createQuery(StudentHibernate.class);
            Root<StudentHibernate> studentRoot = cq.from(StudentHibernate.class);

            cq.where(cb.equal(studentRoot.get("id"), studentId));
            StudentHibernate student = session.createQuery(cq).getSingleResult();

            AdminHomeDisplay display = AdminHomeDisplay.builder()
                    .student_id(student.getId())
                    .first_name(student.getFirst_name())
                    .last_name(student.getLast_name())
                    .email(student.getEmail())
                    .department_name(student.getDepartmentHibernate().getName())
                    .school_name(student.getDepartmentHibernate().getSchool())
                    .is_active(String.valueOf(student.getIs_active()))
                    .build();
            return display;
        }
    }

    @Override
    public String UpdateStudentStatus(int studentId, int status) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = null;
            try {
                transaction = session.beginTransaction();

                StudentHibernate student = session.get(StudentHibernate.class, studentId);
                if (student == null) {
                    return "Student not found";
                }

                student.setIs_active(status);
                session.update(student);
                transaction.commit();
                return "Student status updated successfully";
            } catch (Exception e) {
                if (transaction != null) {
                    transaction.rollback();
                }
                return "Error updating student status: " + e.getMessage();
            }
        }
    }


    @Override
    public List<StudentClassDisplay> getStudentClassesByStudentId(int studentId) {

        try(Session session = sessionFactory.openSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<StudentClassHibernate> cq = cb.createQuery(StudentClassHibernate.class);
            Root<StudentClassHibernate> studentClassRoot = cq.from(StudentClassHibernate.class);

            cq.where(cb.equal(studentClassRoot.get("studentHibernate").get("id"), studentId));
            List<StudentClassHibernate> studentClasses = session.createQuery(cq).getResultList();
            List<StudentClassDisplay> results = new ArrayList<>();

            for(StudentClassHibernate sc : studentClasses) {
                StudentClassDisplay display = StudentClassDisplay.builder()
                        .student_id(sc.getStudentHibernate().getId())
                        .class_id(sc.getWebRegClassHibernate().getId())
                        .course_name(sc.getWebRegClassHibernate().getCourseHibernate().getName())
                        .course_code(sc.getWebRegClassHibernate().getCourseHibernate().getCode())
                        .department_name(sc.getWebRegClassHibernate().getCourseHibernate().getDepartmentHibernate().getName())
                        .school_name(sc.getWebRegClassHibernate().getCourseHibernate().getDepartmentHibernate().getSchool())
                        .semester_name(sc.getWebRegClassHibernate().getSemesterHibernate().getName())
                        .status(sc.getStatus())
                        .build();
                results.add(display);
            }

            return results.size() == 0 ? null : results;
        }

    }

    @Override
    public void flipStudentStatus(int studentId) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();

            StudentHibernate student = session.get(StudentHibernate.class, studentId);
            if (student != null) {
                student.setIs_active(student.getIs_active() == 1 ? 0 : 1);
                session.update(student);
            }

            transaction.commit();
        }
    }


    @Override
    public void changeStudentClassStatus(int studentId, int classId, String status) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();

            // Retrieve StudentClassHibernate with the given studentId and classId
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<StudentClassHibernate> cq = cb.createQuery(StudentClassHibernate.class);
            Root<StudentClassHibernate> studentClassRoot = cq.from(StudentClassHibernate.class);
            cq.where(cb.and(
                    cb.equal(studentClassRoot.get("studentHibernate").get("id"), studentId),
                    cb.equal(studentClassRoot.get("webRegClassHibernate").get("id"), classId)
            ));

            StudentClassHibernate studentClass = session.createQuery(cq).getResultList().get(0);

            if (studentClass != null) {
                studentClass.setStatus(status);
                session.update(studentClass);
            }

            transaction.commit();
        }
    }

}
