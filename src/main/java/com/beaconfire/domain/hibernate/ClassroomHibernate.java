package com.beaconfire.domain.hibernate;

import lombok.*;
import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="Classroom")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ClassroomHibernate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;

    @Column(name="name")
    private String name;

    @Column(name="building")
    private String building;

    @Column(name="capacity")
    private Integer capacity;

    @OneToMany(mappedBy = "classroomHibernate")
    private List<WebRegClassHibernate> webRegClassesHibernate;
}
