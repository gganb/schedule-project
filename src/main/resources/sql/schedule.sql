create table users
(
    id       BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '작성자 id',
    username VARCHAR(50) NOT NULL COMMENT '작성자 이름',
    password VARCHAR(60) NOT NULL COMMENT '비밀번호'
);

create table schedules (
                           id BIGINT  AUTO_INCREMENT PRIMARY KEY COMMENT '일정 id',
                           userId BIGINT not null COMMENT '작성자 id',
                           task TEXT comment '할일',
                           createdAt datetime default current_timestamp comment '작성된 시간',
                           updatedAt datetime default current_timestamp on update current_timestamp comment '수정된 시간',
                           foreign key (userId) references users(id)
);