package com.example.schedule.entity;


import com.example.schedule.service.ScheduleService;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.sql.Timestamp;


@Getter
@AllArgsConstructor
// id, 이름, 할일, 비번, 작성일, 수정일
public class Schedule {

    private Long id;
    //  private Long userId;    // 외래키
    private String userName;
    private String password;
    private String task;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    /**
     * 글 작성을 위한 생성자
     * @param userName
     * @param password
     * @param task
     */
    public Schedule(String userName, String task, String password){
        this.userName = userName;
        this.task = task;
        this.password = password;
    }


    public Schedule(Long id, String userName, String task, Timestamp createdAt, Timestamp updatedAt) {
        this.id = id;
        this.userName = userName;
        this.task = task;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
