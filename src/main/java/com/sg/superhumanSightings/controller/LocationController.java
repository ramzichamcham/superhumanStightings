package com.sg.superhumanSightings.controller;

import com.sg.superhumanSightings.dao.LocationDao;
import com.sg.superhumanSightings.dao.SightingDao;
import com.sg.superhumanSightings.dao.SuperhumanDao;
import com.sg.superhumanSightings.entity.Location;
import com.sg.superhumanSightings.entity.Sighting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class LocationController {

    @Autowired
    LocationDao locationDao;

    @Autowired
    SightingDao sightingDao;

    @Autowired
    SuperhumanDao superhumanDao;

    @GetMapping("locations")
    public String displayLocations(Model model){
        List<Location> locations = locationDao.getAllLocations();
        model.addAttribute("locations", locations);
        return "locations";
    }

    @PostMapping("addLocation")
    public String addLocation(HttpServletRequest request){
        String name = request.getParameter("locationName");
        String description = request.getParameter("locationDescription");
        String address = request.getParameter("locationAddress");
        double latitude = Double.parseDouble(request.getParameter("locationLatitude"));
        double longitude = Double.parseDouble(request.getParameter("locationLongitude"));

        Location location = new Location();
        location.setName(name);
        location.setDescription(description);
        location.setAddress(address);
        location.setLatitude(latitude);
        location.setLongitude(longitude);

        locationDao.addLocation(location);

        return "redirect:/locations";

    }

    @GetMapping("deleteLocation")
    public String deleteLocation(HttpServletRequest request){
        int id = Integer.parseInt(request.getParameter("id"));
        locationDao.deleteLocationById(id);

        return "redirect:/locations";
    }
}
