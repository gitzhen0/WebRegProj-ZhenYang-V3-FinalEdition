package com.beaconfire.dao.hibernate;

import com.beaconfire.dao.DepartmentDao;
import com.beaconfire.domain.hibernate.DepartmentHibernate;
import com.beaconfire.domain.jdbc.Department;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Repository("departmentDaoHibernateImpl")
public class DepartmentDaoHibernateImpl implements DepartmentDao {

    @Autowired
    protected SessionFactory sessionFactory;

    @Override
    public List<Department> getAllDepartments() {
        try (Session session = sessionFactory.openSession()) {

            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<DepartmentHibernate> cq = cb.createQuery(DepartmentHibernate.class);
            Root<DepartmentHibernate> root = cq.from(DepartmentHibernate.class);
            cq.select(root);


            Query<DepartmentHibernate> query = session.createQuery(cq);
            List<DepartmentHibernate> tmpList = query.getResultList();

            List<Department> results = new ArrayList<>();

            for(DepartmentHibernate dh: tmpList){
                Department d = Department.builder()
                        .id(dh.getId())
                        .name(dh.getName())
                        .school(dh.getSchool())
                        .build();
                results.add(d);
            }
            return results;
        }


    }
}
