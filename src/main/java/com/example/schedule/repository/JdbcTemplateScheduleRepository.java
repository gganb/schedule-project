package com.example.schedule.repository;

import com.example.schedule.dto.ScheduleResponseDto;
import com.example.schedule.entity.Schedule;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import javax.xml.crypto.Data;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Repository
public class JdbcTemplateScheduleRepository implements ScheduleRepository{

    private final JdbcTemplate jdbcTemplate;

    public JdbcTemplateScheduleRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public ScheduleResponseDto saveSchedule(Schedule schedule) {

        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        // usingColumns
        //
        jdbcInsert.withTableName("schedules").usingColumns("username","task","password").usingGeneratedKeyColumns("id");

        // 작성자, 할일 . . 비밀번호 ? 비밀번호와 작성자를 묶으면 ...
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("username",schedule.getUserName());
        parameters.put("task",schedule.getTask());
        parameters.put("password",schedule.getPassword());

        // 저장 후 생성된 key 값을 Number 타입으로 반환하는 메서드 -> 공통 타입으로 추상화
        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));
        String sql = "select id, username, task, createdAt, updatedAt from schedules where id = ?";
        return jdbcTemplate.queryForObject(sql,scheduleRowMapper(),key.longValue());

    }

    private RowMapper<ScheduleResponseDto> scheduleRowMapper(){
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

}
