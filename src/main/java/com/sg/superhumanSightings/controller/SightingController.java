package com.sg.superhumanSightings.controller;

import com.sg.superhumanSightings.ServiceLayer.SuperhumanSightingsServiceLayer;
import com.sg.superhumanSightings.dao.LocationDao;
import com.sg.superhumanSightings.dao.SightingDao;
import com.sg.superhumanSightings.dao.SuperhumanDao;
import com.sg.superhumanSightings.entity.*;
import org.apache.tomcat.jni.Local;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
public class SightingController {

    @Autowired
    SightingDao sightingDao;

    @Autowired
    SuperhumanDao superhumanDao;

    @Autowired
    LocationDao locationDao;

    @Autowired
    SuperhumanSightingsServiceLayer service;

    Set<ConstraintViolation<Sighting>> violations = new HashSet<>();

    @GetMapping("sightings")
    public String displaySightings(Model model){
        List<Location> locations = locationDao.getAllLocations();
        List<Superhuman> superhumans = superhumanDao.getAllSuperhumans();
        List<Sighting> sightings = sightingDao.getAllSightings();
        model.addAttribute("sightings", sightings);
        model.addAttribute("locations", locations);
        model.addAttribute("superhumans", superhumans);
        model.addAttribute("errors", violations);
        return "sightings";

    }

    @PostMapping("addSighting")
    public String addSighting(HttpServletRequest request){
        String superhumanId = request.getParameter("sightingSuperhuman");
        String locationId = request.getParameter("sightingLocation");
        String date = request.getParameter("sightingDate");
        String time = request.getParameter("sightingTime");

        Superhuman superhuman = superhumanDao.getSuperhumanById(Integer.parseInt(superhumanId));
        Location location = locationDao.getLocationById(Integer.parseInt(locationId));

        LocalDateTime dateTime = null;
        if(date !="" && time != ""){
            dateTime = service.stringsToLocalDatetime(date, time);
        }


        Sighting sighting = new Sighting();
        sighting.setLocation(location);
        sighting.setSuperhuman(superhuman);
        sighting.setDateTime(dateTime);

        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
        violations = validate.validate(sighting);

        if(violations.isEmpty()){
            sightingDao.addSighting(sighting);
        }



        return "redirect:/sightings";
    }


    @GetMapping("deleteSighting")
    public String deleteSighting(HttpServletRequest request, Integer superhumanId, Integer locationId) {
            String stringDateTime = request.getParameter("dateTime");

            LocalDateTime dateTime= LocalDateTime.parse(stringDateTime);

        sightingDao.deleteSighting(dateTime, superhumanId, locationId);
        return "redirect:/sightings";
    }

    @GetMapping("editSighting")
    public String EditSighting(HttpServletRequest request, Integer superhumanId, Integer locationId, Model model) {
        //construct sighting to edit
        String stringDateTime = request.getParameter("dateTime");
        LocalDateTime dateTime= LocalDateTime.parse(stringDateTime);

        Superhuman superhuman = superhumanDao.getSuperhumanById(superhumanId);
        Location location = locationDao.getLocationById(locationId);

        Sighting sighting = new Sighting();
        sighting.setSuperhuman(superhuman);
        sighting.setLocation(location);
        sighting.setDateTime(dateTime);

        //get superhumans
        List<Superhuman> superhumans = superhumanDao.getAllSuperhumans();

        //get locations
        List<Location> locations = locationDao.getAllLocations();

        model.addAttribute("sightingToEdit", sighting);
        model.addAttribute("superhumans", superhumans);
        model.addAttribute("locations", locations);

        return "editSighting";
    }


    @PostMapping("editSighting")
    public String performEditSighting(HttpServletRequest request){
        //delete sighting to edit
        int superhumanToEditId = Integer.parseInt(request.getParameter("superhumanToEditId"));
        int locationToEditId = Integer.parseInt(request.getParameter("locationToEditId"));
        String dateTimeToEditString = request.getParameter("dateTimeToEdit");
        LocalDateTime dateTimeToEdit = LocalDateTime.parse(dateTimeToEditString);
        sightingDao.deleteSighting(dateTimeToEdit, superhumanToEditId, locationToEditId);

        //Add new Sighting with new values
        String superhumanId = request.getParameter("newSightingSuperhuman");
        String locationId = request.getParameter("newSightingLocation");
        String date = request.getParameter("newSightingDate");
        String time = request.getParameter("newSightingTime");

        Superhuman superhuman = superhumanDao.getSuperhumanById(Integer.parseInt(superhumanId));
        Location location = locationDao.getLocationById(Integer.parseInt(locationId));
        LocalDateTime dateTime = service.stringsToLocalDatetime(date, time);

        Sighting sighting = new Sighting();
        sighting.setLocation(location);
        sighting.setSuperhuman(superhuman);
        sighting.setDateTime(dateTime);

        sightingDao.addSighting(sighting);

        return "redirect:/sightings";

    }

}
