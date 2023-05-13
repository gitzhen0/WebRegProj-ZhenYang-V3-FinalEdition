package com.beaconfire.dao.hibernate;

import com.beaconfire.domain.hibernate.WebRegClassHibernate;
import com.beaconfire.domain.jdbc.WebRegClassDisplay;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;



@Repository("webRegClassDisplayDaoHibernateImpl")
public class WebRegClassDisplayDaoHibernateImpl implements com.beaconfire.dao.WebRegClassDisplayDao{

    @Autowired
    protected SessionFactory sessionFactory;
    @Override
    public WebRegClassDisplay getWebRegClassDisplayById(Integer id) {
        try(Session session = sessionFactory.openSession()){
            WebRegClassHibernate webRegClassHibernate = session.get(WebRegClassHibernate.class, id);

            WebRegClassDisplay result = WebRegClassDisplay.builder()
                    .class_id(String.valueOf(webRegClassHibernate.getId()))
                    .course_name(webRegClassHibernate.getCourseHibernate().getName())
                    .course_code(webRegClassHibernate.getCourseHibernate().getCode())
                    .department_name(webRegClassHibernate.getCourseHibernate().getDepartmentHibernate().getName())
                    .school_name(webRegClassHibernate.getCourseHibernate().getDepartmentHibernate().getSchool())
                    .course_description(webRegClassHibernate.getCourseHibernate().getDescription())
                    .capacity(webRegClassHibernate.getCapacity())
                    .enrollment_num(webRegClassHibernate.getEnrollment_num())
                    .is_active(String.valueOf(webRegClassHibernate.getIs_active()))
                    .build();

            return result;
        }
    }

    @Override
    public WebRegClassDisplay getWebRegClassDisplayByClassId(Integer id) {
        try(Session session = sessionFactory.openSession()){
            WebRegClassHibernate webRegClassHibernate = session.get(WebRegClassHibernate.class, id);

            WebRegClassDisplay result = WebRegClassDisplay.builder()
                    .class_id(String.valueOf(webRegClassHibernate.getId()))
                    .course_name(webRegClassHibernate.getCourseHibernate().getName())
                    .course_code(webRegClassHibernate.getCourseHibernate().getCode())
                    .department_name(webRegClassHibernate.getCourseHibernate().getDepartmentHibernate().getName())
                    .school_name(webRegClassHibernate.getCourseHibernate().getDepartmentHibernate().getSchool())
                    .course_description(webRegClassHibernate.getCourseHibernate().getDescription())
                    .capacity(webRegClassHibernate.getCapacity())
                    .enrollment_num(webRegClassHibernate.getEnrollment_num())
                    .is_active(String.valueOf(webRegClassHibernate.getIs_active()))
                    .build();

            return result;
        }
    }
}
