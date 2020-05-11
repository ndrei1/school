CREATE TABLE groups
(
    ID   serial PRIMARY KEY,
    NAME text
);

CREATE TABLE students
(
    ID      serial PRIMARY KEY,
    NAME    text NOT NULL,
    SURNAME text NOT NULL,
    idGroup int  not null,
    FOREIGN KEY (idGroup) REFERENCES groups (ID)
);

CREATE TABLE courses
(
    ID          serial,
    NAME        text PRIMARY KEY,
    description text NOT NULL
);

create table courses_students
(
    student_id   int REFERENCES students (id) ON UPDATE CASCADE ON DELETE CASCADE,
    courses_name text REFERENCES courses (name) ON UPDATE CASCADE,
    CONSTRAINT student_courses_pkey PRIMARY KEY (student_id, courses_name)
);