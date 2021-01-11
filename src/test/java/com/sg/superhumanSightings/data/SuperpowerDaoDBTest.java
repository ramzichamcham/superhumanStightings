package com.sg.superhumanSightings.data;

import com.sg.superhumanSightings.dao.*;
import com.sg.superhumanSightings.entity.*;
import net.bytebuddy.implementation.bind.annotation.Super;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.ArrayList;
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



    @Test
    public void testDeleteSuperpowerById() {
        //create and add 2 new superpowers
        Superpower sp= new Superpower();
        sp.setName("Super strength");
        sp.setDescription("The strength of 50 men");

        superpowerDao.addSuperpower(sp);

        Superpower sp2= new Superpower();
        sp2.setName("Super speed");
        sp2.setDescription("User's time is 20 times lower");

        superpowerDao.addSuperpower(sp2);

        //create new superhuman
        Superhuman sh = new Superhuman();
        sh.setName("Superman");
        sh.setDescription("A man that is super");

        //add superpowers to sh
        List<Superpower> superpowers = new ArrayList<>();

        superpowers.add(sp);
        superpowers.add(sp2);
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

        //superpowers
        List<Superpower> powersFromDao = superpowerDao.getAllSuperpowers();
        assertEquals(2, powersFromDao.size());
        assertTrue(powersFromDao.contains(sp));
        assertTrue(powersFromDao.contains(sp2));

        //sh's superpowers
        Superhuman shFromDao = superhumanDao.getSuperhumanById(sh.getId());
        List<Superpower> shPowers = shFromDao.getSuperpowers();
        assertEquals(2, shPowers.size());
        assertTrue(shPowers.contains(sp));
        assertTrue(shPowers.contains(sp2));

        //delete superpower from db
        superpowerDao.deleteSuperpowerById(sp.getId());

        //superpowers
        powersFromDao = superpowerDao.getAllSuperpowers();
        assertEquals(1, powersFromDao.size());
        assertFalse(powersFromDao.contains(sp));
        assertTrue(powersFromDao.contains(sp2));

        //sh's superpowers
        shFromDao = superhumanDao.getSuperhumanById(sh.getId());
        shPowers = shFromDao.getSuperpowers();
        assertEquals(1, shPowers.size());
        assertFalse(shPowers.contains(sp));
        assertTrue(shPowers.contains(sp2));

    }


    @Test
    public void testGetSuperpowersForSuperhuman(){
        //add superhuman with 2 superpowers

        //create and add 2 new superpowers
        Superpower sp= new Superpower();
        sp.setName("Super strength");
        sp.setDescription("The strength of 50 men");

        superpowerDao.addSuperpower(sp);

        Superpower sp2= new Superpower();
        sp2.setName("Super speed");
        sp2.setDescription("User's time is 20 times lower");

        superpowerDao.addSuperpower(sp2);

        //create new superhuman
        Superhuman sh = new Superhuman();
        sh.setName("Superman");
        sh.setDescription("A man that is super");

        //add superpowers to sh
        List<Superpower> superpowers = new ArrayList<>();
        superpowers.add(sp);
        superpowers.add(sp2);
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



        //getSuperpowersForSuperhuman
        List<Superpower> spsForShFromDao = superpowerDao.getSuperpowersForSuperhuman(sh);

        //assert
        assertEquals(2, spsForShFromDao.size());
        assertTrue(spsForShFromDao.contains(sp));
        assertTrue(spsForShFromDao.contains(sp2));
    }


}
