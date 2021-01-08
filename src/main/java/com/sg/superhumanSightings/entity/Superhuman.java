package com.sg.superhumanSightings.entity;

import java.util.List;
import java.util.Objects;

public class Superhuman {

    private int id;
    private String name;
    private String description;
    private List<Location> locations;
    private List<Organization> organizations;
    private List<Superpower> superpowers;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public List<Location> getLocations() {
        return locations;
    }

    public void setLocations(List<Location> locations) {
        this.locations = locations;
    }

    public List<Organization> getOrganizations() {
        return organizations;
    }

    public void setOrganizations(List<Organization> organizations) {
        this.organizations = organizations;
    }

    public List<Superpower> getSuperpowers() {
        return superpowers;
    }

    public void setSuperpowers(List<Superpower> superpowers) {
        this.superpowers = superpowers;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Superhuman that = (Superhuman) o;
        return id == that.id &&
                name.equals(that.name) &&
                Objects.equals(description, that.description) &&
                locations.equals(that.locations) &&
                organizations.equals(that.organizations) &&
                superpowers.equals(that.superpowers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, locations, organizations, superpowers);
    }
}
