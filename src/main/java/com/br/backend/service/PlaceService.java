package com.br.backend.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers;
import org.springframework.stereotype.Service;

import com.br.backend.model.Place;
import com.br.backend.repository.PlaceRepository;

@Service 
public class PlaceService {

	@Autowired 
	private PlaceRepository placeRepository;
	
	public Place insert(Place place) {			
		place.setCreatedAt(new Date());
		return this.placeRepository.save(place);
	}
	
	public Place update(Long id, Place place) {
		Place placeSaved = placeRepository.findById(id.intValue()).orElseThrow(() -> new RuntimeException("Place not found"));
		
		BeanUtils.copyProperties(place, placeSaved, "createdAt");
		
		placeSaved.setUpdatedAt(new Date());
		return placeRepository.save(place);
	}

	public void delete(Long id) {
		placeRepository.deleteById(id.intValue());
	}

	public List<Place> findAll(){
		return this.placeRepository.findAll();
	}
	
	public Place findById(Long id){
		return this.placeRepository.findById(id.intValue()).orElseThrow(() -> new EmptyResultDataAccessException(1));
	}
	
	public List<Place> findByName(String name) {

		Place p = new Place();
		p.setName(name);

		ExampleMatcher matcher = ExampleMatcher.matching().withMatcher("name",GenericPropertyMatchers.contains().ignoreCase());

		Example<Place> example = Example.of(p, matcher);
		return placeRepository.findAll(example);
	}
	
}
