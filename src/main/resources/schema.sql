DROP DATABASE IF EXISTS WebRegDB;

CREATE DATABASE IF NOT EXISTS WebRegDB;

USE WebRegDB;


CREATE TABLE Department (-- CHECK
                            id INT AUTO_INCREMENT PRIMARY KEY,
                            name VARCHAR(255) NOT NULL,
                            school VARCHAR(255) NOT NULL
);


CREATE TABLE Course (-- CHECK
                        id INT AUTO_INCREMENT PRIMARY KEY,
                        name VARCHAR(255) NOT NULL,
                        code VARCHAR(100) NOT NULL,
                        department_id INT,
                        description TEXT,
                        FOREIGN KEY (department_id) REFERENCES Department(id)
);


CREATE TABLE Prerequisite (-- CHECK
                              id INT AUTO_INCREMENT PRIMARY KEY,
                              course_id INT,
                              pre_course_id INT,
                              FOREIGN KEY (course_id) REFERENCES Course(id),
                              FOREIGN KEY (pre_course_id) REFERENCES Course(id),
                              UNIQUE (course_id, pre_course_id)
);

CREATE TABLE Semester (-- CHECK
                          id INT AUTO_INCREMENT PRIMARY KEY,
                          start_date DATE NOT NULL,
                          end_date DATE NOT NULL,
                          name VARCHAR(20) NOT NULL
);

CREATE TABLE Professor (-- CHECK
                           id INT AUTO_INCREMENT PRIMARY KEY,
                           first_name VARCHAR(255) NOT NULL,
                           last_name VARCHAR(255) NOT NULL,
                           email VARCHAR(255) NOT NULL UNIQUE,
                           department_id INT,
                           FOREIGN KEY (department_id) REFERENCES Department(id)
);

CREATE TABLE Classroom (-- CHECK
                           id INT AUTO_INCREMENT PRIMARY KEY,
                           name VARCHAR(50) NOT NULL,
                           building VARCHAR(255) NOT NULL,
                           capacity INT NOT NULL
);

CREATE TABLE WebRegClass (-- CHECK
                             id INT AUTO_INCREMENT PRIMARY KEY,
                             course_id INT,
                             semester_id INT,
                             professor_id INT,
                             classroom_id INT,
                             capacity INT NOT NULL,
                             enrollment_num INT NOT NULL DEFAULT 0,
                             is_active BOOLEAN NOT NULL DEFAULT true,
                             FOREIGN KEY (course_id) REFERENCES Course(id),
                             FOREIGN KEY (semester_id) REFERENCES Semester(id),
                             FOREIGN KEY (professor_id) REFERENCES Professor(id),
                             FOREIGN KEY (classroom_id) REFERENCES Classroom(id)
);

CREATE TABLE Lecture (-- CHECK
                         id INT AUTO_INCREMENT PRIMARY KEY,
                         class_id INT,
                         day_of_the_week INT NOT NULL,
                         start_time TIME NOT NULL,
                         end_time TIME NOT NULL,
                         FOREIGN KEY (class_id) REFERENCES WebRegClass(id)
);

CREATE TABLE Student (-- CHECK
                         id INT AUTO_INCREMENT PRIMARY KEY,
                         first_name VARCHAR(255) NOT NULL,
                         last_name VARCHAR(255) NOT NULL,
                         email VARCHAR(255) NOT NULL UNIQUE,
                         password VARCHAR(255) NOT NULL,
                         department_id INT,
                         is_active BOOLEAN NOT NULL,
                         is_admin BOOLEAN NOT NULL,
                         FOREIGN KEY (department_id) REFERENCES Department(id)
);

CREATE TABLE StudentClass (-- CHECK
                              id INT AUTO_INCREMENT PRIMARY KEY,
                              student_id INT,
                              class_id INT,
                              status ENUM('ongoing', 'pass', 'fail', 'withdraw') NOT NULL,
                              FOREIGN KEY (student_id) REFERENCES Student(id),
                              FOREIGN KEY (class_id) REFERENCES WebRegClass(id)
);

CREATE TABLE Application (-- CHECK
                             id INT AUTO_INCREMENT PRIMARY KEY,
                             student_id INT,
                             class_id INT,
                             creation_time DATETIME NOT NULL,
                             request ENUM('add', 'withdraw') NOT NULL,
                             status ENUM('pending', 'approved', 'rejected') NOT NULL,
                             feedback TEXT,
                             FOREIGN KEY (student_id) REFERENCES Student(id),
                             FOREIGN KEY (class_id) REFERENCES WebRegClass(id)
);




