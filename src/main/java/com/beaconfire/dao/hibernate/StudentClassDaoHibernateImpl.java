package com.beaconfire.dao.hibernate;

import com.beaconfire.dao.StudentClassDao;
import com.beaconfire.domain.hibernate.*;
import com.beaconfire.domain.jdbc.StudentClass;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Repository("studentClassDaoHibernateImpl")
public class StudentClassDaoHibernateImpl implements StudentClassDao {

    @Autowired
    protected SessionFactory sessionFactory;


    @Override
    public List<StudentClass> getStudentClassByStudentId(int studentId) {
        try(Session session = sessionFactory.openSession()){
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<StudentClassHibernate> cq = cb.createQuery(StudentClassHibernate.class);
            cq.where(cb.equal(cq.from(StudentClassHibernate.class).get("student_id"), studentId));
            List<StudentClassHibernate> tmpList = session.createQuery(cq).getResultList();

            List<StudentClass> results = new ArrayList<>();

            for(StudentClassHibernate s: tmpList){
                StudentClass sc = StudentClass.builder()
                                .id(s.getId())
                        .student_id(s.getStudentHibernate().getId())
                        .class_id(s.getWebRegClassHibernate().getId())
                                .status(s.getStatus())
                                        .build();
                results.add(sc);
            }
            return results.isEmpty() ? new ArrayList<>() : results;
        }
    }

