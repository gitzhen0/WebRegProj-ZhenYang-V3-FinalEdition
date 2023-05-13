package com.beaconfire.dao.hibernate;

import com.beaconfire.dao.AdminHomeDisplayDao;
import com.beaconfire.domain.hibernate.DepartmentHibernate;
import com.beaconfire.domain.hibernate.StudentHibernate;
import com.beaconfire.domain.jdbc.AdminHomeDisplay;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

//import javax.persistence.Query;
import org.hibernate.query.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Repository("adminHomeDisplayDaoHibernateImpl")
public class AdminHomeDisplayDaoHibernateImpl implements AdminHomeDisplayDao {

    @Autowired
    protected SessionFactory sessionFactory;

    @Override
    public List<AdminHomeDisplay> findPaginated(int page, int limit) {

        int offset = (page - 1) * limit;
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<StudentHibernate> cq = cb.createQuery(StudentHibernate.class);
            Root<StudentHibernate> studentRoot = cq.from(StudentHibernate.class);
            cq.where(cb.equal(studentRoot.get("is_admin"), 0));

            Query<StudentHibernate> query = session.createQuery(cq);
            query.setFirstResult(offset);
            query.setMaxResults(limit);


            List<StudentHibernate> tmpList = query.getResultList();

            List<AdminHomeDisplay> results = new ArrayList<>();

            for(StudentHibernate s: tmpList){
                AdminHomeDisplay display = AdminHomeDisplay.builder()
                        .student_id(s.getId())
                        .first_name(s.getFirst_name())
                        .last_name(s.getLast_name())
                        .email(s.getEmail())
                        .department_name(s.getDepartmentHibernate().getName())
                        .school_name(s.getDepartmentHibernate().getSchool())
                        .is_active(String.valueOf(s.getIs_active()))
                        .build();
                results.add(display);
            }

            //continue here
            return results.size() == 0 ? null : results;
        }
    }

    @Override
    public int getTotalPages(int limit) {
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Long> cq = cb.createQuery(Long.class);
            Root<StudentHibernate> studentRoot = cq.from(StudentHibernate.class);

            cq.select(cb.count(studentRoot));
            cq.where(cb.equal(studentRoot.get("is_admin"), 0));

            Long totalRecords = session.createQuery(cq).uniqueResult();
            return (int) Math.ceil((double) totalRecords / limit);
        }
    }
}
