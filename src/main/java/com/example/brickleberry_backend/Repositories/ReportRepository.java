package com.example.brickleberry_backend.Repositories;

import com.example.brickleberry_backend.Entities.Report;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
@AllArgsConstructor
public class ReportRepository {
    private final JdbcTemplate jdbcTemplate;

    public List<Report> getAllReportsByPersonId(long personId) {
        String query = "SELECT * FROM Report WHERE person_id = " + personId;
        return this.jdbcTemplate.query(query, this::wrapReport);
    }

    private Report wrapReport(ResultSet rs, int rowNum) throws SQLException {
        Report r = new Report();
        r.setId(rs.getLong("id"));
        r.setPersonId(rs.getLong("person_id"));
        r.setInvResultId(rs.getLong("inv_result_id"));
        r.setDate(rs.getDate("date"));
        r.setDescription(rs.getString("description"));
        return r;
    }
}
