package com.example.schedule.dto;

import com.example.schedule.entity.Schedule;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

import java.sql.Timestamp;
import java.time.LocalDateTime;


@Getter
public class ScheduleResponseDto {

    private Long id;
    private String userName;
    private String task;

    // 시간 변환 포맷
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedAt;

    // Entity -> DTO
    public ScheduleResponseDto(Schedule schedule){
        this.id = schedule.getId();
        this.userName = schedule.getUserName();
        this.task = schedule.getTask();
        this.createdAt = toLocalDateTime(schedule.getCreatedAt());
        this.updatedAt = toLocalDateTime(schedule.getUpdatedAt());
    }

    // 필드로 초기화
    public ScheduleResponseDto(Long id, String userName, String task, Timestamp createdAt, Timestamp updatedAt) {
        this.id = id;
        this.userName = userName;
        this.task = task;
        this.createdAt = toLocalDateTime(createdAt);
        this.updatedAt = toLocalDateTime(updatedAt);
    }


    private LocalDateTime toLocalDateTime(Timestamp timestamp) {
        return timestamp != null ? timestamp.toLocalDateTime() : null;
    }

}
