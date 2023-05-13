package com.beaconfire.dao.hibernate;

import com.beaconfire.dao.ClassManagementDisplayDao;
import com.beaconfire.domain.hibernate.*;
import com.beaconfire.domain.jdbc.AdminClassDisplay;
import com.beaconfire.domain.jdbc.ClassManagementDisplay;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Repository("classManagementDisplayDaoHibernateImpl")
public class ClassManagementDisplayDaoHibernateImpl implements ClassManagementDisplayDao {

    @Autowired
    protected SessionFactory sessionFactory;

    @Override
    public List<ClassManagementDisplay> getClassManagementDisplayByStudentId(int userId) {
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<WebRegClassHibernate> cq = cb.createQuery(WebRegClassHibernate.class);
            Root<WebRegClassHibernate> webRegClassRoot = cq.from(WebRegClassHibernate.class);
            Join<WebRegClassHibernate, SemesterHibernate> semesterJoin = webRegClassRoot.join("semesterHibernate");

            cb.equal(webRegClassRoot.get("is_active"), 1);
            cb.lessThanOrEqualTo(semesterJoin.get("start_date"), LocalDate.now());
            cb.greaterThanOrEqualTo(semesterJoin.get("end_date"), LocalDate.now());
            cq.orderBy(cb.asc(semesterJoin.get("start_date")));
            List<WebRegClassHibernate> tmpList = session.createQuery(cq).getResultList();

            List<ClassManagementDisplay> results = new ArrayList<>();

            for(WebRegClassHibernate w: tmpList){
                ClassManagementDisplay cmd = ClassManagementDisplay.builder()
                        .class_id(w.getId())
                        .course_name(w.getCourseHibernate().getName())
                        .course_code(w.getCourseHibernate().getCode())
                        .department_name(w.getCourseHibernate().getDepartmentHibernate().getName())
                        .school_name(w.getCourseHibernate().getDepartmentHibernate().getSchool())
                        .semester_name(w.getSemesterHibernate().getName())
                        .capacity(String.valueOf(w.getCapacity()))
                        .enrollment_num(String.valueOf(w.getEnrollment_num()))
                        .isEnrolled(isStudentEnrolledInClass(userId, w.getId()) ? "Enrolled" : "Not Enrolled")
                        .build();
                results.add(cmd);
            }
            return results == null ? new ArrayList<>() : results;


        }
    }

    public boolean isStudentEnrolledInClass(int studentId, int classId) {
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Long> cq = cb.createQuery(Long.class);
            Root<StudentClassHibernate> studentClassRoot = cq.from(StudentClassHibernate.class);

            cq.select(cb.count(studentClassRoot))
                    .where(cb.and(
                            cb.equal(studentClassRoot.get("studentHibernate").get("id"), studentId),
                            cb.equal(studentClassRoot.get("webRegClassHibernate").get("id"), classId),
                            cb.equal(studentClassRoot.get("status"), "ongoing")
                    ));

            Query<Long> query = session.createQuery(cq);
            long count = query.getSingleResult();

            return count > 0;
        }
    }

}
