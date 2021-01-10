package com.sg.superhumanSightings.data;

import com.sg.superhumanSightings.dao.*;
import com.sg.superhumanSightings.entity.*;
import net.bytebuddy.implementation.bind.annotation.Super;
import static org.junit.Assert.*;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LocationDaoDBTest {

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
            superhumanDao.deleteSuperhumanById(org.getId());
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
    public void testAddandGetLocation(){
        Location loc = new Location();
        loc.setName("Times Square");
        loc.setAddress("Manhattan, NY 10036, United States");
        loc.setLongitude(-73.985130);
        loc.setLatitude(40.758896);
        loc.setDescription("Famous central square in the Theater District of Manhattan");

        locationDao.addLocation(loc);
        Location fromDao = locationDao.getLocationById(loc.getId());

        assertEquals(loc, fromDao);

    }

    @Test
    public void testGetAllLocations() {
        Location loc = new Location();
        loc.setName("Times Square");
        loc.setAddress("Manhattan, NY 10036, United States");
        loc.setLongitude(-73.985130);
        loc.setLatitude(40.758896);
        loc.setDescription("Famous central square in the Theater District of Manhattan");

        locationDao.addLocation(loc);

        Location loc2 = new Location();
        loc2.setName("The Bridge");
        loc2.setAddress("Manhattan, NY 00451, United States");
        loc2.setLongitude(-73.985130);
        loc2.setLatitude(40.758896);
        loc2.setDescription("A really big bridge");

        locationDao.addLocation(loc2);

        List<Location> locations = locationDao.getAllLocations();

        assertEquals(2, locations.size());
        assertTrue(locations.contains(loc));
        assertTrue(locations.contains(loc2));
    }


}
