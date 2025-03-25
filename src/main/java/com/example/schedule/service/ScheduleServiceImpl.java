package com.example.schedule.service;

import com.example.schedule.dto.ScheduleRequestDto;
import com.example.schedule.dto.ScheduleResponseDto;
import com.example.schedule.entity.Schedule;
import com.example.schedule.repository.JdbcTemplateScheduleRepository;
import com.example.schedule.repository.ScheduleRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.NoSuchElementException;

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

    @Override
    public List<ScheduleResponseDto> findAllTasks() {
        return scheduleRepository.findAllTasks();
    }

    @Override
    public ScheduleResponseDto findScheduleById(Long id) {
        Schedule schedule = scheduleRepository.findScheduleByIdOrElseThrow(id);
        return new ScheduleResponseDto(schedule);
    }

    // 선택 글 수정
    // 비밀번호가 일치하면 수정이 가능하도록 ... db에서 id와 비밀번호를 가져와야하는디?
    // 이름이랑 할일만 수정가능
    // 성공시 수정일 변경
    @Transactional
    @Override
    public ScheduleResponseDto updateSchedule(Long id, String userName, String task, String password) {

        return null;
    }


    // 이름으로 조회
    @Override
    public List<ScheduleResponseDto> findNameTasks(String userName) {
        return scheduleRepository.findNameTasks(userName);
    }


    /**
     *  특정 사용자의 단건 글 조회
     * @param name
     * @param id
     * @return  {@link ScheduleResponseDto}
     */
    @Override
    public ScheduleResponseDto findScheduleByNameAndId(String name, Long id) {
        Schedule scheduleName = scheduleRepository.findScheduleByNameAndIdOrElseThrow(name,id);
        return new ScheduleResponseDto(scheduleName);
    }

    //입력받은 id에 맞는 비밀번호 값 가져와서 일치하면 삭제......???
    @Override
    public void deleteSchedule(Long id, String password) {
        if(!password.equals(scheduleRepository.isEqualPassword(id))){
            throw new NoSuchElementException("비밀번호가 일치하지 않습니다");
        }
       int deletedRow = scheduleRepository.deleteSchedule(id);

       if(deletedRow == 0){
           throw new ResponseStatusException(HttpStatus.NOT_FOUND, "선택한 일정이 존재하지 않습니다" + id);
       }
    }
}
