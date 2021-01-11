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

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SuperhumanDaoDBTest {


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
    public void testAddAndGetSuperhuman(){
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

        Superhuman shFromDao = superhumanDao.getSuperhumanById(sh.getId());

        assertEquals(sh, shFromDao);

    }

    @Test
    public void testGetAllSuperhumans() {

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

        List<Superhuman> superhumans = superhumanDao.getAllSuperhumans();

        assertEquals(2, superhumans.size());
        assertTrue(superhumans.contains(sh));
        assertTrue(superhumans.contains(sh2));
    }


}
