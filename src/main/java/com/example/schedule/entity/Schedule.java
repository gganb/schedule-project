package com.example.schedule.entity;



import lombok.AllArgsConstructor;
import lombok.Getter;

import java.sql.Timestamp;


@Getter
@AllArgsConstructor
public class Schedule {

    // id, 이름, 할일, 비밀번호, 작성일, 수정일
    private Long id;
    private String userName;
    private String password;
    private String task;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    /**
     * 글 작성을 위한 생성자
     */
    public Schedule(String userName, String task, String password){
        this.userName = userName;
        this.task = task;
        this.password = password;
    }

    /**
     * DB 조회
     */
    public Schedule(Long id, String userName, String task, Timestamp createdAt, Timestamp updatedAt) {
        this.id = id;
        this.userName = userName;
        this.task = task;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

}
