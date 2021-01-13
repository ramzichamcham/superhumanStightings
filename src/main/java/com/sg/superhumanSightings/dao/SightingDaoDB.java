package com.sg.superhumanSightings.dao;

import com.sg.superhumanSightings.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class SightingDaoDB implements SightingDao{

    @Autowired
    JdbcTemplate jdbc;

    @Override
    public List<Sighting> getAllSightings() {
        final String SELECT_ALL_SIGHTINGS = "SELECT * FROM sighting";
        List<Sighting> sightings = jdbc.query(SELECT_ALL_SIGHTINGS, new SightingMapper());
        associateSuperhumanAndLocation(sightings);
        return sightings;
    }

    @Override
    public Sighting addSighting(Sighting sighting) {
        final String INSERT_SIGHTING = "INSERT INTO sighting(superhuman_id, location_id, date_time) " +
                "VALUES(?, ?, ?)";
        jdbc.update(INSERT_SIGHTING, sighting.getSuperhuman().getId(), sighting.getLocation().getId(), Timestamp.valueOf(sighting.getDateTime()));

        return sighting;
    }



    @Override
    public List<Sighting> getSightingsForDate(Date date) {
        final String SELECT_SIGHTINGS_BY_DATE =
                "SELECT * FROM sighting WHERE DATE(date_time) = ?";
        List<Sighting> sightings = jdbc.query(SELECT_SIGHTINGS_BY_DATE, new SightingMapper(), date);
        associateSuperhumanAndLocation(sightings);

        return sightings;
    }




    private void associateSuperhumanAndLocation(List<Sighting> sightings){
        for (Sighting sighting: sightings){
            sighting.setSuperhuman(getSuperhumanForSighting(sighting.getDateTime()));
            sighting.setLocation(getLocationForSighting(sighting.getDateTime()));
        }
    }

    private Superhuman getSuperhumanForSighting(LocalDateTime time){
        final String SELECT_SUPERHUMAN_FOR_SIGHTING=
                "SELECT superhuman.* " +
                        "FROM superhuman " +
                        "JOIN sighting " +
                        "ON sighting.superhuman_id = superhuman.id " +
                        "WHERE sighting.date_time = ?";

        Superhuman sh = jdbc.queryForObject(SELECT_SUPERHUMAN_FOR_SIGHTING, new SuperhumanDaoDB.SuperhumanMapper(), Timestamp.valueOf(time));
        sh.setOrganizations(getOrganizationsForSuperhuman(sh.getId()));
        sh.setSuperpowers(getSuperpowersForSuperhuman(sh.getId()));
        return sh;
    }

    private List<Superpower> getSuperpowersForSuperhuman(int id) {
        final String SELECT_SUPERPOWERS_FOR_SUPERHUMAN =
                "SELECT superpower.* " +
                        "FROM superpower " +
                        "JOIN superhuman_superpower " +
                        "ON superpower.id = superhuman_superpower.superpower_id " +
                        "WHERE superhuman_superpower.superhuman_id = ? ";
        return jdbc.query(SELECT_SUPERPOWERS_FOR_SUPERHUMAN, new SuperpowerDaoDB.SuperpowerMapper(), id);
    }

    private List<Organization> getOrganizationsForSuperhuman(int id) {
        final String SELECT_ORGANIZATIONS_FOR_SUPERHUMAN =
                "SELECT organization.* " +
                        "FROM organization " +
                        "JOIN member ON member.organization_id = organization.id " +
                        "WHERE member.superhuman_id = ? ";
        return jdbc.query(SELECT_ORGANIZATIONS_FOR_SUPERHUMAN, new OrganizationDaoDB.OrganizationMapper(), id);
    }

    private Location getLocationForSighting(LocalDateTime time){
        final String SELECT_LOCATION_FOR_SIGHTING=
                "SELECT location.* " +
                        "FROM location " +
                        "JOIN sighting " +
                        "ON sighting.location_id = location.id " +
                        "WHERE sighting.date_time = ?";

        return jdbc.queryForObject(SELECT_LOCATION_FOR_SIGHTING, new LocationDaoDB.LocationMapper(), Timestamp.valueOf(time));

    }

    @Override
    public List<Superhuman> getSuperhumansForLocation(Location loc) {
        final String SELECT_SUPERHUMANS_BY_LOC =
                "SELECT superhuman.* " +
                        "FROM superhuman " +
                        "JOIN sighting " +
                        "ON sighting.superhuman_id = superhuman.id " +
                        "WHERE sighting.location_id = ?";
        List<Superhuman> superhumans = jdbc.query(SELECT_SUPERHUMANS_BY_LOC, new SuperhumanDaoDB.SuperhumanMapper(), loc.getId());
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
    public List<Location> getLocationsForSuperhuman(Superhuman sh) {
        final String SELECT_LOCATIONS_BY_SH =
                "SELECT location.* " +
                        "FROM location " +
                        "JOIN sighting " +
                        "ON sighting.location_id = location.id " +
                        "WHERE sighting.superhuman_id = ?";
        return jdbc.query(SELECT_LOCATIONS_BY_SH, new LocationDaoDB.LocationMapper(), sh.getId());
    }


    public static final class SightingMapper implements RowMapper<Sighting>{
        @Override
        public Sighting mapRow(ResultSet rs, int i) throws SQLException {
            Sighting sighting = new Sighting();
            sighting.setDateTime(rs.getTimestamp("date_time").toLocalDateTime());

            return sighting;
        }
    }




}
