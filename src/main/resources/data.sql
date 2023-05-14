use WebRegDB;
-- drop all tables, don't uncomment this!
-- DROP TABLE IF EXISTS Application;
-- DROP TABLE IF EXISTS StudentClass;
-- DROP TABLE IF EXISTS Student;
-- DROP TABLE IF EXISTS Lecture;
-- DROP TABLE IF EXISTS WebRegClass;
-- DROP TABLE IF EXISTS Classroom;
-- DROP TABLE IF EXISTS Professor;
-- DROP TABLE IF EXISTS Semester;
-- DROP TABLE IF EXISTS Prerequisite;
-- DROP TABLE IF EXISTS Course;
-- DROP TABLE IF EXISTS Department;


-- ----------------------------------------------------------------------
-- Using this script to inserting some fake data
-- ----------------------------------------------------------------------

-- Insert fake data into Department
INSERT INTO Department (name, school)
VALUES ('Computer Science', 'School of Engineering'),
       ('Electrical Engineering', 'School of Engineering'),
       ('Mathematics', 'School of Science'),
       ('Physics', 'School of Science'),
       ('Chemistry', 'School of Science'),
       ('Biology', 'School of Science'),
       ('History', 'School of Humanities'),
       ('English', 'School of Humanities'),
       ('Economics', 'School of Social Sciences'),
       ('Political Science', 'School of Social Sciences');

-- Insert fake data into Course
INSERT INTO Course (name, code, department_id, description)
VALUES ('Introduction to Programming', 'CS101', 1, 'An introduction to programming concepts and techniques.'),
       ('Data Structures', 'CS201', 1, 'An in-depth study of data structures and their applications.'),
       ('Operating Systems', 'CS301', 1, 'An exploration of operating system concepts and implementations.'),
       ('Calculus I', 'MATH101', 3, 'An introduction to single-variable calculus.'),
       ('Calculus II', 'MATH201', 3, 'A continuation of single-variable calculus.'),
       ('General Physics I', 'PHYS101', 4, 'An introduction to classical mechanics.'),
       ('General Physics II', 'PHYS201', 4, 'An introduction to electricity and magnetism.'),
       ('Organic Chemistry I', 'CHEM101', 5, 'An introduction to the study of organic chemistry.'),
       ('World History I', 'HIST101', 7, 'A survey of world history from ancient times to the early modern period.'),
       ('Microeconomics', 'ECON101', 9, 'An introduction to microeconomic theory.'),
       ('Test Prereq I', 'TEST001', 1, 'Testing Prerequisite 1'),
       ('Test Prereq II', 'TEST002', 1, 'Testing Prerequisite 2'),
       ('Test Prereq III', 'TEST003', 1, 'Testing Prerequisite 3'),
       ('Test General I', 'TEST111', 1, 'General Testing 1'),
       ('Test General II', 'TEST222', 1, 'General Testing 2'),
       ('Test Lecture Conflict I', 'TEST101', 1, 'Testing Lecture Time Conflict 2'),
       ('Test Lecture Conflict II', 'TEST102', 1, 'Testing Lecture Time Conflict 2'),
       ('Test Withdraw in 2 Weeks', 'TEST404', 1, 'Test if Can Drop within 2 weeks');

-- Insert fake data into Prerequisite
INSERT INTO Prerequisite (course_id, pre_course_id)
VALUES (2, 1),
       (3, 2),
       (5, 4),
       (5, 1),
       (6, 4),
       (7, 6),
       (8, 5),
       (10, 9),
       (12, 11),
       (13, 12);


-- Insert fake data into Semester
INSERT INTO Semester (start_date, end_date, name)
VALUES ('2023-01-23', '2023-05-19', '2023 SPRING'),
       ('2022-08-29', '2022-12-16', '2022 FALL'),
       ('2022-01-24', '2022-05-20', '2022 SPRING'),
       ('2021-08-30', '2021-12-17', '2021 FALL'),
       ('2021-01-25', '2021-05-21', '2021 SPRING'),
       ('2020-08-31', '2020-12-18', '2020 FALL'),
       ('2020-01-27', '2020-05-22', '2020 SPRING'),
       ('2019-08-26', '2019-12-13', '2019 FALL'),
       ('2019-01-28', '2019-05-24', '2019 SPRING'),
       ('2018-08-27', '2018-12-14', '2018 FALL'),
       ('2023-05-11', '2023-7-14', 'In2WeeKs');

