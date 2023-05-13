package com.beaconfire.domain.hibernate;

import lombok.*;
import javax.persistence.*;
import java.time.LocalTime;

@Entity
@Table(name="Lecture")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class LectureHibernate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name="class_id")
    private WebRegClassHibernate webRegClassHibernate;

    @Column(name="day_of_the_week")
    private Integer day_of_the_week;

    @Column(name="start_time")
    private LocalTime start_time;

    @Column(name="end_time")
    private LocalTime end_time;

}
