package com.example.schedule.repository;

import com.example.schedule.dto.ScheduleResponseDto;
import com.example.schedule.entity.Schedule;

import java.util.List;
import java.util.Optional;

public interface ScheduleRepository {
    ScheduleResponseDto saveSchedule(Schedule schedule);

    List<ScheduleResponseDto> findAllTasks();

    List<ScheduleResponseDto> findNameTasks(String userName);

    Schedule findScheduleByIdOrElseThrow(Long id);


}
