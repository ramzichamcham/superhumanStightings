package com.sg.superhumanSightings.dao;

import com.sg.superhumanSightings.entity.Location;
import com.sg.superhumanSightings.entity.Sighting;
import com.sg.superhumanSightings.entity.Superhuman;

import java.sql.Date;
import java.util.List;

public interface SightingDao {

    List<Sighting> getAllSightings();
    Sighting addSighting(Sighting sighting);
    List<Sighting> getSightingsForDate(Date date);
    List<Superhuman> getSuperhumansForLocation(Location loc);
    List<Location> getLocationsForSuperhuman(Superhuman sh);
}
