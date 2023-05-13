package com.beaconfire.domain.hibernate;

import lombok.*;
import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="Professor")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ProfessorHibernate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;

    @Column(name="first_name")
    private String first_name;

    @Column(name="last_name")
    private String last_name;

    @Column(name="email")
    private String email;

//    @Column(name="department_id")
//    private Integer department_id;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private DepartmentHibernate departmentHibernate;

    @OneToMany(mappedBy = "professorHibernate")
    private List<WebRegClassHibernate> webRegClassesHibernate;



}
