package com.sg.superhumanSightings.entity;

import org.apache.tomcat.jni.Local;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

public class Sighting {
    private int id;
    private Superhuman superhuman;
    private Location location;
    private Date date;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Sighting sighting = (Sighting) o;
        return id == sighting.id &&
                superhuman.equals(sighting.superhuman) &&
                location.equals(sighting.location) &&
                date.equals(sighting.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, superhuman, location, date);
    }
}
