package com.sg.superhumanSightings.entity;

import java.time.LocalDateTime;
import java.util.Objects;

public class Sighting {

    private Superhuman superhuman;
    private Location location;
    private LocalDateTime dateTime;



    public Superhuman getSuperhuman() {
        return superhuman;
    }

    public void setSuperhuman(Superhuman superhuman) {
        this.superhuman = superhuman;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Sighting sighting = (Sighting) o;
        return superhuman.equals(sighting.superhuman) &&
                location.equals(sighting.location) &&
                dateTime.equals(sighting.dateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(superhuman, location, dateTime);
    }
}
