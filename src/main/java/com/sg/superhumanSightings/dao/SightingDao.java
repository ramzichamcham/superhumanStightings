package com.sg.superhumanSightings.dao;

import com.sg.superhumanSightings.entity.Location;
import com.sg.superhumanSightings.entity.Sighting;
import com.sg.superhumanSightings.entity.Superhuman;

import java.sql.Date;
import java.util.List;

public interface SightingDao {

    Sighting addSighting(Superhuman sh, Location loc, Date date);
    List<Sighting> getSightingsForDate(Date date);
}
