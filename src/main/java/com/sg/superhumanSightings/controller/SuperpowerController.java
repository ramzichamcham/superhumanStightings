package com.sg.superhumanSightings.controller;

import com.sg.superhumanSightings.dao.OrganizationDao;
import com.sg.superhumanSightings.dao.SuperhumanDao;
import com.sg.superhumanSightings.dao.SuperpowerDao;
import com.sg.superhumanSightings.entity.Organization;
import com.sg.superhumanSightings.entity.Superpower;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class SuperpowerController {
    @Autowired
    SuperpowerDao superpowerDao;

    @Autowired
    SuperhumanDao superhumanDao;

    @Autowired
    OrganizationDao organizationDao;


    @GetMapping("superpowers")
    public String displaySuperpowers(Model model){
        List<Superpower> superpowers = superpowerDao.getAllSuperpowers();
        model.addAttribute("superpowers", superpowers);
        return "superpowers";
    }

    @PostMapping("addSuperpower")
    public String addSuperpower(HttpServletRequest request){
        String name = request.getParameter("superpowerName");
        String description =request.getParameter("superpowerDescription");

        Superpower superpower = new Superpower();
        superpower.setName(name);
        superpower.setDescription(description);
        superpowerDao.addSuperpower(superpower);

        return "redirect:/superpowers";
    }

    @GetMapping("deleteSuperpower")
    public String deleteSuperpower(HttpServletRequest request){
        int id = Integer.parseInt(request.getParameter("id"));
        superpowerDao.deleteSuperpowerById(id);
        return "redirect:/superpowers";
    }

    @GetMapping("editSuperpower")
    public String editSuperpower(HttpServletRequest request, Model model){
        int id = Integer.parseInt(request.getParameter("id"));
        Superpower superpower = superpowerDao.getSuperpowerById(id);
        model.addAttribute("superpower", superpower);
        return "editSuperpower";
    }

    @PostMapping("editSuperpower")
    public String performEditSuperpower(HttpServletRequest request){
        int id = Integer.parseInt(request.getParameter("id"));

        Superpower superpower = superpowerDao.getSuperpowerById(id);
        superpower.setName(request.getParameter("superpowerName"));
        superpower.setDescription(request.getParameter("superpowerDescription"));
        superpowerDao.updateSuperpower(superpower);

        return "redirect:/superpowers";

    }

}
