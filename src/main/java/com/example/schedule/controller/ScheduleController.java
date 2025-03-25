package com.example.schedule.controller;


import com.example.schedule.dto.ScheduleRequestDto;
import com.example.schedule.dto.ScheduleResponseDto;
import com.example.schedule.service.ScheduleService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/schedule")
public class ScheduleController {

    private final ScheduleService scheduleService;

    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    /**
     * 일정 생성
     *
     * @param {@link scheduleRequestDto} 할 일 생성 요청 객체
     * @return {@link ResponseEntity<ScheduleResponseDto>} JSON 응답
     */
    @PostMapping
    public ResponseEntity<ScheduleResponseDto> createSchedule(
            @RequestBody ScheduleRequestDto scheduleRequestDto
    ) {
        return new ResponseEntity<>(scheduleService.saveSchedule(scheduleRequestDto), HttpStatus.CREATED);
    }

    /**
     * 전체 일정 조회
     */
    @GetMapping
    public List<ScheduleResponseDto> findAllTasks() {
        return scheduleService.findAllTasks();
    }

    /**
     * 사용자 이름으로 일정 목록 조회
     *
     * @param userName
     */
    @GetMapping("/user/{userName}")
    public List<ScheduleResponseDto> findNameTasks(
            @PathVariable String userName
    ) {
        return scheduleService.findNameTasks(userName);
    }

    /**
     * id로 일정 단건 조회 API
     *
     * @param {@link id}
     * @return {@link ResponseEntity<ScheduleResponseDto>} JSON 응답
     */
    @GetMapping("/{id}")
    public ResponseEntity<ScheduleResponseDto> findScheduleById(
            @PathVariable Long id
    ) {
        return new ResponseEntity<>(scheduleService.findScheduleById(id), HttpStatus.OK);
    }

    /**
     * 사용자 이름 + ID로 일정 단건 조회
     * 예: /api/schedule/user?userName=name&id=1
     */
    @GetMapping("/user")
    public ResponseEntity<ScheduleResponseDto> findScheduleByNameAndId(
            @RequestParam String userName,
            @RequestParam Long id
    ) {
        return new ResponseEntity<>(scheduleService.findScheduleByNameAndId(userName, id), HttpStatus.OK);
    }

    /**
     * 날짜 기준 일정 조회
     * 예: /api/schedule/date?updatedAt=2025-03-25
     */
    @GetMapping("/date")
    public List<ScheduleResponseDto> findScheduleByDate(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date updatedAt
    ) {
        return scheduleService.findScheduleByDate(updatedAt);
    }

    /**
     * 일정 수정 (이름, 할 일 수정 가능 / 비밀번호 확인 필요)
     */

    @PatchMapping("/{id}")
    public ResponseEntity<ScheduleResponseDto> updateSchedule(
            @PathVariable Long id,
            @RequestBody ScheduleRequestDto scheduleRequestDto
    ) {
        return new ResponseEntity<>(scheduleService.updateSchedule(
                id,
                scheduleRequestDto.getUserName(),
                scheduleRequestDto.getTask(),
                scheduleRequestDto.getPassword()),
                HttpStatus.OK);
    }

    /**
     * 일정 삭제 (비밀번호 확인 필요)
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSchedule(
            @PathVariable Long id,
            @RequestBody ScheduleRequestDto scheduleRequestDto
    ) {
        scheduleService.deleteSchedule(id, scheduleRequestDto.getPassword());
        return new ResponseEntity<>(HttpStatus.OK);
    }

}

