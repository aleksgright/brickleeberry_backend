package com.example.brickleberry_backend.Repositories;

import com.example.brickleberry_backend.Entities.Warehouse;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
@AllArgsConstructor
public class WarehouseRepository {
    private final JdbcTemplate jdbcTemplate;

    public List<Warehouse> getWarehouseByTerritoryId(int territory_id) {
        String query = "SELECT * FROM Warehouse WHERE territory_id = " + territory_id;
        return this.jdbcTemplate.query(query, this::wrapWarehouse);
    }

    private Warehouse wrapWarehouse(ResultSet rs, int rowNum) throws SQLException {
        Warehouse w = new Warehouse();
        w.setId(rs.getInt("id"));
        w.setName(rs.getString("name"));
        w.setTerritoryId(rs.getInt("territory_id"));
        w.setResponsiblePersonId(rs.getLong("responsible_person"));
        return w;
    }
}
