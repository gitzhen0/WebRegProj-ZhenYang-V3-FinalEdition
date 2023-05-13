package com.beaconfire.dao.hibernate;

import com.beaconfire.dao.ClassToLectureDisplayDao;
import com.beaconfire.domain.hibernate.LectureHibernate;
import com.beaconfire.domain.jdbc.ClassToLectureDisplay;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

@Repository("classToLectureDisplayDaoHibernateImpl")
public class ClassToLectureDisplayDaoHibernateImpl implements ClassToLectureDisplayDao {

    @Autowired
    protected SessionFactory sessionFactory;

    @Override
    public ClassToLectureDisplay getLectureByClassId(int classId) {
        try(Session session = sessionFactory.openSession()){
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<LectureHibernate> cq = cb.createQuery(LectureHibernate.class);
            Root<LectureHibernate> lectureRoot = cq.from(LectureHibernate.class);

            cq.where(cb.equal(lectureRoot.get("webRegClassHibernate").get("id"), classId));

            Query<LectureHibernate> query = session.createQuery(cq);

            LectureHibernate lecture = query.getSingleResult();

            ClassToLectureDisplay display = ClassToLectureDisplay.builder()
                    .class_id(lecture.getWebRegClassHibernate().getId())
                    .day_of_the_week(String.valueOf(lecture.getDay_of_the_week()))
                    .start_time(lecture.getStart_time())
                    .end_time(lecture.getEnd_time())
                    .build();

            return display;
        }
    }
}
