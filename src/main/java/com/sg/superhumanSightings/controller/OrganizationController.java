package com.sg.superhumanSightings.controller;

import com.sg.superhumanSightings.dao.OrganizationDao;
import com.sg.superhumanSightings.entity.Organization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class OrganizationController {

    @Autowired
    OrganizationDao organizationDao;

    @GetMapping("organizations")
    public String displayOrganizations(Model model){
        List<Organization> organizations = organizationDao.getAllOrganizations();
        model.addAttribute("organizations", organizations);
        return "organizations";

    }

}
