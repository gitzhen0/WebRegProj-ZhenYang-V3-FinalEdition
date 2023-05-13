package com.beaconfire.domain.hibernate;

import lombok.*;
import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="Department")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class DepartmentHibernate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;

    @Column(name="name", nullable = false)
    private String name;

    @Column(name="school", nullable = false)
    private String school;

    @OneToMany(mappedBy = "departmentHibernate")
    private List<CourseHibernate> coursesHibernate;

    @OneToMany(mappedBy = "departmentHibernate")
    private List<StudentHibernate> studentsHibernate;

    @OneToMany(mappedBy = "departmentHibernate")
    private List<ProfessorHibernate> professorsHibernate;

}
