package com.example.schedule.controller;


import com.example.schedule.dto.ScheduleRequestDto;
import com.example.schedule.dto.ScheduleResponseDto;
import com.example.schedule.service.ScheduleService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
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
     * 일정 생성 API
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
     * 일정 전체 조회 API
     *
     * @return {@link List<ScheduleResponseDto>}  JSON 응답
     */
    @GetMapping
    public List<ScheduleResponseDto> findAllTasks() {
        return scheduleService.findAllTasks();
    }

    /**
     * 특정 사용자의 전체 글 조회
     *
     * @param userName
     * @return
     */
    @GetMapping("/user/{userName}")
    public List<ScheduleResponseDto> findNameTasks(
            @PathVariable String userName
    ) {
        return scheduleService.findNameTasks(userName);
    }

    /**
     * 일정 단건 조회 API
     *
     * @param {@link id}
     * @return {@link ResponseEntity<ScheduleResponseDto>} JSON 응답
     */
    @GetMapping("/id/{id}")
    public ResponseEntity<ScheduleResponseDto> findScheduleById(
            @PathVariable Long id
    ) {
        return new ResponseEntity<>(scheduleService.findScheduleById(id), HttpStatus.OK);
    }

    /**
     *  특정 사용자의 글 단건 조회
     * @param userName
     * @param id
     * @return
     */
    @GetMapping("/user/{userName}/{id}")
    public ResponseEntity<ScheduleResponseDto> findScheduleByNameAndId(
            @PathVariable String userName,
            @PathVariable Long id
    ){
        return new ResponseEntity<>(scheduleService.findScheduleByNameAndId(userName,id),HttpStatus.OK);
    }

    @GetMapping("/date")
    public List<ScheduleResponseDto> findScheduleByDate(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date updatedAt
    ) {
        return scheduleService.findScheduleByDate(updatedAt);
    }

    /**
     *  **선택한 일정 수정**
     *   선택한 일정 내용 중 `할일`, `작성자명` 만 수정 가능
     *   서버에 일정 수정을 요청할 때 `비밀번호`를 함께 전달합니다.
     *   `작성일` 은 변경할 수 없으며, `수정일` 은 수정 완료 시, 수정한 시점으로 변경합니다.
     */

    @PatchMapping("/id/{id}")
    public ResponseEntity<ScheduleResponseDto> updateSchedule(
            @PathVariable Long id,
            @RequestBody ScheduleRequestDto scheduleRequestDto
    ) {
        return new ResponseEntity<>(scheduleService.updateSchedule(
                id,
                scheduleRequestDto.getUserName(),   // 작성자명
                scheduleRequestDto.getTask(),   // 할 일
                scheduleRequestDto.getPassword()),  // 비밀번호
                HttpStatus.OK);
    }

    /**
     * 선택한 글 삭제 API
     * @param {@link id}
     * @param {@link scheduleRequestDto}
     * @return  void
     */
    @DeleteMapping("/id/{id}")
    public ResponseEntity<Void> deleteSchedule(
            @PathVariable Long id,
            @RequestBody ScheduleRequestDto scheduleRequestDto
    ){
        scheduleService.deleteSchedule(id,scheduleRequestDto.getPassword());
        return new ResponseEntity<>(HttpStatus.OK);
    }

}

