package com.sg.superhumanSightings.dao;

import com.sg.superhumanSightings.entity.Location;
import com.sg.superhumanSightings.entity.Organization;
import com.sg.superhumanSightings.entity.Superhuman;

import java.util.List;

public interface SuperhumanDao {

    Superhuman getSuperhumanById(int id);
    List<Superhuman> getAllSuperhumans();
    Superhuman addSuperhuman(Superhuman sh);
    void updateSuperhuman(Superhuman sh);
    void deleteSuperhumanById(int id);

    List<Superhuman> getSuperhumansForOrganization(Organization org);



}
