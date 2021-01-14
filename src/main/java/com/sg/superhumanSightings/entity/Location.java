package com.sg.superhumanSightings.entity;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;

public class Location {
    private int id;
    private String name;
    @NotBlank(message="Address must not be empty.")
    @Size(max = 100, message = "Address must be less than 100 characters.")
    private String address;
    @Size(max = 200, message = "Description must be less than 200 characters.")
    private String description;

    @NotNull(message = "Latitude must not be empty")
    @Digits(integer = 2, fraction = 6, message = "Latitude must have 2 integral and 6 fractional digits at most. ")
    private Double latitude;

    @NotNull(message="Longitude must not be empty")
    @Digits(integer = 3, fraction = 6, message = "Longitude must have 3 integral and 6 fractional digits at most. ")
    private Double longitude;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    //name and description can be null

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Location location = (Location) o;
        return id == location.id &&
                Objects.equals(name, location.name) &&
                address.equals(location.address) &&
                Objects.equals(description, location.description) &&
                latitude.equals(location.latitude) &&
                longitude.equals(location.longitude);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, address, description, latitude, longitude);
    }
}
