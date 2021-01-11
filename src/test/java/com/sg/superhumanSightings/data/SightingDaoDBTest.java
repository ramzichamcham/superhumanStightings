package com.sg.superhumanSightings.data;

import com.sg.superhumanSightings.ServiceLayer.SuperhumanSightingsServiceLayer;
import com.sg.superhumanSightings.dao.*;
import com.sg.superhumanSightings.entity.*;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SightingDaoDBTest {

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

    @Autowired
    SuperhumanSightingsServiceLayer service;

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
    public void testAddSightingAndGetForDate(){

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

        //create new loc
        Location loc = new Location();
        loc.setName("Times Square");
        loc.setAddress("Manhattan, NY 10036, United States");
        loc.setLongitude(-73.985130);
        loc.setLatitude(40.758896);
        loc.setDescription("Famous central square in the Theater District of Manhattan");

        locationDao.addLocation(loc);

        //create new LocalDateTime
        LocalDateTime dateTime = service.localDateTimeNow();

        //create new sighting
        Sighting sighting = new Sighting();
        sighting.setSuperhuman(sh);
        sighting.setLocation(loc);
        sighting.setDateTime(dateTime);

        //add new sighting
        sightingDao.addSighting(sighting);


        //get By date
        Date date = service.localDateTimetoSQLDate(dateTime);
        List<Sighting> fromDaoForDate = sightingDao.getSightingsForDate(date);

        assertEquals(1, fromDaoForDate.size());
        assertEquals(sighting, fromDaoForDate.get(0));
        assertTrue(fromDaoForDate.contains(sighting));
    }

    @Test
    public void testGetSuperhumansForLocation(){

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

        //create another superhuman
        Superhuman sh2 = new Superhuman();
        sh2.setName("spiderman");
        sh2.setDescription("A man with spider abilities");

        //add superpowers to sh
        List<Superpower> superpowers2 = new ArrayList<>();
        Superpower power2 = new Superpower();
        power2.setName("swing");
        power2.setDescription("Ability to swing");
        superpowerDao.addSuperpower(power2);
        superpowers2.add(power2);
        sh2.setSuperpowers(superpowers2);

        //add organizations to sh
        List<Organization> organizations2 = new ArrayList<>();
        Organization org2 = new Organization();
        org2.setName("Spider people institute");
        org2.setAddress("Cambridge, MA, United States");
        org2.setDescription("Learn to swing");
        org2.setEmail("spiderpeople@gmail.com");
        org2.setPhoneNumber("415-290-7908");
        organizationDao.addOrganization(org2);
        organizations2.add(org2);
        sh2.setOrganizations(organizations2);

        superhumanDao.addSuperhuman(sh2);

        //create location
        Location loc = new Location();
        loc.setName("Times Square");
        loc.setAddress("Manhattan, NY 10036, United States");
        loc.setLongitude(-73.985130);
        loc.setLatitude(40.758896);
        loc.setDescription("Famous central square in the Theater District of Manhattan");

        locationDao.addLocation(loc);


        //add 2 superhuman sightings for same location
        LocalDateTime dateTime = service.localDateTimeNow();
        Sighting sighting = new Sighting();
        sighting.setLocation(loc);
        sighting.setSuperhuman(sh);
        sighting.setDateTime(dateTime);

        sightingDao.addSighting(sighting);

        LocalDateTime dateTime2 = service.localDateTimeNow();
        Sighting sighting2 = new Sighting();
        sighting2.setLocation(loc);
        sighting2.setSuperhuman(sh2);
        sighting2.setDateTime(dateTime2);

        sightingDao.addSighting(sighting2);
        //getSuperhumansForLocation
        List<Superhuman> shForLocationFromDao = sightingDao.getSuperhumansForLocation(loc);

        //assert
        assertEquals(2, shForLocationFromDao.size());
        assertTrue(shForLocationFromDao.contains(sh));
        assertTrue(shForLocationFromDao.contains(sh2));
    }

    @Test
    public void testGetLocationsForSuperhuman(){
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

        //create another superhuman
        Superhuman sh2 = new Superhuman();
        sh2.setName("spiderman");
        sh2.setDescription("A man with spider abilities");

        //add superpowers to sh
        List<Superpower> superpowers2 = new ArrayList<>();
        Superpower power2 = new Superpower();
        power2.setName("swing");
        power2.setDescription("Ability to swing");
        superpowerDao.addSuperpower(power2);
        superpowers2.add(power2);
        sh2.setSuperpowers(superpowers2);

        //add organizations to sh
        List<Organization> organizations2 = new ArrayList<>();
        Organization org2 = new Organization();
        org2.setName("Spider people institute");
        org2.setAddress("Cambridge, MA, United States");
        org2.setDescription("Learn to swing");
        org2.setEmail("spiderpeople@gmail.com");
        org2.setPhoneNumber("415-290-7908");
        organizationDao.addOrganization(org2);
        organizations2.add(org2);
        sh2.setOrganizations(organizations2);

        superhumanDao.addSuperhuman(sh2);

        //create 2 locations
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


        //add 2 sightings for same superhuman in 2 locations
        LocalDateTime dateTime = service.localDateTimeNow();
        Sighting sighting = new Sighting();
        sighting.setLocation(loc);
        sighting.setSuperhuman(sh);
        sighting.setDateTime(dateTime);

        sightingDao.addSighting(sighting);

        LocalDateTime dateTime2 = service.localDateTimeNow();
        Sighting sighting2 = new Sighting();
        sighting2.setLocation(loc2);
        sighting2.setSuperhuman(sh);
        sighting2.setDateTime(dateTime2);

        sightingDao.addSighting(sighting2);

        //getLocationsForSuperhuman
        List<Location> locsForShFromDao = sightingDao.getLocationsForSuperhuman(sh);

        assertEquals(2, locsForShFromDao.size());
        assertTrue(locsForShFromDao.contains(loc));
        assertTrue(locsForShFromDao.contains(loc2));


    }


}
