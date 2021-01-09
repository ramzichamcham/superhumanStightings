package com.sg.superhumanSightings.dao;

import com.sg.superhumanSightings.entity.Organization;
import com.sg.superhumanSightings.entity.Superhuman;

import java.util.List;

public interface OrganizationDao {

    Organization getOrganizationById(int id);
    List<Organization> getAllOrganizations();
    Organization addOrganization(Organization org);
    void updateOrganization(Organization org);
    void deleteOrganizationById(int id);

    List<Organization> getOrganizationsForSuperhuman(Superhuman sh);



}
