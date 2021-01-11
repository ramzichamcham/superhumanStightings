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
import org.mockito.internal.matchers.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Date;
import java.util.ArrayList;
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

    @Test
    public void testUpdateLocation(){
        Location loc = new Location();
        loc.setName("Times Square");
        loc.setAddress("Manhattan, NY 10036, United States");
        loc.setLongitude(-73.985130);
        loc.setLatitude(40.758896);
        loc.setDescription("Famous central square in the Theater District of Manhattan");
        loc = locationDao.addLocation(loc);

        Location fromDao = locationDao.getLocationById(loc.getId());
        assertEquals(loc, fromDao);

        loc.setName("The Bridge");
        locationDao.updateLocation(loc);

        assertNotEquals(loc, fromDao);

        fromDao = locationDao.getLocationById(loc.getId());

        assertEquals(loc, fromDao);
    }

    @Test
    public void testDeleteLocationById() {
        //create 2 new locations 
        Location loc = new Location();
        loc.setName("Times Square");
        loc.setAddress("Manhattan, NY 10036, United States");
        loc.setLongitude(-73.985130);
        loc.setLatitude(40.758896);
        loc.setDescription("Famous central square in the Theater District of Manhattan");
        loc = locationDao.addLocation(loc);


        Location loc2 = new Location();
        loc2.setName("The Bridge");
        loc2.setAddress("Manhattan, NY 00451, United States");
        loc2.setLongitude(-73.985130);
        loc2.setLatitude(40.758896);
        loc2.setDescription("A really big bridge");
        locationDao.addLocation(loc2);
        
        //create new superhuman
        Superhuman sh = new Superhuman();
        sh.setName("Superman");
        sh.setDescription("A man that is super");
        
        //add superpowers to sh
        List<Superpower> superpowers = new ArrayList<>();
        Superpower power = new Superpower();
        power.setName("fly");
        power.setDescription("Ability to fly");
        superpowerDao.addSuperpower(power);
        superpowers.add(power);
        sh.setSuperpowers(superpowers);

        //add organizations to sh
        List<Organization> organizations = new ArrayList<>();
        Organization org = new Organization();
        org.setName("Super people institute");
        org.setAddress("Cambridge, MA, United States");
        org.setDescription("Learn to fly");
        org.setEmail("superpeople@gmail.com");
        org.setPhoneNumber("415-290-7907");
        organizationDao.addOrganization(org);
        organizations.add(org);
        sh.setOrganizations(organizations);
        
        superhumanDao.addSuperhuman(sh);

        //Add a sighting with sh and loc
        Sighting sighting = new Sighting();
        sighting.setLocation(loc);
        sighting.setSuperhuman(sh);
        Date date= Date.valueOf("2021-01-01");
        sighting.setDate(date);
        sightingDao.addSighting(sighting);

        assertEquals(1, sightingDao.getSightingsForDate(sighting.getDate()).size());
        assertEquals(2, locationDao.getAllLocations().size());
        assertTrue(locationDao.getAllLocations().contains(loc));
        assertTrue(locationDao.getAllLocations().contains(loc2));

        //delete loc from db
        locationDao.deleteLocationById(loc.getId());

        //assert location and respective sighting have been deleted from db
        assertEquals(0, sightingDao.getSightingsForDate(sighting.getDate()).size());
        assertEquals(1, locationDao.getAllLocations().size());
        assertFalse(locationDao.getAllLocations().contains(loc));
        assertTrue(locationDao.getAllLocations().contains(loc2));


        
    }


}
