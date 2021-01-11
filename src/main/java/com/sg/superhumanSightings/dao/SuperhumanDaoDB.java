package com.sg.superhumanSightings.dao;

import com.sg.superhumanSightings.entity.Location;
import com.sg.superhumanSightings.entity.Organization;
import com.sg.superhumanSightings.entity.Superhuman;
import com.sg.superhumanSightings.entity.Superpower;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import com.sg.superhumanSightings.dao.OrganizationDaoDB.OrganizationMapper;
import com.sg.superhumanSightings.dao.LocationDaoDB.LocationMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class SuperhumanDaoDB implements SuperhumanDao{

    @Autowired
    JdbcTemplate jdbc;


    // GET SUPERHUMAN BY ID
    @Override
    public Superhuman getSuperhumanById(int id) {
        try{
            final String SELECT_SUPERHUMAN_BY_ID = "SELECT * FROM superhuman WHERE id = ?";
            Superhuman sh = jdbc.queryForObject(SELECT_SUPERHUMAN_BY_ID, new SuperhumanMapper(), id);
            sh.setOrganizations(getOrganizationsForSuperhuman(sh.getId()));
            sh.setSuperpowers(getSuperpowersForSuperhuman(sh.getId()));
            return sh;

        }catch(DataAccessException ex){
            return null;
        }
    }


    private List<Superpower> getSuperpowersForSuperhuman(int id) {
        final String SELECT_SUPERPOWERS_FOR_SUPERHUMAN =
                "SELECT superpower.* " +
                        "FROM superpower " +
                        "JOIN superhuman_superpower " +
                        "ON superpower.id = superhuman_superpower.superpower_id " +
                        "WHERE superhuman_superpower.superhuman_id = ?";
        return jdbc.query(SELECT_SUPERPOWERS_FOR_SUPERHUMAN, new SuperpowerDaoDB.SuperpowerMapper(), id);
    }

    private List<Organization> getOrganizationsForSuperhuman(int id) {
        final String SELECT_ORGANIZATIONS_FOR_SUPERHUMAN =
                "SELECT organization.* " +
                        "FROM organization " +
                        "JOIN member ON member.organization_id = organization.id " +
                        "WHERE member.superhuman_id = ?";
        return jdbc.query(SELECT_ORGANIZATIONS_FOR_SUPERHUMAN, new OrganizationDaoDB.OrganizationMapper(), id);
    }

    //GET SUPERHUMAN BY ID END


    //GET ALL SUPERHUMANS

    @Override
    public List<Superhuman> getAllSuperhumans() {
        final String SELECT_ALL_SUPERHUMANS = "SELECT * FROM Superhuman";
        List<Superhuman> superhumans = jdbc.query(SELECT_ALL_SUPERHUMANS, new SuperhumanMapper());
        associateOrgPwr(superhumans);
        return superhumans;
    }

    private void associateOrgPwr(List<Superhuman> superhumans) {
        for(Superhuman sh: superhumans){
            sh.setSuperpowers(getSuperpowersForSuperhuman(sh.getId()));
            sh.setOrganizations(getOrganizationsForSuperhuman(sh.getId()));
        }
    }

    @Override
    @Transactional
    public Superhuman addSuperhuman(Superhuman sh) {
        final String INSERT_SUPERHUMAN =
                "INSERT INTO superhuman(name, description)" +
                        "VALUES(?, ?)";
        jdbc.update(INSERT_SUPERHUMAN,
                sh.getName(),
                sh.getDescription()
                );

        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        sh.setId(newId);
        insertSuperhumanOrg(sh);
        insertSuperhumanSuperpower(sh);
        return sh;
    }


    private void insertSuperhumanSuperpower(Superhuman sh) {
        final String INSERT_INTO_superhuman_superpower =
                "INSERT INTO superhuman_superpower(superhuman_id, superpower_id) " +
                        "VALUES(?, ?)";
        for(Superpower sp: sh.getSuperpowers()){
            jdbc.update(INSERT_INTO_superhuman_superpower,
                    sh.getId(), sp.getId());
        }
    }

    private void insertSuperhumanOrg(Superhuman sh) {
        final String INSERT_INTO_MEMBER =
                "INSERT INTO member(superhuman_id, organization_id) " +
                        "VALUES(?, ?)";
        for(Organization org: sh.getOrganizations()){
            jdbc.update(INSERT_INTO_MEMBER,
                    sh.getId(),
                    org.getId()
            );
        }
    }

    @Override
    @Transactional
    public void updateSuperhuman(Superhuman sh) {
        final String UPDATE_SUPERHUMAN = "UPDATE superhuman SET " +
                "name = ?, description = ? WHERE id = ?";

        jdbc.update(UPDATE_SUPERHUMAN, sh.getName(), sh.getDescription(), sh.getId());

        final String DELETE_MEMBERS = "DELETE FROM member WHERE superhuman_id = ?";
        jdbc.update(DELETE_MEMBERS, sh.getId());
        insertSuperhumanOrg(sh);

        final String DELETE_SUPERHUMAN_SUPERPOWERS =
                "DELETE FROM superhuman_superpower WHERE superhuman_id = ?";
        jdbc.update(DELETE_SUPERHUMAN_SUPERPOWERS, sh.getId());
        insertSuperhumanSuperpower(sh);


    }

    @Override
    @Transactional
    public void deleteSuperhumanById(int id) {
        final String DELETE_MEMBERS = "DELETE FROM member WHERE superhuman_id = ?";
        jdbc.update(DELETE_MEMBERS, id);

        final String DELETE_SUPERHUMAN_SUPERPOWERS =
                "DELETE FROM superhuman_superpower WHERE superhuman_id = ?";
        jdbc.update(DELETE_SUPERHUMAN_SUPERPOWERS, id);

        final String DELETE_SUPERHUMAN_SIGHTINGS =
                "DELETE FROM sighting WHERE superhuman_id = ?";
        jdbc.update(DELETE_SUPERHUMAN_SIGHTINGS, id);

        final String DELETE_SUPERHUMAN = "DELETE FROM superhuman WHERE id = ?";
        jdbc.update(DELETE_SUPERHUMAN, id);



    }

    @Override
    public List<Superhuman> getSuperhumansForOrganization(Organization org) {
        final String SELECT_SUPERHUMANS_FOR_ORG =
                "SELECT superhuman.* " +
                        "FROM superhuman " +
                        "JOIN member " +
                        "ON superhuman.id = member.superhuman_id " +
                        "WHERE member.organization_id = ?";
        List<Superhuman> superhumans = jdbc.query(SELECT_SUPERHUMANS_FOR_ORG, new SuperhumanMapper(), org.getId());
        associateOrgPwr(superhumans);
        return superhumans;
    }



    public static final class SuperhumanMapper implements RowMapper<Superhuman>{


        @Override
        public Superhuman mapRow(ResultSet rs, int i) throws SQLException {
            Superhuman sh = new Superhuman();
            sh.setId(rs.getInt("id"));
            sh.setName(rs.getString("name"));
            sh.setDescription(rs.getString("description"));

            return sh;
        }
    }


}
