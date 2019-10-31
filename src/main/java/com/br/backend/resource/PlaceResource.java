package com.br.backend.resource;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.br.backend.model.Place;
import com.br.backend.service.PlaceService;

@RestController
@RequestMapping("place")
public class PlaceResource {

	@Autowired
	private PlaceService placeService;
	
	@GetMapping("/findAll")
	public ResponseEntity<?> findAll(){
		return ResponseEntity.ok(this.placeService.findAll());
	}
	
	@PostMapping
	public ResponseEntity<?> insert(@Valid @RequestBody Place place) {
		return ResponseEntity.status(HttpStatus.CREATED).body(placeService.insert(place));
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> update(@PathVariable int id, @Valid @RequestBody Place place) {
		return ResponseEntity.ok(placeService.update(id, place));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable int id) {
		placeService.delete(id);
		return ResponseEntity.noContent().build();
	}

	@GetMapping
	public ResponseEntity<?> find(String name) {
		return ResponseEntity.ok(placeService.findByName(name));
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> find(@PathVariable int id) {
		return ResponseEntity.ok(placeService.findById(id));
	}

}
