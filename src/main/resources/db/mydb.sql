/*CREATE TABLE employees(
    id serial primary key,
    name varchar(15),
    surname varchar(25),
    department varchar(20),
    salary int
);
INSERT INTO employees (name, surname, department, salary)
VALUES
    ('Zaur', 'Tregulov', 'IT', 500),
    ('Oleg', 'Ivanov', 'Sales', 700),
    ('Nina', 'Sidorova', 'HR', 850);
*/
CREATE table post(
                     post_id serial primary key,
                     title text,
                     body text,
                     picture varchar(400),
                     date_time timestamp,
                     user_id int,
                     FOREIGN KEY (user_id) REFERENCES user_main(user_id)
);

CREATE table user_main(
                          user_id serial primary key,
                          username varchar(50),
                          password varchar(300),
                          key_user_detail_id int,
                          FOREIGN KEY (key_user_detail_id) REFERENCES user_detail(user_detail_id)
);

CREATE table user_detail(
                            user_detail_id serial primary key ,
                            age int,
                            name varchar(50),
                            surname varchar(50),
                            city varchar(50),
                            description text,
                            email varchar(50),
                            date_of_registration date,
                            profile_image varchar(300)
);

CREATE TABLE user_likes (
                            like_id serial primary key,
                            user_id int NOT NULL,
                            post_id int NOT NULL,
                            FOREIGN KEY (post_id) REFERENCES post(post_id),
                            FOREIGN KEY (user_id) REFERENCES user_main(user_id));


/*CREATE TABLE users (
  username varchar(15) primary key,
  password varchar(100),
  enabled smallint
);

CREATE TABLE authorities (
    username varchar(15),
    authority varchar(25),
    FOREIGN KEY (username) references users(username)
);

INSERT INTO users (username, password, enabled)
VALUES
    ('vlas', '{noop}vlas', 1),
    ('elena', '{noop}elena', 1),
    ('ivan', '{noop}ivan', 1);

INSERT INTO authorities (username, authority)
VALUES
    ('vlas', 'ROLE_EMPLOYEE'),
    ('elena', 'ROLE_HR'),
    ('ivan', 'ROLE_HR'),
    ('ivan', 'ROLE_MANAGER');*/


/*CREATE table departments(
    id serial primary key,
    name varchar(15),
    max_salary int,
    min_salary int
);

CREATE TABLE children (
  id serial primary key ,
  name varchar(15),
  age int
);

CREATE TABLE section (
  id serial primary key ,
  name varchar(15)
);

CREATE TABLE child_section (
  child_id int NOT NULL,
  section_id int NOT NULL,
  PRIMARY KEY (child_id, section_id),
  FOREIGN KEY (child_id) REFERENCES children(id),
  FOREIGN KEY (section_id) REFERENCES  section(id));
*/