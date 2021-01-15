package com.sg.superhumanSightings.entity;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.time.LocalDateTime;
import java.util.Objects;

public class Sighting {

    @NotNull(message="You must choose a superhuman")
    private Superhuman superhuman;

    @NotNull(message="You must choose a location")
    private Location location;

    @NotNull(message="You must specify date and time")
    @Past(message = "Date must be in the past")
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
