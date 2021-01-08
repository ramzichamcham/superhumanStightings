package com.sg.superhumanSightings.entity;

import java.util.Objects;

public class Member {
    private Superhuman superhuman;
    private Organization organization;

    public Superhuman getSuperhuman() {
        return superhuman;
    }

    public void setSuperhuman(Superhuman superhuman) {
        this.superhuman = superhuman;
    }

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Member member = (Member) o;
        return superhuman.equals(member.superhuman) &&
                organization.equals(member.organization);
    }

    @Override
    public int hashCode() {
        return Objects.hash(superhuman, organization);
    }
}
