package com.br.backend.service;

import java.beans.FeatureDescriptor;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
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
	
	public Place update(Place place) {
		Place placeSaved = placeRepository.findById(place.getId()).orElseThrow(() -> new RuntimeException("Place not found"));
		placeSaved.setUpdatedAt(new Date());
		
		String[] nullPropertyNames = getNullPropertyNames(placeSaved);
		BeanUtils.copyProperties(placeSaved, place, nullPropertyNames);
		
		return placeRepository.save(place);
	}
	
	public static String[] getNullPropertyNames(Object source) {
        final BeanWrapper wrappedSource = new BeanWrapperImpl(source);
        return Stream.of(wrappedSource.getPropertyDescriptors())
            .map(FeatureDescriptor::getName)
            .filter(propertyName -> wrappedSource.getPropertyValue(propertyName) == null)
            .toArray(String[]::new);
    }

	public void delete(int id) {
		placeRepository.deleteById(id);
	}

	public List<Place> findAll(){
		return (List<Place>) this.placeRepository.findAll();
	}
	
	public Place findById(int id){
		return placeRepository.findById(id).orElseThrow(() -> new EmptyResultDataAccessException(1));
	}
	
	public List<Place> findByName(String name) {

		Place p = new Place();
		p.setName(name);

		ExampleMatcher matcher = ExampleMatcher.matching().withMatcher("name",GenericPropertyMatchers.contains().ignoreCase());

		Example<Place> example = Example.of(p, matcher);
		return placeRepository.findAll(example);
	}
	
}
