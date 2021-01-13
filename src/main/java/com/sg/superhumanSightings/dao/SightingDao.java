package com.sg.superhumanSightings.dao;

import com.sg.superhumanSightings.entity.Location;
import com.sg.superhumanSightings.entity.Sighting;
import com.sg.superhumanSightings.entity.Superhuman;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;

public interface SightingDao {

    List<Sighting> getAllSightings();
    public void deleteSighting(LocalDateTime dateTime, int superhumanId, int locationId);
    public List<Sighting> getRecentSightings(int x);

    Sighting addSighting(Sighting sighting);
    List<Sighting> getSightingsForDate(Date date);
    List<Superhuman> getSuperhumansForLocation(Location loc);
    List<Location> getLocationsForSuperhuman(Superhuman sh);
}
