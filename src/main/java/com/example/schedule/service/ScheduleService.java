package com.example.schedule.service;

import com.example.schedule.dto.ScheduleRequestDto;
import com.example.schedule.dto.ScheduleResponseDto;

import java.util.Date;
import java.util.List;

public interface ScheduleService {

    // 일정 생성
    ScheduleResponseDto saveSchedule(ScheduleRequestDto scheduleRequestDto);

    // 전체 일정 조회
    List<ScheduleResponseDto> findAllTasks();

    // id로 일정 단건 조회
    ScheduleResponseDto findScheduleById(Long id);

    // 일정 수정 (이름, 할 일만 수정 가능)
    ScheduleResponseDto updateSchedule(Long id, String userName, String task, String password);

    // 일정 삭제
    void deleteSchedule (Long id, String password);

    // 사용자 이름으로 일정 목록 조회
    List<ScheduleResponseDto> findNameTasks(String userName);

    // 특정 날짜의 일정 목록 조회
    List<ScheduleResponseDto> findScheduleByDate (Date updatedAt) ;

    // 사용자 이름 + id 로 일정 단건 조회
    ScheduleResponseDto findScheduleByNameAndId(String userName, Long id);

  }
