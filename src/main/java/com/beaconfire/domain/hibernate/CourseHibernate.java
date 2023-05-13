package com.beaconfire.domain.hibernate;

import lombok.*;
import javax.persistence.*;
import java.time.LocalTime;
import java.util.List;

@Entity
@Table(name="Course")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CourseHibernate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;

    @Column(name="name")
    private String name;

    @Column(name="code")
    private String code;

//    @Column(name="department_id")
//    private Integer department_id;

    @ManyToOne
    @JoinColumn(name="department_id")
    private DepartmentHibernate departmentHibernate;

    @OneToMany(mappedBy = "courseHibernate")
    private List<WebRegClassHibernate> webRegClassesHibernate;


    @OneToMany(mappedBy = "courseHibernate")
    private List<PrerequisiteHibernate> prerequisitesHibernate;

    @OneToMany(mappedBy = "preCourseHibernate")
    private List<PrerequisiteHibernate> prePrerequisitesHibernate;




    @Column(name="description")
    private String description;
}
