drop table if exists member CASCADE;
create table member (
                        id bigint generated by default as identity,
                        uid varchar(255),
                        name varchar(255),
                        age TINYINT UNSIGNED,
                        gender VARCHAR(4),
                        address VARCHAR(255),
                        mbti VARCHAR(4),
                        primary key (id)
);