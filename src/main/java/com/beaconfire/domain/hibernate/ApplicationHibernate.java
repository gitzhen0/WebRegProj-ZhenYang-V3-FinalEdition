package com.beaconfire.domain.hibernate;

import lombok.*;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name="Application")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ApplicationHibernate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;

//    @Column(name="student_id")
//    private Integer studentId;

    @ManyToOne
    @JoinColumn(name="student_id")
    private StudentHibernate studentHibernate;

//    @Column(name="class_id")
//    private Integer classId;

    @ManyToOne
    @JoinColumn(name="class_id")
    private WebRegClassHibernate webRegClassHibernate;

    @Column(name="creation_time")
    private LocalDateTime creation_time;

    @Column(name="request")
    private String request;

    @Column(name="status")
    private String status;

    @Column(name="feedback")
    private String feedback;

}
