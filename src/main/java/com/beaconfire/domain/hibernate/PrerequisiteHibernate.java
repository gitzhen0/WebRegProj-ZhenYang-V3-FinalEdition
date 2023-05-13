package com.beaconfire.domain.hibernate;

import lombok.*;
import javax.persistence.*;

@Entity
@Table(name="Prerequisite")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PrerequisiteHibernate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name="course_id")
    private CourseHibernate courseHibernate;

    @ManyToOne
    @JoinColumn(name="pre_course_id")
    private CourseHibernate preCourseHibernate;
}
