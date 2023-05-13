package com.beaconfire.dao.hibernate;

import com.beaconfire.dao.ClassToPrerequisiteDisplayDao;
import com.beaconfire.domain.hibernate.CourseHibernate;
import com.beaconfire.domain.hibernate.PrerequisiteHibernate;
import com.beaconfire.domain.hibernate.WebRegClassHibernate;
import com.beaconfire.domain.jdbc.ClassToPrerequisiteDisplay;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

//import javax.persistence.TypedQuery;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;


@Repository("classToPrerequisiteDisplayDaoHibernateImpl")
public class ClassToPrerequisiteDisplayDaoHibernateImpl implements ClassToPrerequisiteDisplayDao {

    @Autowired
    protected SessionFactory sessionFactory;

    @Override
    public ClassToPrerequisiteDisplay getPrerequisiteByClassId(int classId) {
        try(Session session = sessionFactory.openSession()){

            WebRegClassHibernate webRegClass = session.get(WebRegClassHibernate.class, classId);
            CourseHibernate course = webRegClass.getCourseHibernate();

            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<PrerequisiteHibernate> cq = cb.createQuery(PrerequisiteHibernate.class);
            Root<PrerequisiteHibernate> prerequisiteRoot = cq.from(PrerequisiteHibernate.class);
            cq.where(cb.equal(prerequisiteRoot.get("courseHibernate"), course));

            TypedQuery<PrerequisiteHibernate> query = session.createQuery(cq);

            List<PrerequisiteHibernate> prerequisites = query.getResultList();

            List<ClassToPrerequisiteDisplay> results = new ArrayList<>();

            for(PrerequisiteHibernate p : prerequisites) {
                ClassToPrerequisiteDisplay display = ClassToPrerequisiteDisplay.builder()
                        .class_id(p.getCourseHibernate().getId())
                                .prerequisite_id(p.getPreCourseHibernate().getId())
                                .prerequisite_name(p.getPreCourseHibernate().getName())
                                .build();
                results.add(display);
            }
            return results.size() == 0 ? null :results.get(0);

        }
    }

}
