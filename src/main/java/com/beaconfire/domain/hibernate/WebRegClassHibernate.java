package com.beaconfire.domain.hibernate;

import lombok.*;
import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="WebRegClass")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class WebRegClassHibernate {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id", unique = true, nullable = false)
        private Integer id;

//        @Column(name = "course_id")
//        private Integer course_id;

        @ManyToOne
        @JoinColumn(name = "course_id")
        private CourseHibernate courseHibernate;


//        @Column(name = "semester_id")
//        private Integer semester_id;

        @ManyToOne
        @JoinColumn(name = "semester_id")
        private SemesterHibernate semesterHibernate;

//        @Column(name = "professor_id")
//        private Integer professor_id;

        @ManyToOne
        @JoinColumn(name = "professor_id")
        private ProfessorHibernate professorHibernate;

//        @Column(name = "classroom_id")
//        private Integer classroom_id;

        @ManyToOne
        @JoinColumn(name = "classroom_id")
        private ClassroomHibernate classroomHibernate;

        @Column(name = "capacity")
        private Integer capacity;

        @Column(name = "enrollment_num")
        private Integer enrollment_num;

        @Column(name = "is_active")
        private Integer is_active;

        @OneToMany(mappedBy = "webRegClassHibernate")
        private List<StudentClassHibernate> studentClassesHibernate;

        @OneToMany(mappedBy = "webRegClassHibernate")
        private List<LectureHibernate> lectureHibernate;
}
