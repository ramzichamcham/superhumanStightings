package com.sg.superhumanSightings.entity;

import java.util.Objects;

public class Member {
    private int superhumanId;
    private int organizationId;

    public int getSuperhumanId() {
        return superhumanId;
    }

    public void setSuperhumanId(int superhumanId) {
        this.superhumanId = superhumanId;
    }

    public int getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(int organizationId) {
        this.organizationId = organizationId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Member member = (Member) o;
        return superhumanId == member.superhumanId &&
                organizationId == member.organizationId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(superhumanId, organizationId);
    }
}
