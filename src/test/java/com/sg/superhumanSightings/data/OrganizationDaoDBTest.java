package com.sg.superhumanSightings.data;

import com.sg.superhumanSightings.dao.*;
import com.sg.superhumanSightings.entity.*;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.*;

import org.mockito.internal.matchers.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrganizationDaoDBTest {

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
    public void testAddandGetOrganization(){
        Organization org = new Organization();
        org.setName("Xavier Institute");
        org.setAddress("Cambridge, MA, United States");
        org.setDescription("Train young mutants in controlling their powers");
        org.setEmail("xavierInstitute@gmail.com");
        org.setPhoneNumber("415-290-7907");

        organizationDao.addOrganization(org);

        Organization fromDao = organizationDao.getOrganizationById(org.getId());

        assertEquals(org, fromDao);

    }

    @Test
    public void testGetAllOrganizations() {

        Organization org = new Organization();
        org.setName("Xavier Institute");
        org.setAddress("Cambridge, MA, United States");
        org.setDescription("Train young mutants in controlling their powers");
        org.setEmail("xavierInstitute@gmail.com");
        org.setPhoneNumber("415-290-7907");

        organizationDao.addOrganization(org);

        Organization org2 = new Organization();
        org2.setName("Super people institute");
        org2.setAddress("Cambridge, MA, United States");
        org2.setDescription("Learn to fly");
        org2.setEmail("superpeople@gmail.com");
        org2.setPhoneNumber("415-290-7907");

        organizationDao.addOrganization(org2);

        List<Organization> organizations = organizationDao.getAllOrganizations();

        assertEquals(2, organizations.size());
        assertTrue(organizations.contains(org));
        assertTrue(organizations.contains(org2));
    }

    @Test
    public void testUpdateOrganization(){
        Organization org = new Organization();
        org.setName("Xavier Institute");
        org.setAddress("Cambridge, MA, United States");
        org.setDescription("Train young mutants in controlling their powers");
        org.setEmail("xavierInstitute@gmail.com");
        org.setPhoneNumber("415-290-7907");

        organizationDao.addOrganization(org);

        Organization fromDao = organizationDao.getOrganizationById(org.getId());
        assertEquals(org, fromDao);

        org.setName("The Bridge");
        organizationDao.updateOrganization(org);

        assertNotEquals(org, fromDao);

        fromDao = organizationDao.getOrganizationById(org.getId());

        assertEquals(org, fromDao);
    }

    @Test
    public void testDeleteOrganizationById() {
        //create 2 new organizations
        Organization org = new Organization();
        org.setName("Xavier Institute");
        org.setAddress("Cambridge, MA, United States");
        org.setDescription("Train young mutants in controlling their powers");
        org.setEmail("xavierInstitute@gmail.com");
        org.setPhoneNumber("415-290-7907");

        organizationDao.addOrganization(org);

        Organization org2 = new Organization();
        org2.setName("Super people institute");
        org2.setAddress("Cambridge, MA, United States");
        org2.setDescription("Learn to fly");
        org2.setEmail("superpeople@gmail.com");
        org2.setPhoneNumber("415-290-7907");

        organizationDao.addOrganization(org2);

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

        organizations.add(org);
        organizations.add(org2);
        sh.setOrganizations(organizations);

        //add superhuman
        superhumanDao.addSuperhuman(sh);

        //get organizations (count and check contains)
        List<Organization> orgsFromDao = organizationDao.getAllOrganizations();

        assertEquals(2, orgsFromDao.size());
        assertTrue(orgsFromDao.contains(org));
        assertTrue(orgsFromDao.contains(org2));

        //get superhuman and count their organizations
        Superhuman shFromDao = superhumanDao.getSuperhumanById(sh.getId());

        assertEquals(2, shFromDao.getOrganizations().size());

        //delete organization
        organizationDao.deleteOrganizationById(org.getId());

        //get organizations (count and check contains)
        orgsFromDao = organizationDao.getAllOrganizations();

        assertEquals(1, orgsFromDao.size());
        assertFalse(orgsFromDao.contains(org));
        assertTrue(orgsFromDao.contains(org2));

        //get superhuman and count their organizations
        shFromDao = superhumanDao.getSuperhumanById(sh.getId());
        assertEquals(1, shFromDao.getOrganizations().size());
    }

}
