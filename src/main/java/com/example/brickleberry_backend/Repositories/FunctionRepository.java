package com.example.brickleberry_backend.Repositories;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
@AllArgsConstructor
public class FunctionRepository {
    private final JdbcTemplate jdbcTemplate;

    public void callRegulation(int responsiblePersonId) {
        String sql = String.format("SELECT regulation(%d)", responsiblePersonId);
        this.jdbcTemplate.execute(sql);
    }

    public void callInventarization() {
        Random r = new Random();
        int territoryId = r.nextInt(3) + 1;
        Integer personId = this.jdbcTemplate.queryForObject(
                "SELECT person_id from Person_role order by random() limit 1", Integer.class);
        Integer typeId = this.jdbcTemplate.queryForObject(
                String.format("SELECT animal_type_id from animal_type_territory where territory_id = %d order by random() limit 1", territoryId),
                Integer.class);
        String sql = String.format("SELECT inventarization(%d, %d, %d, %d, %d)",
                r.nextInt(50) + 5,
                r.nextInt(17) - 10,
                typeId,
                territoryId,
                personId
        );
        this.jdbcTemplate.execute(sql);
    }
}
