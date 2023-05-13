package com.beaconfire.domain.hibernate;

import lombok.*;
import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name="Semester")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SemesterHibernate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;

    @Column(name="start_date")
    private LocalDate start_date;

    @Column(name="end_date")
    private LocalDate end_date;

    @Column(name="name")
    private String name;

    @OneToMany(mappedBy = "semesterHibernate")
    private List<WebRegClassHibernate> webRegClassesHibernate;
}
