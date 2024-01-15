package com.example.brickleberry_backend.Repositories;

import com.example.brickleberry_backend.Entities.Animal;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
@AllArgsConstructor
public class AnimalRepository {
    private final JdbcTemplate jdbcTemplate;

    public List<Animal> getAllAnimalsPaged(int limit, int offset) {
        String query = "SELECT Animal_type.id, Animal_type.type_name, Animal_type.endangered, SUM(Animal_type_territory.animal_count)" +
                " FROM Animal_type JOIN Animal_type_territory ON Animal_type.id = Animal_type_territory.animal_type_id" +
                " GROUP BY Animal_type.id " +
                " ORDER BY Animal_type.id " +
                "  LIMIT " + limit +
                " OFFSET " + offset;
        return this.jdbcTemplate.query(query, this::wrapAnimal);
    }

    public int getAnimalsPagesCount(int pageSize) {
        String rowCountSql = "SELECT ceiling(count(distinct(animal_type_id))/" + pageSize +
                ") AS row_count FROM animal_type_territory";
        Integer result = this.jdbcTemplate.queryForObject(rowCountSql, Integer.class);
        return result == null ? 0 : result;
    }

    public List<Animal> getAnimalsByTerritoryPaged(int territoryId, int limit, int offset) {
        String query = "SELECT Animal_type.id, Animal_type.type_name, Animal_type.endangered, SUM(Animal_type_territory.animal_count)" +
                " FROM Animal_type JOIN Animal_type_territory ON Animal_type.id = Animal_type_territory.animal_type_id " +
                "WHERE Animal_type_territory.territory_id = " + territoryId +
                " GROUP BY Animal_type.id " +
                " ORDER BY Animal_type.id " +
                "  LIMIT " + limit +
                " OFFSET " + offset;
        return this.jdbcTemplate.query(query, this::wrapAnimal);
    }

    public int getAnimalsByTerritoryPagesCount(int territoryId, int pageSize) {
        String rowCountSql = "SELECT ceiling(count(distinct(animal_type_id))/" + pageSize +
                ") AS row_count FROM animal_type_territory WHERE territory_id = " + territoryId;
        Integer result = this.jdbcTemplate.queryForObject(rowCountSql, Integer.class);
        return result == null ? 0 : result;
    }

    public boolean checkForRegulation() {
        String rowCountSql = "SELECT exists(SELECT 1 FROM animal_type_territory" +
                " group by animal_type_territory.animal_type_id" +
                " having SUM(Animal_type_territory.animal_count) < 10)";
        Boolean result = this.jdbcTemplate.queryForObject(rowCountSql, Boolean.class);
        return result != null && result;
    }

    private Animal wrapAnimal(ResultSet rs, int rowNum) throws SQLException {
        Animal a = new Animal();
        a.setId(rs.getLong("id"));
        a.setTypeName(rs.getString("type_name"));
        a.setEndangered(rs.getBoolean("endangered"));
        a.setCount(rs.getInt("sum"));
        return a;
    }
}
