package com.sg.superhumanSightings.controller;

import com.sg.superhumanSightings.dao.OrganizationDao;
import com.sg.superhumanSightings.dao.SuperhumanDao;
import com.sg.superhumanSightings.dao.SuperpowerDao;
import com.sg.superhumanSightings.entity.Organization;
import com.sg.superhumanSightings.entity.Superhuman;
import com.sg.superhumanSightings.entity.Superpower;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
public class SuperhumanController {

    @Autowired
    SuperhumanDao superhumanDao;

    @Autowired
    SuperpowerDao superpowerDao;

    @Autowired
    OrganizationDao organizationDao;

    @GetMapping("superhumans")
    public String displaySuperhumans(Model model){
        List<Superhuman> superhumans = superhumanDao.getAllSuperhumans();
        List<Superpower> superpowers = superpowerDao.getAllSuperpowers();
        List<Organization> organizations = organizationDao.getAllOrganizations();

        model.addAttribute("superhumans", superhumans);
        model.addAttribute("superpowers", superpowers);
        model.addAttribute("organizations", organizations);
        return "superhumans";
    }


    @PostMapping("addSuperhuman")
    public String addSuperhuman(Superhuman superhuman, HttpServletRequest request){
//        Superhuman superhuman = new Superhuman();
//
//        String name = request.getParameter("superhumanName");
//        String description = request.getParameter("superhumanDescription");

        String[] superpowerIds = request.getParameterValues("superpowerIds");
        String[] organizationIds = request.getParameterValues("organizationIds");

        List<Superpower> superpowers = new ArrayList<>();
        for(String superpowerId: superpowerIds){
            superpowers.add(superpowerDao.getSuperpowerById(Integer.parseInt(superpowerId)));
        }

        List<Organization> organizations = new ArrayList<>();
        for(String organizationId: organizationIds){
            organizations.add(organizationDao.getOrganizationById(Integer.parseInt(organizationId)));
        }


        superhuman.setSuperpowers(superpowers);
        superhuman.setOrganizations(organizations);

        superhumanDao.addSuperhuman(superhuman);

        return "redirect:/superhumans";

    }

    @GetMapping("superhumanDetail")
    public String superhumanDetail(Integer id, Model model) {
        Superhuman superhuman = superhumanDao.getSuperhumanById(id);

        model.addAttribute("superhuman", superhuman);
        return "superhumanDetail";
    }


    @GetMapping("deleteSuperhuman")
    public String deleteCourse(Integer id) {
        superhumanDao.deleteSuperhumanById(id);
        return "redirect:/superhumans";
    }

    @GetMapping("editSuperhuman")
    public String editSuperhuman(Integer id, Model model) {
        Superhuman superhuman = superhumanDao.getSuperhumanById(id);
        List<Superpower> superpowers = superpowerDao.getAllSuperpowers();
        List<Organization> organizations = organizationDao.getAllOrganizations();

        model.addAttribute("superhuman", superhuman);
        model.addAttribute("superpowers", superpowers);
        model.addAttribute("organizations", organizations);
        return "editSuperhuman";
    }

    @PostMapping("editSuperhuman")
    public String performEditSuperhuman(Superhuman superhuman, HttpServletRequest request) {
        

        String[] superpowerIds = request.getParameterValues("superpowerIds");
        String[] organizationIds = request.getParameterValues("organizationIds");

        List<Superpower> superpowers = new ArrayList<>();
        for(String superpowerId: superpowerIds){
            superpowers.add(superpowerDao.getSuperpowerById(Integer.parseInt(superpowerId)));
        }

        List<Organization> organizations = new ArrayList<>();
        for(String organizationId: organizationIds){
            organizations.add(organizationDao.getOrganizationById(Integer.parseInt(organizationId)));
        }

        superhuman.setSuperpowers(superpowers);
        superhuman.setOrganizations(organizations);

        superhumanDao.updateSuperhuman(superhuman);

        return "redirect:/superhumans";
    }


}
