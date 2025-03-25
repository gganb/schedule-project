
create table schedules (
                           id BIGINT  AUTO_INCREMENT PRIMARY KEY COMMENT '일정 id',
                           username VARCHAR(50) NOT NULL COMMENT '작성자 이름',
                           password VARCHAR(60) NOT NULL COMMENT '비밀번호',
                           task TEXT comment '할일',
                           createdAt timestamp default current_timestamp comment '작성된 시간',
                           updatedAt datetime default current_timestamp on update current_timestamp comment '수정된 시간'


);
SHOW CREATE TABLE schedules;

TRUNCATE TABLE schedules;

ALTER TABLE schedules CHANGE username userName VARCHAR(50);
