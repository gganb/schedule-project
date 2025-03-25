package com.example.schedule.service;

import com.example.schedule.dto.ScheduleRequestDto;
import com.example.schedule.dto.ScheduleResponseDto;
import com.example.schedule.entity.Schedule;
import com.example.schedule.repository.ScheduleRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ScheduleServiceImpl implements ScheduleService {

    private final ScheduleRepository scheduleRepository;

    public ScheduleServiceImpl(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    /**
     * 일정 생성
     */
    @Override
    public ScheduleResponseDto saveSchedule(ScheduleRequestDto scheduleRequestDto) {
        Schedule schedule = new Schedule(scheduleRequestDto.getUserName(),
                scheduleRequestDto.getTask(),
                scheduleRequestDto.getPassword()
        );
        return scheduleRepository.saveSchedule(schedule);
    }

    /**
     * 전체 일정 조회
     */
    @Override
    public List<ScheduleResponseDto> findAllTasks() {
        return scheduleRepository.findAllTasks();
    }

    /**
     * id로 일정 단건 조회
     */
    @Override
    public ScheduleResponseDto findScheduleById(Long id) {
        Schedule schedule = scheduleRepository.findScheduleByIdOrElseThrow(id);
        return new ScheduleResponseDto(schedule);
    }

    /**
     * 일정 수정 (이름, 할 일만 수정 가능 / 비밀번호 검증)
     */
    @Transactional
    @Override
    public ScheduleResponseDto updateSchedule(Long id, String userName, String task, String password) {
        if (!password.equals(scheduleRepository.getPasswordById(id))) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"비밀번호가 일치하지 않습니다");
        }

        if (userName == null || task == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "이름과 할 일을 입력해주세요");
        }

        int updatedRow = scheduleRepository.updateSchedule(id, userName, task);
        if (updatedRow == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "존재하지 않는 일정입니다. ID: " + id);
        }

        Schedule schedule = scheduleRepository.findScheduleByIdOrElseThrow(id);
        return new ScheduleResponseDto(schedule);
    }

    /**
     * 사용자 이름으로 일정 목록 조회
     */
    @Override
    public List<ScheduleResponseDto> findNameTasks(String userName) {
        return scheduleRepository.findNameTasks(userName);
    }

    /**
     * 날짜 기준 일정 목록 조회
     */
    @Override
    public List<ScheduleResponseDto> findScheduleByDate(Date updatedAt){
        return scheduleRepository.findScheduleByDate(updatedAt);
    }


    /**
     * 사용자 이름 + ID로 일정 단건 조회
     */
    @Override
    public ScheduleResponseDto findScheduleByNameAndId(String userName, Long id) {
        Schedule schedule = scheduleRepository.findScheduleByNameAndIdOrElseThrow(userName, id);
        return new ScheduleResponseDto(schedule);
    }

    /**
     * 일정 삭제 (비밀번호 검증)
     */
    @Override
    public void deleteSchedule(Long id, String password) {
        if (!password.equals(scheduleRepository.getPasswordById(id))) {
            throw new NoSuchElementException("비밀번호가 일치하지 않습니다");
        }

        int deletedRow = scheduleRepository.deleteSchedule(id);

        if (deletedRow == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "선택한 일정이 존재하지 않습니다. ID: " + id);
        }
    }
}
