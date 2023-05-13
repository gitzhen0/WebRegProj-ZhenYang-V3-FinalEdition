package com.beaconfire.domain.hibernate;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="Student")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class StudentHibernate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    @Column(name = "first_name")
    private String first_name;

    @Column(name = "last_name")
    private String last_name;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

//    @Column(name = "department_id")
//    private Integer department_id;

    @Column(name = "is_active")
    private Integer is_active;

    @Column(name = "is_admin")
    private Integer is_admin;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private DepartmentHibernate departmentHibernate;

    @OneToMany(mappedBy = "studentHibernate")
    private List<StudentClassHibernate> studentClassesHibernate;

    @OneToMany(mappedBy = "studentHibernate")
    private List<ApplicationHibernate> applicationsHibernate;

}