-- Insert fake data into Professor
INSERT INTO Professor (first_name, last_name, email, department_id)
VALUES ('John', 'Doe', 'john.doe@example.com', 1),
       ('Jane', 'Smith', 'jane.smith@example.com', 1),
       ('Michael', 'Johnson', 'michael.johnson@example.com', 2),
       ('Emily', 'Brown', 'emily.brown@example.com', 3),
       ('Samantha', 'Davis', 'samantha.davis@example.com', 4),
       ('David', 'Martinez', 'david.martinez@example.com', 5),
       ('Sophia', 'Garcia', 'sophia.garcia@example.com', 6),
       ('Daniel', 'Miller', 'daniel.miller@example.com', 7),
       ('Olivia', 'Rodriguez', 'olivia.rodriguez@example.com', 8),
       ('Alexander', 'Perez', 'alexander.perez@example.com', 9),
       ('Patrick', 'Star', 'Patrick.star@example.com', 6),
       ('Mr.', 'Krabs', 'Krabs@example.com', 6);

-- Insert fake data into Classroom
INSERT INTO Classroom (name, building, capacity)
VALUES ('A101', 'Building A', 50),
       ('A102', 'Building A', 30),
       ('B101', 'Building B', 40),
       ('B102', 'Building B', 20),
       ('C101', 'Building C', 60),
       ('C102', 'Building C', 40),
       ('D101', 'Building D', 25),
       ('D102', 'Building D', 35),
       ('E101', 'Building E', 45),
       ('E102', 'Building E', 30);

-- Insert fake data into WebRegClass
INSERT INTO WebRegClass (course_id, semester_id, professor_id, classroom_id, capacity, enrollment_num, is_active)
VALUES (1, 1, 1, 1, 50, 40, 1),
       (2, 1, 1, 2, 30, 25, 1),
       (3, 1, 1, 3, 40, 30, 1),
       (4, 1, 4, 4, 20, 15, 1),
       (5, 1, 4, 5, 60, 45, 1),
       (6, 1, 5, 6, 40, 35, 1),
       (7, 1, 5, 7, 25, 20, 1),
       (8, 1, 6, 8, 35, 25, 1),
       (9, 1, 8, 9, 45, 30, 1),
       (10, 1, 9, 1, 30, 20, 1),
       (11, 1, 1, 1, 10, 5, 1),
       (12, 1, 1, 1, 10, 5, 1),
       (13, 1, 1, 1, 10, 5, 1),
       (16, 1, 1, 1, 10, 5, 1),
       (17, 1, 1, 1, 10, 5, 1),
       (14, 1, 1, 1, 20, 20, 1),
       (15, 1, 1, 1, 10, 11, 1),
       (18, 11, 3, 1, 10, 5, 1);

-- Insert fake data into Lecture
INSERT INTO Lecture (class_id, day_of_the_week, start_time, end_time)
VALUES (1, 1, '09:00:00', '10:15:00'),
       (2, 2, '13:30:00', '14:45:00'),
       (3, 1, '11:00:00', '12:15:00'),
       (4, 2, '10:00:00', '11:15:00'),
       (5, 1, '14:00:00', '15:15:00'),
       (6, 2, '15:30:00', '16:45:00'),
       (7, 3, '16:00:00', '17:15:00'),
       (8, 4, '08:30:00', '09:45:00'),
       (9, 1, '13:00:00', '14:15:00'),
       (10, 4, '11:30:00', '12:45:00'),
       (11, 1, '11:00:00', '12:00:00'),
       (12, 1, '11:00:00', '12:00:00'),
       (13, 1, '11:00:00', '12:00:00'),
       (14, 1, '14:00:00', '16:00:00'),
       (15, 1, '14:00:00', '16:00:00'),
       (16, 3, '14:00:00', '16:00:00'),
       (17, 4, '20:00:00', '21:00:00'),
       (18, 7, '2:00:00', '4:00:00');