    @Override
    public int check0IsStudentEnrolledInClass(int studentId, int classId) {
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Long> cq = cb.createQuery(Long.class);
            Root<StudentClassHibernate> studentClassRoot = cq.from(StudentClassHibernate.class);

            cq.select(cb.count(studentClassRoot))
                    .where(cb.and(
                            cb.equal(studentClassRoot.get("studentHibernate").get("id"), studentId),
                            cb.equal(studentClassRoot.get("webRegClassHibernate").get("id"), classId),
                            cb.equal(studentClassRoot.get("status"), "ongoing")));

            Query<Long> query = session.createQuery(cq);
            return query.getSingleResult().intValue() > 0 ? 1 : 0;
        }
    }

    @Override
    public int checkAStudentIsActive(int studentId) {
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Long> cq = cb.createQuery(Long.class);
            Root<StudentHibernate> studentRoot = cq.from(StudentHibernate.class);

            cq.select(cb.count(studentRoot))
                    .where(cb.and(
                            cb.equal(studentRoot.get("id"), studentId),
                            cb.equal(studentRoot.get("is_active"), 1)));

            Query<Long> query = session.createQuery(cq);
            return query.getSingleResult().intValue() > 0 ? 1 : 0;
        }
    }


    @Override
    public int checkBClassIsActive(int classId) {
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Long> cq = cb.createQuery(Long.class);
            Root<WebRegClassHibernate> classRoot = cq.from(WebRegClassHibernate.class);

            cq.select(cb.count(classRoot))
                    .where(cb.and(
                            cb.equal(classRoot.get("id"), classId),
                            cb.equal(classRoot.get("is_active"), 1)));

            Query<Long> query = session.createQuery(cq);
            return query.getSingleResult().intValue() > 0 ? 1 : 0;
        }
    }

    @Override
    public int checkCStudentPassPrerequisite(int studentId, int classId) {
        try (Session session = sessionFactory.openSession()) {
            // Get the course_id of the class
            WebRegClassHibernate webRegClass = session.get(WebRegClassHibernate.class, classId);
            int courseId = webRegClass.getCourseHibernate().getId();

            // Get the prerequisite courses
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<CourseHibernate> cq = cb.createQuery(CourseHibernate.class);
            Root<PrerequisiteHibernate> prerequisiteRoot = cq.from(PrerequisiteHibernate.class);

            cq.select(prerequisiteRoot.get("preCourseHibernate"))
                    .where(cb.equal(prerequisiteRoot.get("courseHibernate").get("id"), courseId));

            Query<CourseHibernate> query = session.createQuery(cq);
            List<CourseHibernate> prerequisiteCourses = query.getResultList();

            // Check if the student has passed all prerequisite courses
            for (CourseHibernate prerequisiteCourse : prerequisiteCourses) {
                int preCourseId = prerequisiteCourse.getId();
                CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
                Root<StudentClassHibernate> studentClassRoot = countQuery.from(StudentClassHibernate.class);

                countQuery.select(cb.count(studentClassRoot))
                        .where(cb.and(
                                cb.equal(studentClassRoot.get("studentHibernate").get("id"), studentId),
                                cb.equal(studentClassRoot.get("webRegClassHibernate").get("courseHibernate").get("id"), preCourseId),
                                cb.equal(studentClassRoot.get("status"), "completed")));

                Query<Long> completedCoursesQuery = session.createQuery(countQuery);
                int count = completedCoursesQuery.getSingleResult().intValue();

                if (count == 0) {
                    return 0;
                }
            }
            return 1;
        }
    }


    @Override
    public int checkDNoLectureConflict(int studentId, int classId) {
        try (Session session = sessionFactory.openSession()) {
            // Get the lecture time of the class
            WebRegClassHibernate webRegClass = session.get(WebRegClassHibernate.class, classId);
            LectureHibernate lecture = webRegClass.getLectureHibernate().get(0);
            Time lectureStart = Time.valueOf(lecture.getStart_time());
            Time lectureEnd = Time.valueOf(lecture.getEnd_time());



            // Get the lecture times of the classes the student is enrolled in
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<WebRegClassHibernate> cq = cb.createQuery(WebRegClassHibernate.class);
            Root<StudentClassHibernate> studentClassRoot = cq.from(StudentClassHibernate.class);

            cq.select(studentClassRoot.get("webRegClassHibernate"))
                    .where(cb.and(
                            cb.equal(studentClassRoot.get("studentHibernate").get("id"), studentId),
                            cb.equal(studentClassRoot.get("status"), "ongoing")));

            Query<WebRegClassHibernate> query = session.createQuery(cq);
            List<WebRegClassHibernate> enrolledClasses = query.getResultList();

            // Check for lecture time conflicts
            for (WebRegClassHibernate enrolledClass : enrolledClasses) {
                LectureHibernate l2 = enrolledClass.getLectureHibernate().get(0);

                Time enrolledLectureStart = Time.valueOf(l2.getStart_time());
                Time enrolledLectureEnd = Time.valueOf(l2.getEnd_time());

                if (lectureStart.before(enrolledLectureEnd) && lectureEnd.after(enrolledLectureStart)) {
                    return 0;
                }
            }
            return 1;
        }
    }


    @Override
    public int checkEClassIsFull(int classId) {
        try (Session session = sessionFactory.openSession()) {
            // Get the class by classId
            WebRegClassHibernate webRegClass = session.get(WebRegClassHibernate.class, classId);

            // Check if the class is full
            if (webRegClass.getEnrollment_num() < webRegClass.getCapacity()) {
                return 1;
            } else {
                return 0;
            }
        }
    }


    @Override
    public int checkFIfStudentNeverPassed(int studentId, int courseId) {
        try (Session session = sessionFactory.openSession()) {
            // Create a CriteriaBuilder
            CriteriaBuilder cb = session.getCriteriaBuilder();

            // Create a CriteriaQuery for StudentClassHibernate
            CriteriaQuery<StudentClassHibernate> cq = cb.createQuery(StudentClassHibernate.class);

            // Create the root for StudentClassHibernate
            Root<StudentClassHibernate> studentClassRoot = cq.from(StudentClassHibernate.class);

            // Define the predicates
            Predicate studentPredicate = cb.equal(studentClassRoot.get("studentHibernate").get("id"), studentId);
            Predicate coursePredicate = cb.equal(studentClassRoot.get("webRegClassHibernate").get("courseHibernate").get("id"), courseId);
            Predicate statusPredicate = cb.equal(studentClassRoot.get("status"), "pass");

            // Combine the predicates and execute the query
            cq.select(studentClassRoot).where(cb.and(studentPredicate, coursePredicate, statusPredicate));
            List<StudentClassHibernate> results = session.createQuery(cq).getResultList();

            // Check if the student never passed the course
            return results.isEmpty() ? 1 : 0;
        }
    }


    @Override
    public int checkXisStudentEnrolledInClassWithinTwoWeeks(int studentId, int classId) {
        try (Session session = sessionFactory.openSession()) {
            // Get the class reference from the table
            WebRegClassHibernate webRegClass = session.get(WebRegClassHibernate.class, classId);
            if (webRegClass != null) {
                LocalDate semesterStartDate = webRegClass.getSemesterHibernate().getStart_date();
                LocalDate currentDate = LocalDate.now();
                long daysBetween = ChronoUnit.DAYS.between(semesterStartDate, currentDate);

                // Check if the current time is within 2 weeks (14 days) of the start of the semester
                if (daysBetween <= 14) {
                    return 1; // Within 2 weeks
                }
            }
        }
        return 0;
    }

    @Override
    public void addStudentToClass(int studentId, int classId) {
        try (Session session = sessionFactory.openSession()) {
            // Begin a transaction
            Transaction tx = session.beginTransaction();

            // Get the student and class references from their respective tables
            StudentHibernate student = session.get(StudentHibernate.class, studentId);
            WebRegClassHibernate webRegClass = session.get(WebRegClassHibernate.class, classId);

            // Create a new StudentClassHibernate instance and set the properties
            StudentClassHibernate studentClass = new StudentClassHibernate();
            studentClass.setStudentHibernate(student);
            studentClass.setWebRegClassHibernate(webRegClass);
            studentClass.setStatus("ongoing"); // Set the initial status

            // Save the StudentClassHibernate instance to the database
            session.save(studentClass);

            // Increase the class enrollment by 1
            webRegClass.setEnrollment_num(webRegClass.getEnrollment_num() + 1);
            session.update(webRegClass);

            // Commit the transaction
            tx.commit();
        }
    }

}
