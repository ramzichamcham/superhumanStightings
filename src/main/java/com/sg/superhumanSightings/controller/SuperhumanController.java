package com.sg.superhumanSightings.controller;

import com.sg.superhumanSightings.dao.SuperhumanDao;
import com.sg.superhumanSightings.entity.Superhuman;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class SuperhumanController {

    @Autowired
    SuperhumanDao superhumanDao;

    @GetMapping("superhumans")
    public String displaySuperhumans(Model model){
        List<Superhuman> superhumans = superhumanDao.getAllSuperhumans();
        model.addAttribute("superhumans", superhumans);
        return "superhumans";
    }
}
