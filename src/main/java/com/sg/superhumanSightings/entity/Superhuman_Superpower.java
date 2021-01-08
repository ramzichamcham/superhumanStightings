package com.sg.superhumanSightings.entity;

import java.util.Objects;

public class Superhuman_Superpower {
    private Superhuman superhuman;
    private Superpower superpower;

    public Superhuman getSuperhuman() {
        return superhuman;
    }

    public void setSuperhuman(Superhuman superhuman) {
        this.superhuman = superhuman;
    }

    public Superpower getSuperpower() {
        return superpower;
    }

    public void setSuperpower(Superpower superpower) {
        this.superpower = superpower;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Superhuman_Superpower that = (Superhuman_Superpower) o;
        return superhuman.equals(that.superhuman) &&
                superpower.equals(that.superpower);
    }

    @Override
    public int hashCode() {
        return Objects.hash(superhuman, superpower);
    }
}
