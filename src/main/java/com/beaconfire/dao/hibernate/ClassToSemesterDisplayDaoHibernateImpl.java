package com.beaconfire.dao.hibernate;

import com.beaconfire.domain.hibernate.SemesterHibernate;
import com.beaconfire.domain.hibernate.WebRegClassHibernate;
import com.beaconfire.domain.jdbc.ClassToSemesterDisplay;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("classToSemesterDisplayDaoHibernateImpl")
public class ClassToSemesterDisplayDaoHibernateImpl implements com.beaconfire.dao.ClassToSemesterDisplayDao{

    @Autowired
    protected SessionFactory sessionFactory;
    @Override
    public ClassToSemesterDisplay getSemesterbyClassId(int classId) {
        try(Session session = sessionFactory.openSession()){
            WebRegClassHibernate webRegClass = session.get(WebRegClassHibernate.class, classId);
            SemesterHibernate semester = webRegClass.getSemesterHibernate();
            ClassToSemesterDisplay display = ClassToSemesterDisplay.builder()
                    .class_id(classId)
                    .start_date(semester.getStart_date())
                    .end_date(semester.getEnd_date())
                    .semester_name(semester.getName())
                    .build();
            return display;
        }

    }
}
