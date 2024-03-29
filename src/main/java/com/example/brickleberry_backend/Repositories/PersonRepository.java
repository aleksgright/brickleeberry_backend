package com.example.brickleberry_backend.Repositories;

import com.example.brickleberry_backend.Dtos.PersonRegisterDto;
import com.example.brickleberry_backend.Entities.Person;
import com.example.brickleberry_backend.Enums.Role;
import com.example.brickleberry_backend.Exeptions.PersonNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Component
@AllArgsConstructor
public class PersonRepository {
    private final JdbcTemplate jdbcTemplate;

    public Optional<Person> getPersonById(int id) {
        String query = "SELECT * FROM Person where id = " + id;
        Optional<Person> response = Optional.empty();
        try {
            response = Optional.of(this.jdbcTemplate.query(query, this::wrapPerson).get(0));
        } catch (IndexOutOfBoundsException e) {
            throw new PersonNotFoundException("No person with this id");
        }
        return response;
    }

    public List<Person> getAllPersons() {
        String query = "SELECT * FROM Person";
        return this.jdbcTemplate.query(query, this::wrapPerson);
    }

    public List<Person> getPersonsPage(int limit, int offset) {
        String query = "SELECT * FROM Person LIMIT " + limit + " OFFSET " + offset;
        return this.jdbcTemplate.query(query, this::wrapPerson);
    }

    public int getPersonsPagesCount(int pageSize) {
        String rowCountSql = "SELECT ceiling(count(*)/" + pageSize + ") AS row_count FROM Person ";
        Integer result = this.jdbcTemplate.queryForObject(rowCountSql, Integer.class);
        return result == null ? 0 : result;
    }

    public boolean deletePerson(int id) {
        String sql = "DELETE FROM Person WHERE id = ?";
        Object[] args = new Object[]{id};
        return this.jdbcTemplate.update(sql, args) == 1;
    }

    public boolean addPerson(PersonRegisterDto personRegisterDto) {
        Integer role_id = this.jdbcTemplate.queryForObject(
                "SELECT id FROM Role WHERE role_name = ?",
                new Object[]{personRegisterDto.getRole()},
                Integer.class);
        Long person_id = this.jdbcTemplate.queryForObject("INSERT INTO Person(name, surname)  VALUES(?, ?) RETURNING id",
                new Object[]{personRegisterDto.getName(), personRegisterDto.getSurname()},
                Long.class);
        return this.jdbcTemplate.update("INSERT INTO Person_role(person_id, role_id) VALUES(?, ?)",
                person_id,
                role_id) == 1;
    }

    private Person wrapPerson(ResultSet rs, int rowNum) throws SQLException {
        Person p = new Person();
        p.setId(rs.getLong("id"));
        p.setName(rs.getString("name"));
        p.setSurname(rs.getString("surname"));
        p.setLastname(rs.getString("lastname"));
        p.setDate_of_birth(rs.getDate("date_of_birth"));
        p.setRoles(new HashSet<>());
        String rolesQuery = "SELECT DISTINCT Role.role_name FROM Person_role" +
                " JOIN Role ON Person_role.role_id = Role.id WHERE Person_role.person_id=" + p.getId();
        this.jdbcTemplate.query(rolesQuery,
                (resultSet, r) -> p.getRoles().add(Role.valueOfName(resultSet.getString(1))));
        return p;
    }
}
