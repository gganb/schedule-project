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

    @Override
    public ScheduleResponseDto saveSchedule(Schedule schedule) {

        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("schedules").usingColumns("username", "task", "password").usingGeneratedKeyColumns("id");

        // 작성자, 할일 . . 비밀번호 ? 비밀번호와 작성자를 묶으면 ...
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("userName", schedule.getUserName());
        parameters.put("task", schedule.getTask());
        parameters.put("password", schedule.getPassword());

        // 저장 후 생성된 key 값을 Number 타입으로 반환하는 메서드 -> 공통 타입으로 추상화
        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));
        String sql = "select id, userName, task, createdAt, updatedAt from schedules where id = ?";
        return jdbcTemplate.queryForObject(sql, scheduleRowMapper(), key.longValue());

    }

    // 모든 작성 글 내림차순 정렬
    @Override
    public List<ScheduleResponseDto> findAllTasks() {
        List<ScheduleResponseDto> task = jdbcTemplate.query("select id,userName,task,createdAt,updatedAt from schedules order by updatedAt desc ", scheduleRowMapper());
        return task;
    }

    // 이름 - 수정일 기준 내림차순 정렬
    @Override
    public List<ScheduleResponseDto> findNameTasks(String userName) {
        return jdbcTemplate.query("select id,userName,task,createdAt,updatedAt from schedules where userName = ? order by updatedAt desc", scheduleRowMapper(), userName);

    }


    // id 로 특정 글 검색
    @Override
    public Schedule findScheduleByIdOrElseThrow(Long id) {
        List<Schedule> listSchedules = jdbcTemplate.query("select id, userName,task, createdAt, updatedAt from schedules where id = ?", findScheduleRowMapper(), id);
        return listSchedules.stream().findAny().orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "id가 존재하지 않습니다." + id));
    }

    // 특정 userName의 id로 글 검색
    @Override
    public Schedule findScheduleByNameAndIdOrElseThrow(String userName, Long id) {
        List<Schedule> listScheduleName = jdbcTemplate.query("select id, userName,task, createdAt, updatedAt from schedules where userName = ? and id = ?", findScheduleRowMapper(), userName, id);
        return listScheduleName.stream().findAny().orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 userName에 맞는 id는 존재하지 않습니다." + userName + id));

    }

    /**
     * @return {@link RowMapper<ScheduleResponseDto>}
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
     * @return {@link RowMapper<Schedule>}
     */
    private RowMapper<Schedule> findScheduleRowMapper() {
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
