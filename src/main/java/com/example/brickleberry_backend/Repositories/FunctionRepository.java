package com.example.brickleberry_backend.Repositories;

import com.example.brickleberry_backend.Dtos.InventarizationDataDto;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;


@Component
@AllArgsConstructor
public class FunctionRepository {
    private final JdbcTemplate jdbcTemplate;

    public void callRegulation(int responsiblePersonId) {
        String sql = String.format("SELECT regulation(%d)", responsiblePersonId);
        this.jdbcTemplate.execute(sql);
    }

    public void callInventarization(InventarizationDataDto inventarizationDataDto) {
        String sql = String.format("SELECT inventarization(%d, %d, %d, %d)",
                inventarizationDataDto.getChange(),
                inventarizationDataDto.getTypeId(),
                inventarizationDataDto.getTerId(),
                inventarizationDataDto.getCreatorId()
        );
        this.jdbcTemplate.execute(sql);
    }
}
