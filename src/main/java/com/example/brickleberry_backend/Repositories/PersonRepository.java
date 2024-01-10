package com.example.brickleberry_backend.Repositories;

import com.example.brickleberry_backend.Entities.Person;
import com.example.brickleberry_backend.Enums.Role;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;

@Component
@AllArgsConstructor
public class PersonRepository {
    private final JdbcTemplate jdbcTemplate;

    public List<Person> getAllPersons() {
        String query = "SELECT * FROM Person";
        return this.jdbcTemplate.query(query, this::wrapPerson);
    }

    public List<Person> getPersonsPage(int limit, int offset) {
        String query = "SELECT * FROM Person LIMIT " + limit + " OFFSET " + offset;
        return this.jdbcTemplate.query(query, this::wrapPerson);
    }

    public boolean deletePerson(int id) {
        String sql = "DELETE FROM Person WHERE id =" + id;
        Object[] args = new Object[]{id};
        if (this.jdbcTemplate.update(sql, args) == 1) {
            sql = "DELETE FROM Person_role WHERE person_id = " + id;
            args = new Object[]{id};
            return this.jdbcTemplate.update(sql, args) == 1;
        }
        return false;
    }

    private Person wrapPerson(ResultSet rs, int rowNum) throws SQLException {
        Person p = new Person();
        p.setId(rs.getInt("id"));
        p.setName(rs.getString("name"));
        p.setSurname(rs.getString("surname"));
        p.setLastname(rs.getString("lastname"));
        p.setDate_of_birth(rs.getDate("date_of_birth"));
        p.setRoles(new HashSet<Role>());
        String rolesQuery = "SELECT DISTINCT Role.role_name FROM Person_role" +
                " JOIN Role ON Person_role.role_id = Role.id WHERE Person_role.person_id=" + p.getId();
        this.jdbcTemplate.query(rolesQuery,
                (resultSet, r) -> p.getRoles().add(Role.valueOfName(resultSet.getString(1))));
        return p;
    }
}
