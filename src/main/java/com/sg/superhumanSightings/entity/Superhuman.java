package com.sg.superhumanSightings.entity;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Objects;

public class Superhuman {

    private int id;

    @NotBlank(message="Name must not be empty.")
    @Size(max = 100, message = "Name must be less than 100 characters.")
    private String name;

    private String description;
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
                Objects.equals(organizations, that.organizations) &&
                Objects.equals(superpowers, that.superpowers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, organizations, superpowers);
    }
}
