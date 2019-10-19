package com.quizz.location.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.quizz.location.exception.ResourceNotFoundException;
import com.quizz.location.model.domain.LocationDto;
import com.quizz.location.service.api.LocationService;

@RestController
@RequestMapping("/locations")
@CrossOrigin(origins = "http://localhost:4200")
public class LocationController {

	@Autowired
	private LocationService service;

	/**
	 * API get location by id
	 *
	 * @param id
	 * @return
	 * @throws ResourceNotFoundException
	 */
	@GetMapping("/{id}")
	public LocationDto get(@PathVariable(value = "id") String id) throws ResourceNotFoundException {
		return service.getLocationById(id);
	}

	/**
	 * Api save a location
	 *
	 * @param locationDto
	 */
	@PostMapping
	public void save(@Valid @RequestBody LocationDto locationDto) {
		service.saveLocation(locationDto);
	}

	/**
	 * Api update a location
	 *
	 * @param id
	 * @param updatedLocation
	 * @throws ResourceNotFoundException
	 */
	@PutMapping(value = "/{id}")
	public void update(@PathVariable String id, @Valid @RequestBody LocationDto updatedLocation) throws ResourceNotFoundException {
		LocationDto location = service.getLocationById(id);
		location.setLocationName(updatedLocation.getLocationName());
		location.setLat(updatedLocation.getLat());
		location.setLng(updatedLocation.getLng());
		service.saveLocation(location);
	}

	/**
	 * API delete location by id
	 *
	 * @param id
	 */
	@DeleteMapping("/{id}")
	public void delete(@PathVariable(value = "id") String id) {
		service.deleteLocationById(id);
	}

	/**
	 * API search locations based on a query string and within a radius
	 *
	 * @param query
	 * @param radius
	 * @return
	 */
	@GetMapping(path = "/search")
	public List<LocationDto> search(@RequestParam(value = "query") String query, @RequestParam(value = "radius") Double radius) {
		return service.searchLocations(query, radius);
	}

	/**
	 * API get all locations in collection
	 *
	 * @return
	 */
	@GetMapping(path = "/")
	public List<LocationDto> getAll() {
		return service.getAllLocations();
	}

	/**
	 * API get location by a specificName
	 *
	 * @param locationName
	 * @return
	 */
	@GetMapping
	public LocationDto getByLocationName(@RequestParam(value = "locationName") String locationName) {
		return service.getLocationByLocationName(locationName);
	}
}
