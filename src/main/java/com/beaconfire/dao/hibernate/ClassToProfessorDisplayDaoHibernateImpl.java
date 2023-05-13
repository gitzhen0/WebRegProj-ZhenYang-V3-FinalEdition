package com.beaconfire.dao.hibernate;

import com.beaconfire.domain.hibernate.ProfessorHibernate;
import com.beaconfire.domain.hibernate.WebRegClassHibernate;
import com.beaconfire.domain.jdbc.ClassToProfessorDisplay;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("classToProfessorDisplayDaoHibernateImpl")
public class ClassToProfessorDisplayDaoHibernateImpl implements com.beaconfire.dao.ClassToProfessorDisplayDao{

    @Autowired
    protected SessionFactory sessionFactory;

    @Override
    public ClassToProfessorDisplay getProfessorByClassId(int class_id) {
        try(Session session = sessionFactory.openSession()){
            WebRegClassHibernate webRegClass = session.get(WebRegClassHibernate.class, class_id);

            ProfessorHibernate professor = webRegClass.getProfessorHibernate();
            System.out.println("testAAA: "+professor.getId());
            ClassToProfessorDisplay display = ClassToProfessorDisplay.builder()
                    .class_id(class_id)
                    .first_name(professor.getFirst_name())
                    .last_name(professor.getLast_name())
                    .email(professor.getEmail())
                    .department_name(professor.getDepartmentHibernate().getName())
                    .school_name(professor.getDepartmentHibernate().getSchool())
                    .build();

            return display;
        }
    }
}
