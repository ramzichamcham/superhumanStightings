package com.sg.superhumanSightings.entity;

import org.apache.tomcat.jni.Local;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

public class Sighting {

    private Superhuman superhuman;
    private Location location;
    private LocalDateTime time;



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

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Sighting sighting = (Sighting) o;
        return superhuman.equals(sighting.superhuman) &&
                location.equals(sighting.location) &&
                time.equals(sighting.time);
    }

    @Override
    public int hashCode() {
        return Objects.hash(superhuman, location, time);
    }
}
