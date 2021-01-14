package com.sg.superhumanSightings.controller;

import com.sg.superhumanSightings.dao.OrganizationDao;
import com.sg.superhumanSightings.entity.Organization;
import com.sg.superhumanSightings.entity.Superhuman;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
public class OrganizationController {

    @Autowired
    OrganizationDao organizationDao;

    Set<ConstraintViolation<Organization>> violations = new HashSet<>();


    @GetMapping("organizations")
    public String displayOrganizations(Model model){
        List<Organization> organizations = organizationDao.getAllOrganizations();
        model.addAttribute("organizations", organizations);
        model.addAttribute("errors", violations);
        return "organizations";

    }

    @PostMapping("addOrganization")
    public String addOrganization(HttpServletRequest request){
        String name = request.getParameter("organizationName");
        String description = request.getParameter("organizationDescription");
        String address = request.getParameter("organizationAddress");
        String phoneNumber = request.getParameter("organizationPhoneNumber");
        String email = request.getParameter("organizationEmail");

        Organization organization = new Organization();
        organization.setName(name);
        organization.setDescription(description);
        organization.setAddress(address);
        organization.setPhoneNumber(phoneNumber);
        organization.setEmail(email);

        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
        violations = validate.validate(organization);

        if(violations.isEmpty()){
            organizationDao.addOrganization(organization);
        }

        return "redirect:/organizations";
    }

    @GetMapping("deleteOrganization")
    public String deleteOrganization(HttpServletRequest request){
        int id = Integer.parseInt(request.getParameter("id"));
        organizationDao.deleteOrganizationById(id);

        return "redirect:/organizations";
    }

    @GetMapping("editOrganization")
    public String editOrganization(HttpServletRequest request, Model model){
        int id = Integer.parseInt(request.getParameter("id"));
        Organization organization = organizationDao.getOrganizationById(id);
        model.addAttribute("organization", organization);
        return "editOrganization";
    }

    @PostMapping("editOrganization")
    public String performEditOrganization(HttpServletRequest request){
        int id = Integer.parseInt(request.getParameter("id"));

        String name = request.getParameter("organizationName");
        String description = request.getParameter("organizationDescription");
        String address = request.getParameter("organizationAddress");
        String phoneNumber = request.getParameter("organizationPhoneNumber");
        String email = request.getParameter("organizationEmail");

        Organization organization = organizationDao.getOrganizationById(id);
        organization.setName(name);
        organization.setDescription(description);
        organization.setAddress(address);
        organization.setPhoneNumber(phoneNumber);
        organization.setEmail(email);

        organizationDao.updateOrganization(organization);
        return "redirect:/organizations";

    }

    @GetMapping("organizationDetail")
    public String organizationDetail(Integer id, Model model) {
        Organization organization = organizationDao.getOrganizationById(id);

        model.addAttribute("organization", organization);
        return "organizationDetail";
    }


}
