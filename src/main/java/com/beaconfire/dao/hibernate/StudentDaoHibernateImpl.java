package com.beaconfire.dao.hibernate;

import com.beaconfire.dao.StudentDao;
import com.beaconfire.domain.hibernate.DepartmentHibernate;
import com.beaconfire.domain.hibernate.StudentHibernate;
import com.beaconfire.domain.jdbc.Student;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

@Repository("studentDaoHibernateImpl")
public class StudentDaoHibernateImpl implements StudentDao {

    @Autowired
    protected SessionFactory sessionFactory;

    @Override
    public int registerStudent(String first_name, String last_name, String email, String password, int department_id, int is_active, int is_admin) {
        System.out.println("inside registerStudent");
        try (Session session = sessionFactory.openSession()) {
            // Create a new Student instance and set properties
            StudentHibernate student = new StudentHibernate();
            student.setFirst_name(first_name);
            student.setLast_name(last_name);
            student.setEmail(email);
            student.setPassword(password);
            student.setIs_active(is_active);
            student.setIs_admin(is_admin);
            student.setDepartmentHibernate(session.get(DepartmentHibernate.class, department_id));
            // Persist the new Student instance and return the generated ID
            Transaction transaction = session.beginTransaction();
            int studentId = (int) session.save(student);

            transaction.commit();
            return studentId;
        }
    }

    @Override
    public Student getStudentByEmail(String email) {
        try (Session session = sessionFactory.openSession()) {
            // Get CriteriaBuilder instance from the session
            CriteriaBuilder cb = session.getCriteriaBuilder();

            // Create a CriteriaQuery for Student class
            CriteriaQuery<StudentHibernate> cq = cb.createQuery(StudentHibernate.class);
            Root<StudentHibernate> studentRoot = cq.from(StudentHibernate.class);

            // Set the condition for the email
            cq.where(cb.equal(studentRoot.get("email"), email));

            // Create a query, execute it and return the result
            Query<StudentHibernate> query = session.createQuery(cq);

            StudentHibernate tmp = query.uniqueResult();

            Student student = Student.builder()
                    .id(tmp.getId())
                    .first_name(tmp.getFirst_name())
                    .last_name(tmp.getLast_name())
                    .email(tmp.getEmail())
                    .password(tmp.getPassword())
                    .department_id(tmp.getDepartmentHibernate().getId())
                    .is_active(tmp.getIs_active())
                    .is_admin(tmp.getIs_admin())
                    .build();


            return student;
        }
    }
}
