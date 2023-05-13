package com.beaconfire.dao.hibernate;

import com.beaconfire.dao.AdminCourseDisplayDao;
import com.beaconfire.domain.hibernate.*;
import com.beaconfire.domain.jdbc.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Repository("adminCourseDisplayDaoHibernateImpl")
public class AdminCourseDisplayDaoHibernateImpl implements AdminCourseDisplayDao {

    @Autowired
    protected SessionFactory sessionFactory;


    @Override
    public List<AdminCourseDisplay> getAllCourses() {
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<CourseHibernate> cq = cb.createQuery(CourseHibernate.class);
            Root<CourseHibernate> courseRoot = cq.from(CourseHibernate.class);

            List<CourseHibernate> tmpList = session.createQuery(cq).getResultList();
            List<AdminCourseDisplay> results = new ArrayList<>();
            for (CourseHibernate c : tmpList) {
                AdminCourseDisplay display = AdminCourseDisplay.builder()
                        .course_id(c.getId())
                        .course_name(c.getName())
                        .course_code(c.getCode())
                        .department_name(c.getDepartmentHibernate().getName())
                        .school_name(c.getDepartmentHibernate().getSchool())
                        .description(c.getDescription())
                        .build();
                results.add(display);
            }
            return results.size() == 0 ? null : results;
        }
    }

    @Override
    public List<AdminProfessor> getAllProfessors() {
        try(Session session = sessionFactory.openSession()){
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<ProfessorHibernate> cq = cb.createQuery(ProfessorHibernate.class);
            Root<ProfessorHibernate> professorRoot = cq.from(ProfessorHibernate.class);

            List<ProfessorHibernate> tmpList = session.createQuery(cq).getResultList();
            List<AdminProfessor> results = new ArrayList<>();

            for(ProfessorHibernate p: tmpList){
                AdminProfessor adminProfessor = AdminProfessor.builder()
                                .id(p.getId())
                                .first_name(p.getFirst_name())
                                .last_name(p.getLast_name())
                                .build();
                results.add(adminProfessor);
            }
            return results.size() == 0 ? null : results;
        }
    }

    @Override
    public List<AdminClassroom> getAllClassrooms() {

        try(Session session = sessionFactory.openSession()){
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<ClassroomHibernate> cq = cb.createQuery(ClassroomHibernate.class);
            Root<ClassroomHibernate> classroomRoot = cq.from(ClassroomHibernate.class);

            List<ClassroomHibernate> tmpList = session.createQuery(cq).getResultList();
            List<AdminClassroom> results = new ArrayList<>();

            for(ClassroomHibernate p: tmpList){
                AdminClassroom adminClassroom = AdminClassroom.builder()
                        .id(p.getId())
                        .name(p.getName())
                        .build();
                results.add(adminClassroom);
            }
            return results.size() == 0 ? null : results;
        }
    }

    @Override
    public List<AdminSemester> getAllSemesters() {
        try(Session session = sessionFactory.openSession()){
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<SemesterHibernate> cq = cb.createQuery(SemesterHibernate.class);
            Root<SemesterHibernate> semesterRoot = cq.from(SemesterHibernate.class);

            List<SemesterHibernate> tmpList = session.createQuery(cq).getResultList();
            List<AdminSemester> results = new ArrayList<>();

            for(SemesterHibernate p: tmpList){
                AdminSemester adminSemester = AdminSemester.builder()
                                .id(p.getId())
                                .name(p.getName())
                                .build();
                results.add(adminSemester);
            }
            return results.size() == 0 ? null : results;
        }

    }

    @Override
    public void addNewCourse(String courseName, String courseCode, int departmentId, String description) {
        Transaction tx = null;
        try (Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();

            DepartmentHibernate department = session.get(DepartmentHibernate.class, departmentId);

            CourseHibernate course = new CourseHibernate();

            course.setName(courseName);
            course.setCode(courseCode);
            course.setDepartmentHibernate(department);
            course.setDescription(description);

            session.save(course);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        }
    }
}
