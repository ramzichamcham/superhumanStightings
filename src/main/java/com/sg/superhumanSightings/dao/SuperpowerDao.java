package com.sg.superhumanSightings.dao;

import com.sg.superhumanSightings.entity.Organization;
import com.sg.superhumanSightings.entity.Superhuman;
import com.sg.superhumanSightings.entity.Superpower;

import java.util.List;

public interface SuperpowerDao {

    Superpower getSuperpowerById(int id);
    List<Superpower> getAllSuperpowers();
    Superpower addSuperpower(Superpower sp);
    void updateSuperpower(Superpower sp);
    void delecteSuperpower(Superpower sp);




}
