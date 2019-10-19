package com.quizz.location.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Circle;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Metrics;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.data.mongodb.core.query.TextQuery;
import org.springframework.stereotype.Service;

import com.quizz.location.exception.ResourceNotFoundException;
import com.quizz.location.model.data.LocationEntity;
import com.quizz.location.model.domain.LocationDto;
import com.quizz.location.model.mapper.LocationMapper;
import com.quizz.location.persistence.LocationMongoRepository;
import com.quizz.location.service.api.LocationService;

@Service
public class LocationServiceImpl implements LocationService {
	@Autowired
	private LocationMongoRepository repository;

	@Autowired
	private LocationMapper mapper;

	@Autowired
	private MongoTemplate mongoTemplate;

	public LocationDto getLocationByLocationName(String locationName) {
		return mapper.locationToLocationDto(repository.findByLocationName(locationName));
	}

	public LocationDto getLocationById(String id) throws ResourceNotFoundException {
		return mapper.locationToLocationDto(repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Location " + id + "not found!")));
	}

	public List<LocationDto> getAllLocations() {
		return mapper.locationsToLocationDtos(repository.findAll());
	}

	public void saveLocation(LocationDto locationDto) {
		LocationEntity locationEntity = mapper.locationDtoToLocation(locationDto);
		repository.save(locationEntity);
	}

	public void deleteLocationById(String id) {
		repository.deleteById(id);
	}

	public List<LocationDto> searchLocations(String queryStr, Double radius) {
		LocationEntity matching = searchLocation(queryStr);
		if (matching == null) {
			return new ArrayList<>();
		}
		if (radius == null) {
			LocationDto locationDto = mapper.locationToLocationDto(matching);
			return Collections.singletonList(locationDto);
		}
		return mapper.locationsToLocationDtos(searchLocationWithin(matching, radius));
	}

	private List<LocationEntity> searchLocationWithin(LocationEntity location, Double radius) {
		Point point = new Point(location.getPosition().getX(), location.getPosition().getY());
		Distance distance = new Distance(radius, Metrics.MILES);
		Circle circle = new Circle(point, distance);
		Criteria geoCriteria = Criteria.where("position").withinSphere(circle);
		Query query = Query.query(geoCriteria);
		return mongoTemplate.find(query, LocationEntity.class);
	}

	private LocationEntity searchLocation(String queryStr) {
		TextQuery textQuery = TextQuery.queryText(new TextCriteria().matchingAny(queryStr)).sortByScore();
		List<LocationEntity> result = mongoTemplate.find(textQuery, LocationEntity.class, "location");
		if (result == null || result.isEmpty()) {
			return null;
		}
		return result.get(0);
	}

}
