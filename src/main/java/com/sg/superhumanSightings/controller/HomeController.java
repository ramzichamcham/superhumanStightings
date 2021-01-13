package com.sg.superhumanSightings.controller;

import com.sg.superhumanSightings.dao.SightingDao;
import com.sg.superhumanSightings.entity.Sighting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {

    @Autowired
    SightingDao sightingDao;

    @GetMapping("/")
    public String displayHomePage(Model model){

        List<Sighting> sightings = sightingDao.getRecentSightings(10);

        model.addAttribute("sightings", sightings);
        return "index";
    }
}
