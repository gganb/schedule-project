package com.example.schedule.dto;

import com.example.schedule.entity.Schedule;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

import java.sql.Timestamp;
import java.time.LocalDateTime;


@Getter
public class ScheduleResponseDto {
    private Long id;    // 글 번호
    private String userName;
    private String task;

    // 시간 변환 포맷
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedAt;

    public ScheduleResponseDto(Schedule schedule){
        this.id = schedule.getId();
        this.userName = schedule.getUserName();
        this.task = schedule.getTask();
        this.createdAt = convertToLocalDateTime(schedule.getCreatedAt());
        this.updatedAt = convertToLocalDateTime(schedule.getUpdatedAt());

    }

    public ScheduleResponseDto(Long id, String userName, String task, Timestamp createdAt, Timestamp updatedAt) {
        this.id = id;
        this.userName = userName;
        this.task = task;
        this.createdAt = convertToLocalDateTime(createdAt);
        this.updatedAt = convertToLocalDateTime(updatedAt);
    }


    private LocalDateTime convertToLocalDateTime(Timestamp timestamp) {
        return timestamp != null ? timestamp.toLocalDateTime() : null;
    }

}
