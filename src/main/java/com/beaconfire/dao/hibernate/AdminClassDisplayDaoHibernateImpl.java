package com.beaconfire.dao.hibernate;

import com.beaconfire.dao.AdminClassDisplayDao;
import com.beaconfire.domain.hibernate.*;
import com.beaconfire.domain.jdbc.AdminClassDisplay;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Repository("adminClassDisplayDaoHibernateImpl")
public class AdminClassDisplayDaoHibernateImpl implements AdminClassDisplayDao {

    @Autowired
    protected SessionFactory sessionFactory;

    @Override
    public List<AdminClassDisplay> getAllClasses() {
        System.out.println("trial2: get all classes");
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<WebRegClassHibernate> cq = cb.createQuery(WebRegClassHibernate.class);
            Root<WebRegClassHibernate> root = cq.from(WebRegClassHibernate.class);


            // Join tables and select required fields
            // Add ordering and conditions as needed
            List<WebRegClassHibernate> tmpList = session.createQuery(cq).getResultList();

            List<AdminClassDisplay> results = new ArrayList<>();

            for(WebRegClassHibernate w: tmpList){
                AdminClassDisplay adminClassDisplay = AdminClassDisplay.builder()
                        .class_id(w.getId())
                        .course_id(w.getCourseHibernate().getId())
                        .course_name(w.getCourseHibernate().getName())
                        .course_code(w.getCourseHibernate().getCode())
                        .department_name(w.getCourseHibernate().getDepartmentHibernate().getName())
                        .school_name(w.getCourseHibernate().getDepartmentHibernate().getSchool())
                        .semester_name(w.getSemesterHibernate().getName())
                        .capacity(w.getCapacity())
                        .enrollment_num(w.getEnrollment_num())
                        .is_active(w.getIs_active() == 1 ? "true": "false")
                        .build();
                results.add(adminClassDisplay);
            }
            return results.size() == 0 ? null : results;
        }
    }

    @Override
    public int addNewClass(int course_id, int professor_id, int semester_id, int classroom_id, int capacity, String dayOfWeek, LocalTime startTime, LocalTime endTime) {
        System.out.println("trial2: add new class");
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            // Retrieve associated entities
            CourseHibernate course = session.get(CourseHibernate.class, course_id);
            ProfessorHibernate professor = session.get(ProfessorHibernate.class, professor_id);
            System.out.println("testBBB: "+ professor_id);
            SemesterHibernate semester = session.get(SemesterHibernate.class, semester_id);
            ClassroomHibernate classroom = session.get(ClassroomHibernate.class, classroom_id);

            // Create new WebRegClass instance and set properties
            WebRegClassHibernate newClass = new WebRegClassHibernate();
            newClass.setCourseHibernate(course);
            newClass.setProfessorHibernate(professor);
            newClass.setSemesterHibernate(semester);
            newClass.setClassroomHibernate(classroom);
            newClass.setCapacity(capacity);
            newClass.setEnrollment_num(0);
            newClass.setIs_active(1);

            // Save the new WebRegClass instance
            session.save(newClass);

            // Create new Lecture instance and set properties
            LectureHibernate newLecture = new LectureHibernate();
            newLecture.setWebRegClassHibernate(newClass);
            newLecture.setDay_of_the_week(Integer.valueOf(dayOfWeek)); //??????
            newLecture.setStart_time(startTime);
            newLecture.setEnd_time(endTime);

            // Save the new Lecture instance
            session.save(newLecture);

            session.getTransaction().commit();

            return newClass.getId();
        }
    }


    @Override
    public void flipClassStatus(int classId) {
        System.out.println("trial2: flip class status");
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            WebRegClassHibernate webRegClass = session.get(WebRegClassHibernate.class, classId);
            if (webRegClass != null) {
                webRegClass.setIs_active(webRegClass.getIs_active() == 1 ? 0 : 1);
                session.update(webRegClass);
            }

            session.getTransaction().commit();
        }
    }
}
