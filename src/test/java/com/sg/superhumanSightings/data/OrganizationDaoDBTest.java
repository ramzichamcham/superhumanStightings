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

import org.mockito.internal.matchers.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

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

}
