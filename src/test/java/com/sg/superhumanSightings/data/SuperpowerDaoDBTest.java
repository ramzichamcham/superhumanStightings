package com.sg.superhumanSightings.data;

import com.sg.superhumanSightings.dao.*;
import com.sg.superhumanSightings.entity.Location;
import com.sg.superhumanSightings.entity.Organization;
import com.sg.superhumanSightings.entity.Superhuman;
import com.sg.superhumanSightings.entity.Superpower;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SuperpowerDaoDBTest {

    @Autowired
    LocationDao locationDao;

    @Autowired
    OrganizationDao organizationDao;

    @Autowired
    SightingDao sightingDao;

    @Autowired
    SuperhumanDao superhumanDao;

    @Autowired
    SuperpowerDao superpowerDao;

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }


    @Before
    public void setUp() {

        List<Location> locations = locationDao.getAllLocations();
        for(Location loc: locations){
            locationDao.deleteLocationById(loc.getId());
        }

        List<Organization> organizations = organizationDao.getAllOrganizations();
        for(Organization org: organizations){
            organizationDao.deleteOrganizationById(org.getId());
        }

        List<Superhuman> superhumans = superhumanDao.getAllSuperhumans();
        for(Superhuman sh: superhumans) {
            superhumanDao.deleteSuperhumanById(sh.getId());
        }

        List<Superpower> superpowers = superpowerDao.getAllSuperpowers();
        for(Superpower sp: superpowers){
            superpowerDao.deleteSuperpowerById(sp.getId());
        }
    }

    @Test
    public void testAddandGetSuperpower(){
        //create new superpower
        Superpower sp= new Superpower();
        sp.setName("Super strength");
        sp.setDescription("The strength of 50 men");

        //add superpower to db and retrieve from dao
        superpowerDao.addSuperpower(sp);
        Superpower fromDao = superpowerDao.getSuperpowerById(sp.getId());

        //assertEquals
        assertEquals(sp, fromDao);

    }

    @Test
    public void testGetAllSuperpowers() {
        //create and add 2 new superpowers
        Superpower sp= new Superpower();
        sp.setName("Super strength");
        sp.setDescription("The strength of 50 men");

        superpowerDao.addSuperpower(sp);

        Superpower sp2= new Superpower();
        sp2.setName("Super speed");
        sp2.setDescription("User's time is 20 times lower");

        superpowerDao.addSuperpower(sp2);

        List<Superpower> superpowers = superpowerDao.getAllSuperpowers();

        assertEquals(2, superpowers.size());
        assertTrue(superpowers.contains(sp));
        assertTrue(superpowers.contains(sp2));
    }


    @Test
    public void testUpdateSuperpower(){
        //create and add superpower
        Superpower sp= new Superpower();
        sp.setName("Super strength");
        sp.setDescription("The strength of 50 men");

        superpowerDao.addSuperpower(sp);

        //get From dao and compare
        Superpower fromDao = superpowerDao.getSuperpowerById(sp.getId());
        assertEquals(sp, fromDao);

        //update name
        sp.setName("Mega Strength");
        assertNotEquals(sp, fromDao);

        //update in db and retrieve to compare
        superpowerDao.updateSuperpower(sp);
        fromDao = superpowerDao.getSuperpowerById(sp.getId());

        assertEquals(sp, fromDao);
    }

}
