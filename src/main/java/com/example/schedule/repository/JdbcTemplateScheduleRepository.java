package com.example.schedule.repository;

import com.example.schedule.dto.ScheduleResponseDto;
import com.example.schedule.entity.Schedule;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;


@Repository
public class JdbcTemplateScheduleRepository implements ScheduleRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcTemplateScheduleRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    /**
     * 일정 저장
     */
    @Override
    public ScheduleResponseDto saveSchedule(Schedule schedule) {

        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("schedules").usingColumns("username", "task", "password").usingGeneratedKeyColumns("id");

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("userName", schedule.getUserName());
        parameters.put("task", schedule.getTask());
        parameters.put("password", schedule.getPassword());

        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));

        String sql = "select id, userName, task, createdAt, updatedAt from schedules where id = ?";
        return jdbcTemplate.queryForObject(sql, scheduleRowMapper(), key.longValue());
    }

    /**
     * 전체 일정 조회
     */
    @Override
    public List<ScheduleResponseDto> findAllTasks() {
        String sql = "select id,userName,task,createdAt,updatedAt from schedules order by updatedAt desc ";
        return jdbcTemplate.query(sql, scheduleRowMapper());
    }

    /**
     * 사용자 이름으로 일정 목록 조회
     */
    @Override
    public List<ScheduleResponseDto> findNameTasks(String userName) {
        String sql = "select id,userName,task,createdAt,updatedAt from schedules where userName = ? order by updatedAt desc";
        return jdbcTemplate.query(sql, scheduleRowMapper(), userName);
    }

    /**
     * 날짜 기준 일정 목록 조회
     */
    @Override
    public List<ScheduleResponseDto> findScheduleByDate(Date updatedAt) {
        String sql = "SELECT id, userName, task, createdAt, updatedAt FROM schedules WHERE DATE(updatedAt) = ?";
        return jdbcTemplate.query(sql, scheduleRowMapper(), updatedAt);
    }

    /**
     * ID로 일정 조회 (예외 발생 포함)
     */
    @Override
    public Schedule findScheduleByIdOrElseThrow(Long id) {
        String sql = "select id, userName,task, createdAt, updatedAt from schedules where id = ?";
        List<Schedule> schedules = jdbcTemplate.query(sql, scheduleEntityRowMapper(), id);
        return schedules.stream()
                .findAny()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "id가 존재하지 않습니다." + id));
    }

    /**
     * 사용자 이름 + ID로 일정 조회 (예외 발생 포함)
     */
    @Override
    public Schedule findScheduleByNameAndIdOrElseThrow(String userName, Long id) {
        String sql = "SELECT id, userName, task, createdAt, updatedAt FROM schedules WHERE userName = ? AND id = ?";
        List<Schedule> schedules = jdbcTemplate.query(sql, scheduleEntityRowMapper(), userName, id);
        return schedules.stream()
                .findAny()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "해당 사용자 이름과 ID에 맞는 일정이 존재하지 않습니다. userName: " + userName + ", id: " + id));
    }

    /**
     * 일정 삭제
     */
    @Override
    public int deleteSchedule(Long id) {
        String sql = "DELETE FROM schedules WHERE id = ?";
        return jdbcTemplate.update(sql, id);
    }

    /**
     * 비밀번호 조회
     */
    @Override
    public String getPasswordById(Long id) {
        String sql = "SELECT password FROM schedules WHERE id = ?";
        List<String> result = jdbcTemplate.query(sql, (rs, rowNum) -> rs.getString("password"), id);
        return result.stream()
                .findAny()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "비밀번호가 존재하지 않습니다. ID: " + id));
    }

    /**
     * 일정 수정 (이름, 할 일)
     */
    @Override
    public int updateSchedule(Long id, String userName, String task) {
        String sql = "UPDATE schedules SET userName = ?, task = ? WHERE id = ?";
        return jdbcTemplate.update(sql, userName, task, id);
    }

    /**
     * ScheduleResponseDto 매핑
     */
    private RowMapper<ScheduleResponseDto> scheduleRowMapper() {
        return new RowMapper<ScheduleResponseDto>() {
            @Override
            public ScheduleResponseDto mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new ScheduleResponseDto(
                        rs.getLong("id"),
                        rs.getString("userName"),
                        rs.getString("task"),
                        rs.getTimestamp("createdAt"),
                        rs.getTimestamp("updatedAt")
                );
            }
        };
    }

    /**
     * Schedule 엔티티 매핑
     */
    private RowMapper<Schedule> scheduleEntityRowMapper() {
        return new RowMapper<Schedule>() {
            @Override
            public Schedule mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new Schedule(
                        rs.getLong("id"),
                        rs.getString("userName"),
                        rs.getString("task"),
                        rs.getTimestamp("createdAt"),
                        rs.getTimestamp("updatedAt")
                );
            }
        };
    }
}
