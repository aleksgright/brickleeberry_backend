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

    public List<Animal> getAnimalsByTerritoryPaged(int territory_id, int limit, int offset) {
        String query = "SELECT Animal_type.id, Animal_type.type_name, Animal_type.endangered, SUM(Animal_type_territory.animal_count)" +
                " FROM Animal_type JOIN Animal_type_territory ON Animal_type.id = Animal_type_territory.animal_type_id " +
                "WHERE Animal_type_territory.territory_id = " + territory_id +
                " GROUP BY Animal_type.id " +
                " ORDER BY Animal_type.id " +
                "  LIMIT " + limit +
                " OFFSET " + offset;
        return this.jdbcTemplate.query(query, this::wrapAnimal);
    }

    private Animal wrapAnimal(ResultSet rs, int rowNum) throws SQLException {
        Animal a = new Animal();
        a.setId(rs.getInt("id"));
        a.setTypeName(rs.getString("type_name"));
        a.setEndangered(rs.getBoolean("endangered"));
        a.setCount(rs.getInt("sum"));
        return a;
    }
}
