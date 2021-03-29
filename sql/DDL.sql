create table member (
                        id bigint NOT NULL AUTO_INCREMENT,
                        uid varchar(255),
                        name varchar(255),
                        age TINYINT UNSIGNED Not NULL  DEFAULT 100,
                        gender VARCHAR(4) Not NULL  DEFAULT "남자",
                        mbti VARCHAR(4) Not NULL  DEFAULT "INFP",
                        stateMessage varchar(255),
                        profileImage VARCHAR (100) Not NULL  DEFAULT 'defaultProfileImage.png',
                        lastConnectTime TIMESTAMP Not NULL  DEFAULT NOW(),
                        primary key (id)
);