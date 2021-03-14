create table member (
                        id bigint NOT NULL AUTO_INCREMENT,
                        uid varchar(255),
                        name varchar(255),
                        age TINYINT UNSIGNED,
                        gender VARCHAR(4),
                        mbti VARCHAR(4),
                        stateMessage varchar(255),
                        primary key (id)
);