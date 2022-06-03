DROP TABLE IF EXISTS students.students_courses;
DROP TABLE IF EXISTS students.courses;
DROP TABLE IF EXISTS students.groups;
DROP TABLE IF EXISTS students.students;

CREATE TABLE students.courses (
    course_id SERIAL UNIQUE,
    course_name character varying(30) UNIQUE,
    course_description character varying(30)
);

CREATE TABLE students.groups (
    group_id SERIAL UNIQUE,
    group_name character varying(30)
);

CREATE TABLE students.students (
    student_id SERIAL UNIQUE,
    group_id integer,
    first_name character varying(30),
    last_name character varying(30),
    CONSTRAINT students_pkey PRIMARY KEY (student_id)
);

CREATE TABLE students.students_courses (
    course_id INTEGER,
    course_name character varying(30),
    course_description character varying(30),
    student_id INTEGER,
    group_id integer,
    first_name character varying(30),
    last_name character varying(30),
    FOREIGN KEY (course_id) REFERENCES students.courses (course_id) ON DELETE CASCADE,
    FOREIGN KEY (student_id) REFERENCES students.students (student_id) ON DELETE CASCADE
);