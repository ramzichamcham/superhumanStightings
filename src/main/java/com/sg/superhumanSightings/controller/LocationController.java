package com.sg.superhumanSightings.controller;

import com.sg.superhumanSightings.dao.LocationDao;
import com.sg.superhumanSightings.dao.SightingDao;
import com.sg.superhumanSightings.dao.SuperhumanDao;
import com.sg.superhumanSightings.entity.Location;
import com.sg.superhumanSightings.entity.Organization;
import com.sg.superhumanSightings.entity.Sighting;
import com.sg.superhumanSightings.entity.Superpower;
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
public class LocationController {

    @Autowired
    LocationDao locationDao;

    @Autowired
    SightingDao sightingDao;

    @Autowired
    SuperhumanDao superhumanDao;

    Set<ConstraintViolation<Location>> violations = new HashSet<>();

    @GetMapping("locations")
    public String displayLocations(Model model){

        List<Location> locations = locationDao.getAllLocations();
        model.addAttribute("locations", locations);
        model.addAttribute("errors", violations);
        return "locations";
    }

    @PostMapping("addLocation")
    public String addLocation(HttpServletRequest request){

        String name = request.getParameter("locationName");
        String description = request.getParameter("locationDescription");
        String address = request.getParameter("locationAddress");

        String latitudeString = request.getParameter("locationLatitude");
        Double latitudeDouble;

        if(latitudeString != ""){
            latitudeDouble = Double.parseDouble(latitudeString);
        }else{
            latitudeDouble = null;
        }

        String longitudeString = request.getParameter("locationLongitude");
        Double longitudeDouble;

        if(longitudeString != ""){
            longitudeDouble = Double.parseDouble(longitudeString);
        }else{
            longitudeDouble = null;
        }

        Location location = new Location();
        location.setName(name);
        location.setDescription(description);
        location.setAddress(address);
        location.setLatitude(latitudeDouble);
        location.setLongitude(longitudeDouble);

        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
        violations = validate.validate(location);

        if(violations.isEmpty()){
            locationDao.addLocation(location);
        }

        return "redirect:/locations";

    }

    @GetMapping("deleteLocation")
    public String deleteLocation(HttpServletRequest request){
        int id = Integer.parseInt(request.getParameter("id"));
        locationDao.deleteLocationById(id);

        return "redirect:/locations";
    }

    @GetMapping("editLocation")
    public String editLocation(HttpServletRequest request, Model model){
        int id = Integer.parseInt(request.getParameter("id"));
        Location location = locationDao.getLocationById(id);
        model.addAttribute("location", location);
        model.addAttribute("errors", violations);
        return "editLocation";
    }

    @PostMapping("editLocation")
    public String performEditLocation(HttpServletRequest request, Model model){
        int id = Integer.parseInt(request.getParameter("id"));

        String name = request.getParameter("locationName");
        String description = request.getParameter("locationDescription");
        String address = request.getParameter("locationAddress");



        Location location = locationDao.getLocationById(id);
        location.setName(name);
        location.setDescription(description);
        location.setAddress(address);


        String latitudeString = request.getParameter("locationLatitude");
        Double latitudeDouble;

        if(latitudeString != ""){
            latitudeDouble = Double.parseDouble(latitudeString);
        }else{
            latitudeDouble = null;
        }

        String longitudeString = request.getParameter("locationLongitude");
        Double longitudeDouble;

        if(longitudeString != ""){
            longitudeDouble = Double.parseDouble(longitudeString);
        }else{
            longitudeDouble = null;
        }

        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
        violations = validate.validate(location);

        if(violations.isEmpty()){
            locationDao.updateLocation(location);
            return "redirect:/locations";
        }else{
            model.addAttribute("errors", violations);
            model.addAttribute("location", location);
            return "editLocation";
        }



    }

    @GetMapping("locationDetail")
    public String superhumanDetail(Integer id, Model model) {
        Location location = locationDao.getLocationById(id);

        model.addAttribute("location", location);
        return "locationDetail";
    }

}
