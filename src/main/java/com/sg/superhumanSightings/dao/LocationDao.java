package com.sg.superhumanSightings.dao;

import com.sg.superhumanSightings.entity.Location;
import com.sg.superhumanSightings.entity.Superhuman;

import java.util.List;

public interface LocationDao {

    Location getLocationById(int id);
    List<Location> getAllLocations();
    Location addLocation(Location org);
    void updateLocation(Location org);
    void deleteLocationById(int id);

}