-- Insert fake data into Student
-- the password is md5 encrypted, password are all just "1"
INSERT INTO Student (first_name, last_name, email, password, department_id, is_active, is_admin)
VALUES ('Ashley', 'Lin', 'alice.wang@example.com', '$2a$10$qmrYU5DiLUOdgQvhKoER4eetJINPyr2aDLXh7KiKXnP1FV6Arfgz2', 1, 1, 0),
       ('Bob', 'Johnson', 'bob.johnson@example.com', '$2a$10$qmrYU5DiLUOdgQvhKoER4eetJINPyr2aDLXh7KiKXnP1FV6Arfgz2', 1, 1, 0),
       ('Charlie', 'Gomez', 'charlie.gomez@example.com', '$2a$10$qmrYU5DiLUOdgQvhKoER4eetJINPyr2aDLXh7KiKXnP1FV6Arfgz2', 2, 1, 0),
       ('Diana', 'Kim', 'diana.kim@example.com', '$2a$10$qmrYU5DiLUOdgQvhKoER4eetJINPyr2aDLXh7KiKXnP1FV6Arfgz2', 2, 1, 0),
       ('Eva', 'Lopez', 'eva.lopez@example.com', '$2a$10$qmrYU5DiLUOdgQvhKoER4eetJINPyr2aDLXh7KiKXnP1FV6Arfgz2', 3, 1, 0),
       ('Frank', 'Lee', 'frank.lee@example.com', '$2a$10$qmrYU5DiLUOdgQvhKoER4eetJINPyr2aDLXh7KiKXnP1FV6Arfgz2', 3, 1, 0),
       ('Grace', 'Hernandez', 'grace.hernandez@example.com', '$2a$10$qmrYU5DiLUOdgQvhKoER4eetJINPyr2aDLXh7KiKXnP1FV6Arfgz2', 4, 1, 0),
       ('Hank', 'Gonzalez', 'hank.gonzalez@example.com', '$2a$10$qmrYU5DiLUOdgQvhKoER4eetJINPyr2aDLXh7KiKXnP1FV6Arfgz2', 4, 1, 0),
       ('Ivy', 'Clark', 'ivy.clark@example.com', '$2a$10$qmrYU5DiLUOdgQvhKoER4eetJINPyr2aDLXh7KiKXnP1FV6Arfgz2', 5, 1, 0),
       ('Jack', 'Lewis', 'jack.lewis@example.com', '$2a$10$qmrYU5DiLUOdgQvhKoER4eetJINPyr2aDLXh7KiKXnP1FV6Arfgz2', 5, 1, 0),
       ('Admin', 'One', 'admin@1', '$2a$10$qmrYU5DiLUOdgQvhKoER4eetJINPyr2aDLXh7KiKXnP1FV6Arfgz2', 1, 1, 1),
       ('Admin', 'Two', 'admin@2', '$2a$10$qmrYU5DiLUOdgQvhKoER4eetJINPyr2aDLXh7KiKXnP1FV6Arfgz2', 1, 1, 1),
       ('Student', 'One', 'student@1', '$2a$10$qmrYU5DiLUOdgQvhKoER4eetJINPyr2aDLXh7KiKXnP1FV6Arfgz2', 1, 1, 0),
       ('Student', 'Two', 'student@2', '$2a$10$qmrYU5DiLUOdgQvhKoER4eetJINPyr2aDLXh7KiKXnP1FV6Arfgz2', 1, 1, 0),
       ('test', 'test', '1@1', '$2a$10$qmrYU5DiLUOdgQvhKoER4eetJINPyr2aDLXh7KiKXnP1FV6Arfgz2', 1, 1, 0);

-- Insert fake data into StudentClass
INSERT INTO StudentClass (student_id, class_id, status)
VALUES (1, 1, 'ongoing'),
       (1, 2, 'ongoing'),
       (2, 1, 'pass'),
       (2, 4, 'ongoing'),
       (3, 4, 'ongoing'),
       (3, 5, 'ongoing'),
       (4, 6, 'ongoing'),
       (4, 7, 'ongoing'),
       (5, 8, 'ongoing'),
       (5, 9, 'ongoing'),
       (2, 4, 'ongoing'),
       (2, 1, 'pass'),
       (2, 2, 'fail'),
       (2, 3, 'ongoing'),
       (2, 8, 'ongoing'),
       (2, 9, 'fail'),
       (2, 7, 'withdraw');

-- Insert fake data into Application
INSERT INTO Application (student_id, class_id, creation_time, request, status, feedback)
VALUES (1, 2, '2023-01-15 12:00:00', 'add', 'approved', 'Welcome to the class!'),
       (2, 3, '2023-01-15 14:30:00', 'add', 'rejected', 'Prerequisite not met.'),
       (3, 5, '2023-01-16 09:15:00', 'add', 'approved', 'Enjoy the class!'),
       (4, 7, '2023-01-16 11:45:00', 'add', 'approved', 'See you in class!'),
       (5, 8, '2023-01-17 10:30:00', 'add', 'approved', 'Good luck!'),
       (6, 9, '2023-01-17 16:15:00', 'add', 'rejected', 'Class is full.'),
       (7, 10, '2023-01-18 08:00:00', 'add', 'approved', 'Welcome!'),
       (8, 1, '2023-01-18 14:45:00', 'withdraw', 'approved', 'Withdrawal approved.'),
       (9, 3, '2023-01-19 13:30:00', 'add', 'approved', 'Welcome aboard!'),
       (10, 6, '2023-01-19 17:00:00', 'add', 'rejected', 'Class is full.');

