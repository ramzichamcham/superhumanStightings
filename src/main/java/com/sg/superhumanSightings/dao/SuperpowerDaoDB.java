package com.sg.superhumanSightings.dao;

import com.sg.superhumanSightings.entity.Superhuman;
import com.sg.superhumanSightings.entity.Superpower;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class SuperpowerDaoDB implements SuperpowerDao {

    @Autowired
    JdbcTemplate jdbc;

    @Override
    public Superpower getSuperpowerById(int id) {
        try {
            final String SELECT_POWER_BY_ID = "SELECT * FROM superpower WHERE id = ?";
            return jdbc.queryForObject(SELECT_POWER_BY_ID, new SuperpowerMapper(), id);
        } catch (DataAccessException ex) {
            return null;
        }
    }

    @Override
    public List<Superpower> getAllSuperpowers() {
        final String SELECT_ALL_STUDENTS = "SELECT * FROM student";
        return jdbc.query(SELECT_ALL_STUDENTS, new SuperpowerMapper());
    }

    @Override
    public Superpower addSuperpower(Superpower sp) {
        final String INSERT_POWER = "INSERT INTO power(name, description) "
                + "VALUES(?,?)";
        jdbc.update(INSERT_POWER,
                sp.getName(),
                sp.getDescription());

        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        sp.setId(newId);
        return sp;
    }

    @Override
    public void updateSuperpower(Superpower sp) {
        final String UPDATE_STUDENT = "UPDATE superpower SET name = ?, description = ? "
                + "WHERE id = ?";
        jdbc.update(UPDATE_STUDENT,
                sp.getName(),
                sp.getDescription()
        );
    }


    @Override
    @Transactional
    public void deleteSuperpowerById(int id) {
        final String DELETE_SUPERHUMAN_SUPERPOWER = "DELETE FROM superhuman_superpower WHERE superpower_id = ?";
        jdbc.update(DELETE_SUPERHUMAN_SUPERPOWER, id);
        final String DELETE_SUPERPOWER = "DELETE FROM superpower WHERE id = ?";
        jdbc.update(DELETE_SUPERPOWER, id);
    }

    @Override
    @Transactional
    public List<Superpower> getSuperpowersForSuperhuman(Superhuman sh) {
        final String SELECT_ORGANIZATIONS_BY_SH =
                "SELECT superpower.* " +
                        "FROM superpower " +
                        "JOIN superhuman_superpower " +
                        "ON superhuman_superpower.superpower_id = superpower.id" +
                        "WHERE superhuman_superpower.superhuman_id = ?";
        return jdbc.query(SELECT_ORGANIZATIONS_BY_SH, new SuperpowerMapper(), sh.getId());
    }

    public static final class SuperpowerMapper implements RowMapper<Superpower> {

        @Override
        public Superpower mapRow(ResultSet rs, int index) throws SQLException {
            Superpower sp = new Superpower();
            sp.setId(rs.getInt("id"));
            sp.setName(rs.getString("name"));
            sp.setDescription(rs.getString("description"));

            return sp;
        }
    }
}
