package com.sg.superhumanSightings.dao;

import com.sg.superhumanSightings.entity.Organization;
import com.sg.superhumanSightings.entity.Organization;
import com.sg.superhumanSightings.entity.Superhuman;
import com.sg.superhumanSightings.entity.Superpower;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class OrganizationDaoDB implements OrganizationDao{
    
    @Autowired
    JdbcTemplate jdbc;

    @Override
    public Organization getOrganizationById(int id) {
        try {
            final String SELECT_ORGANIZATION_BY_ID = "SELECT * FROM organization WHERE id = ?";
            return jdbc.queryForObject(SELECT_ORGANIZATION_BY_ID, new OrganizationMapper(), id);
        } catch (DataAccessException ex) {
            return null;
        }
    }

    @Override
    public List<Organization> getAllOrganizations() {
        final String SELECT_ALL_ORGANIZATIONS = "SELECT * FROM organization";
        return jdbc.query(SELECT_ALL_ORGANIZATIONS, new OrganizationMapper());
    }

    @Override
    public Organization addOrganization(Organization org) {
        final String INSERT_ORGANIZATION =
                "INSERT INTO organization(name, description, address, phone_number, email) "
                + "VALUES(?,?,?,?,?)";
        jdbc.update(INSERT_ORGANIZATION,
                org.getName(),
                org.getDescription(),
                org.getAddress(),
                org.getPhoneNumber(),
                org.getEmail());

        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        org.setId(newId);
        return org;
    }

    @Override
    public void updateOrganization(Organization org) {
        final String UPDATE_ORGANIZATION =
                "UPDATE organization SET name = ?, description = ?, address = ?, phone_number = ?, email = ? "
                + "WHERE id = ?";
        jdbc.update(UPDATE_ORGANIZATION,
                org.getName(),
                org.getDescription(),
                org.getAddress(),
                org.getPhoneNumber(),
                org.getEmail()
        );
    }

    @Override
    @Transactional
    public void deleteOrganizationById(int id) {
        final String DELETE_MEMBERS = "DELETE FROM member WHERE organization_id = ?";
        jdbc.update(DELETE_MEMBERS, id);
        final String DELETE_ORGANIZATION = "DELETE FROM organization WHERE id = ?";
        jdbc.update(DELETE_ORGANIZATION, id);
    }

    @Override
    @Transactional
    public List<Organization> getOrganizationsForSuperhuman(Superhuman sh){
        final String SELECT_ORGANIZATIONS_BY_SH =
                "SELECT organization.* " +
                        "FROM organization " +
                        "JOIN member " +
                        "ON member.organization_id = organization.id " +
                        "WHERE member.superhuman_id = ?";
        return jdbc.query(SELECT_ORGANIZATIONS_BY_SH, new OrganizationMapper(), sh.getId());
    }


    public static final class OrganizationMapper implements RowMapper<Organization> {

        @Override
        public Organization mapRow(ResultSet rs, int index) throws SQLException {
            Organization org = new Organization();
            org.setId(rs.getInt("id"));
            org.setName(rs.getString("name"));
            org.setDescription(rs.getString("description"));
            org.setAddress(rs.getString("address"));
            org.setPhoneNumber(rs.getString("phone_number"));
            org.setEmail(rs.getString("email"));

            return org;
        }
    }
    
}
