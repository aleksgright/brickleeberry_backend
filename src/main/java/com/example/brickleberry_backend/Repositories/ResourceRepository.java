package com.example.brickleberry_backend.Repositories;

import com.example.brickleberry_backend.Dtos.ResourceAddDto;
import com.example.brickleberry_backend.Entities.Resource;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
@AllArgsConstructor
public class ResourceRepository {
    private final JdbcTemplate jdbcTemplate;

    public List<Resource> getResourcesByWarehouseIdPaged(int warehouseId, int limit, int offset) {
        String query = "SELECT * FROM Resource WHERE warehouse_id = " + warehouseId +
                "  LIMIT " + limit +
                " OFFSET " + offset;
        return this.jdbcTemplate.query(query, this::wrapResource);
    }

    public int getResourcesByWarehouseIdPagesCount(int warehouseId, int pageSize) {
        String rowCountSql = "SELECT ceiling(count(*)/" + pageSize + ") AS row_count " +
                "FROM Resource WHERE warehouse_id = " + warehouseId;
        Integer result = this.jdbcTemplate.queryForObject(rowCountSql, Integer.class);
        return result == null ? 0 : result;
    }

    public boolean addResource(ResourceAddDto resourceAddDto) {
        return this.jdbcTemplate.update("INSERT INTO Resource(name, count, warehouse_id) VALUES(?, ?, ?)",
                resourceAddDto.getName(),
                resourceAddDto.getCount(),
                resourceAddDto.getWarehouse_id()) == 1;
    }

    public boolean updateResource(long resourceId, int warehouseId, int change) {
        return this.jdbcTemplate.update(
                "UPDATE Resource SET count = count + " + change +
                        " WHERE id = " + resourceId +
                        " AND warehouse_id = " + warehouseId) == 1;
    }

//    public boolean getRfidChips() {
//        return
//    }

    private Resource wrapResource(ResultSet rs, int rowNum) throws SQLException {
        Resource r = new Resource();
        r.setId(rs.getLong("id"));
        r.setName(rs.getString("name"));
        r.setCount(rs.getInt("count"));
        r.setWarehouseId(rs.getInt("warehouse_id"));
        return r;
    }
}
