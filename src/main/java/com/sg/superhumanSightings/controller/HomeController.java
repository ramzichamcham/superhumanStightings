package com.sg.superhumanSightings.controller;

import com.sg.superhumanSightings.dao.SightingDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @Autowired
    SightingDao sightingDao;

    @GetMapping("index")
    public String displayHomePage(Model model){
        //get 10 most recent sightings to display in table

        //
        return "index";
    }
}
