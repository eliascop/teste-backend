package com.br.backend.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.br.backend.model.Place;
import com.br.backend.service.PlaceService;

@Controller
public class PlaceController {
	
    @Autowired
    PlaceService placeService;
    
    @RequestMapping(value="/list-place", method = RequestMethod.GET)
    public String showAll(Model model){
    	List<Place> places = placeService.findAll();
    	model.addAttribute("listAll",places);
    	return "lstPlace";
    }
	
    @RequestMapping(value="/new", method = RequestMethod.GET)
    public String newPlace(ModelMap model){
        return "newPlace";
    }
    
    @RequestMapping(value="/edit", method = RequestMethod.GET)
    public String edit(Model model,@RequestParam int id){
    	Place place = placeService.findById(id);
    	if(place!=null) {
    		model.addAttribute("place",place);
    		return "newPlace";	
    	}else
    		return "index";
    }
    
    @RequestMapping(value="/new", method = RequestMethod.POST)
    public String save(ModelMap model, @RequestParam String name, @RequestParam String slug,
    								   @RequestParam String city, @RequestParam String state){

        Place place = new Place(name,slug,city,state);
        place.setCreatedAt(new Date());
        placeService.insert(place);
        return "index";

    }
}
