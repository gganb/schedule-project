package com.example.schedule.service;

import com.example.schedule.dto.ScheduleRequestDto;
import com.example.schedule.dto.ScheduleResponseDto;
import com.example.schedule.entity.Schedule;
import com.example.schedule.repository.JdbcTemplateScheduleRepository;
import com.example.schedule.repository.ScheduleRepository;
import org.springframework.stereotype.Service;

@Service
public class ScheduleServiceImpl implements ScheduleService{


    private final ScheduleRepository scheduleRepository;

    public ScheduleServiceImpl(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    // 순서 일치하기
    @Override
    public ScheduleResponseDto saveSchedule(ScheduleRequestDto scheduleRequestDto) {
       // 작성자 이름, 할일, 비밀번호
        Schedule schedule = new Schedule(scheduleRequestDto.getUserName(),scheduleRequestDto.getTask(),scheduleRequestDto.getPassword());
        return scheduleRepository.saveSchedule(schedule);

    }
}
