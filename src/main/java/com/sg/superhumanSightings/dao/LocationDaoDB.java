package com.sg.superhumanSightings.dao;

import com.sg.superhumanSightings.entity.Location;
import com.sg.superhumanSightings.entity.Organization;
import com.sg.superhumanSightings.entity.Superhuman;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class LocationDaoDB implements LocationDao{

    @Autowired
    JdbcTemplate jdbc;

    @Override
    public Location getLocationById(int id) {
        try {
            final String SELECT_LOCATION_BY_ID = "SELECT * FROM location WHERE id = ?";
            return jdbc.queryForObject(SELECT_LOCATION_BY_ID, new LocationMapper(), id);
        } catch (DataAccessException ex) {
            return null;
        }
    }

    @Override
    public List<Location> getAllLocations() {
        final String SELECT_ALL_LOCATIONS = "SELECT * FROM location";
        return jdbc.query(SELECT_ALL_LOCATIONS, new LocationMapper());
    }

    @Override
    public Location addLocation(Location loc) {
        final String INSERT_LOCATION =
                "INSERT INTO location(name, description, address, latitude, longitude) "
                        + "VALUES(?,?,?,?,?)";
        jdbc.update(INSERT_LOCATION,
                loc.getName(),
                loc.getDescription(),
                loc.getAddress(),
                loc.getLatitude(),
                loc.getLongitude()
        );

        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        loc.setId(newId);
        return loc;
    }

    @Override
    public void updateLocation(Location loc) {
        final String UPDATE_LOCATION =
                "UPDATE location SET name = ?, description = ?, address = ?, latitude = ?, longitude = ?"
                        + "WHERE id = ?";
        jdbc.update(UPDATE_LOCATION,
                loc.getName(),
                loc.getDescription(),
                loc.getAddress(),
                loc.getLatitude(),
                loc.getLongitude()
        );
    }

    @Override
    public void deleteLocationById(int id) {
        final String DELETE_SIGHTINGS = "DELETE FROM sighting WHERE location_id = ?";
        jdbc.update(DELETE_SIGHTINGS, id);
        final String DELETE_LOCATION = "DELETE FROM location WHERE id = ?";
        jdbc.update(DELETE_LOCATION, id);
    }

    @Override
    public List<Location> getLocationsForSuperhuman(Superhuman sh) {
        final String SELECT_LOCATIONS_BY_SH =
                "SELECT location.* " +
                        "FROM location " +
                        "JOIN sighting " +
                        "ON sighting.location_id = location.id" +
                        "WHERE member.superhuman_id = ?";
        return jdbc.query(SELECT_LOCATIONS_BY_SH, new LocationMapper(), sh.getId());
    }

    public static final class LocationMapper implements RowMapper<Location> {

        @Override
        public Location mapRow(ResultSet rs, int index) throws SQLException {
            Location loc = new Location();
            loc.setId(rs.getInt("id"));
            loc.setName(rs.getString("name"));
            loc.setDescription(rs.getString("description"));
            loc.setAddress(rs.getString("address"));
            loc.setLatitude(rs.getDouble("latitude"));
            loc.setLongitude(rs.getDouble("longitude"));

            return loc;
        }
    }
}
