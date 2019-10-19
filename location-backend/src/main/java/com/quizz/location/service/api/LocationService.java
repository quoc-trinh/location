package com.quizz.location.service.api;

import java.util.List;

import com.quizz.location.exception.ResourceNotFoundException;
import com.quizz.location.model.domain.LocationDto;

public interface LocationService {

	LocationDto getLocationById(String id) throws ResourceNotFoundException;

	void saveLocation(LocationDto locationDto);

	void deleteLocationById(String id);

	LocationDto getLocationByLocationName(String locationName);

	List<LocationDto> getAllLocations();

	List<LocationDto> searchLocations(String queryStr, Double radius);
}
