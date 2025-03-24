package com.example.schedule.controller;


import com.example.schedule.dto.ScheduleRequestDto;
import com.example.schedule.dto.ScheduleResponseDto;
import com.example.schedule.service.ScheduleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/schedule")
public class ScheduleController {

    private final ScheduleService scheduleService;

    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    /**
     * 일정 생성 API
     *
     * @param  {@link scheduleRequestDto} 할 일 생성 요청 객체
     * @return  {@link ResponseEntity<ScheduleResponseDto>} JSON 응답
     */
    @PostMapping
    public ResponseEntity<ScheduleResponseDto> createSchedule(
            @RequestBody ScheduleRequestDto scheduleRequestDto
    ) {
        return new ResponseEntity<>(scheduleService.saveSchedule(scheduleRequestDto), HttpStatus.CREATED);
    }

    /**
     * 일정 전체 조회 API
     * @return {@link List<ScheduleResponseDto>}  JSON 응답
     */
    @GetMapping
    public List<ScheduleResponseDto> findAllTasks(){
        return scheduleService.findAllTasks();
    }

    /**
     * 일정 단건 조회 API
     * @param {@link id}
     * @return {@link ResponseEntity<ScheduleResponseDto>} JSON 응답
     */
    @GetMapping("/{id}")
    public ResponseEntity<ScheduleResponseDto> findScheduleById(
            @PathVariable Long id
    ){
        return new ResponseEntity<>(scheduleService.findScheduleById(id),HttpStatus.OK);
    }

}

