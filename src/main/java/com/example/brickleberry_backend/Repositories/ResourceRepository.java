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

    public boolean getRfidChips(int change, long rfidId) {
        String updateQuery = "Update Resource set count = count - %d where id = %d";
        return this.jdbcTemplate.update(String.format(updateQuery, change, rfidId)) == 1;
    }

//    public void getRfidChips(int change, long rfidId) {
//        if (change > 0) {
//            String query = "Select count from Resource where id = " + rfidId + " and warehouse_id = ";
//            String updateQuery = "Update Resource set count = %d where id = %d and warehouse_id = %d";
//            String existsQuery = "Select Exists(Select count from Resource where id = %d and warehouse_id = %d)";
//            Integer warehouseCount;
//            List<Integer> warehouseIds = this.jdbcTemplate.query("Select id from Warehouse", (rs, rowNum) -> rs.getInt("id"));
//            for (Integer id : warehouseIds) {
//                Boolean check = this.jdbcTemplate.queryForObject(String.format(existsQuery, rfidId, id), Boolean.class);
//                if (!check) {
//                    continue;
//                }
//                warehouseCount = this.jdbcTemplate.queryForObject(query + id, Integer.class);
//                if (change > warehouseCount) {
//
//                    this.jdbcTemplate.update(String.format(updateQuery, 0, rfidId, id));
//                    change -= warehouseCount;
//                } else {
//                    this.jdbcTemplate.update(String.format(updateQuery, warehouseCount - change, rfidId, id));
//                }
//            }
//        } else {
//            Integer warehouseId = this.jdbcTemplate.queryForObject("Select id from Warehouse limit 1", Integer.class);
//            String query = "Update Resource set count = count - %d where id = %d and warehouse_id = %d";
//            this.jdbcTemplate.update(String.format(query, change, rfidId, warehouseId));
//        }
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
