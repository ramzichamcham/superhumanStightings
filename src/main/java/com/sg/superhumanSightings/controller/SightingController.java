package com.sg.superhumanSightings.controller;

import com.sg.superhumanSightings.ServiceLayer.SuperhumanSightingsServiceLayer;
import com.sg.superhumanSightings.dao.LocationDao;
import com.sg.superhumanSightings.dao.SightingDao;
import com.sg.superhumanSightings.dao.SuperhumanDao;
import com.sg.superhumanSightings.entity.Location;
import com.sg.superhumanSightings.entity.Organization;
import com.sg.superhumanSightings.entity.Sighting;
import com.sg.superhumanSightings.entity.Superhuman;
import org.apache.tomcat.jni.Local;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

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


    @GetMapping("sightings")
    public String displaySightings(Model model){
        List<Location> locations = locationDao.getAllLocations();
        List<Superhuman> superhumans = superhumanDao.getAllSuperhumans();
        List<Sighting> sightings = sightingDao.getAllSightings();
        model.addAttribute("sightings", sightings);
        model.addAttribute("locations", locations);
        model.addAttribute("superhumans", superhumans);
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
        LocalDateTime dateTime = service.stringsToLocalDatetime(date, time);

        Sighting sighting = new Sighting();
        sighting.setLocation(location);
        sighting.setSuperhuman(superhuman);
        sighting.setDateTime(dateTime);

        sightingDao.addSighting(sighting);

        return "redirect:/sightings";
    }


    @GetMapping("deleteSighting")
    public String deleteSighting(HttpServletRequest request, Integer superhumanId, Integer locationId) {
            String stringDateTime = request.getParameter("dateTime");

            LocalDateTime dateTime= LocalDateTime.parse(stringDateTime);

        sightingDao.deleteSighting(dateTime, superhumanId, locationId);
        return "redirect:/sightings";
    }



}
