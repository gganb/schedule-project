package com.example.schedule.repository;

import com.example.schedule.dto.ScheduleResponseDto;
import com.example.schedule.entity.Schedule;

import java.util.Date;
import java.util.List;

public interface ScheduleRepository {

    // 일정 저장
    ScheduleResponseDto saveSchedule(Schedule schedule);

    // 전체 일정 조회
    List<ScheduleResponseDto> findAllTasks();

    // 사용자 이름으로 일정 목록 조회
    List<ScheduleResponseDto> findNameTasks(String userName);

    // 날짜 기준 일정 조회
    List<ScheduleResponseDto> findScheduleByDate (Date updatedAt);

    // id로 일정 조회
    Schedule findScheduleByIdOrElseThrow(Long id);

    // 사용자 이름 + id로 단건 조회
    Schedule findScheduleByNameAndIdOrElseThrow(String userName, Long id);

    // 일정 삭제
    int deleteSchedule(Long id);

    // 해당 일정의 비밀번호 조회
    String getPasswordById(Long id);

    // 일정 수정 ( 이름, 할일)
    int updateSchedule(Long id, String userName, String task);

}
