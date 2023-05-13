package com.beaconfire.domain.hibernate;

import lombok.*;
import javax.persistence.*;

@Entity
@Table(name="StudentClass")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class StudentClassHibernate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private StudentHibernate studentHibernate;

    @ManyToOne
    @JoinColumn(name = "class_id")
    private WebRegClassHibernate webRegClassHibernate;

    @Column(name = "status")
    private String status;
}
