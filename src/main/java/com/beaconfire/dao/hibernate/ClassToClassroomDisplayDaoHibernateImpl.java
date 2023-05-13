package com.beaconfire.dao.hibernate;

import com.beaconfire.dao.ClassToClassroomDisplayDao;
import com.beaconfire.domain.hibernate.ClassroomHibernate;
import com.beaconfire.domain.hibernate.WebRegClassHibernate;
import com.beaconfire.domain.jdbc.ClassToClassroomDisplay;
import com.beaconfire.domain.jdbc.WebRegClassDisplay;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

@Repository("classToClassroomDisplayDaoHibernateImpl")
public class ClassToClassroomDisplayDaoHibernateImpl implements ClassToClassroomDisplayDao {

    @Autowired
    protected SessionFactory sessionFactory;
    @Override
    public ClassToClassroomDisplay getClassroomByClassId(int class_id) {
        try(Session session = sessionFactory.openSession()){
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<ClassroomHibernate> cr = cb.createQuery(ClassroomHibernate.class);
            Root<ClassroomHibernate> root = cr.from(ClassroomHibernate.class);
            WebRegClassHibernate webRegClass = session.get(WebRegClassHibernate.class, class_id);
            ClassroomHibernate classroom = webRegClass.getClassroomHibernate();
            ClassToClassroomDisplay display = ClassToClassroomDisplay.builder()
                    .class_id(classroom.getId())
                    .name(classroom.getName())
                    .building(classroom.getBuilding())
                    .capacity(classroom.getCapacity())
                    .build();
            return display;
        }
    }
}
