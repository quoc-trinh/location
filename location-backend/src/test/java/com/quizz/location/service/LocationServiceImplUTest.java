package com.quizz.location.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;

import com.quizz.location.model.data.LocationEntity;
import com.quizz.location.model.domain.LocationDto;
import com.quizz.location.model.mapper.LocationMapper;
import com.quizz.location.persistence.LocationMongoRepository;
import com.quizz.location.service.impl.LocationServiceImpl;

public class LocationServiceImplUTest {
	@InjectMocks
	private LocationServiceImpl sut;

	@Mock
	private MongoTemplate mongoTemplate;

	@Mock
	private LocationMapper mapper;

	@Mock
	private LocationMongoRepository repository;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testSearchWithRadiusNull() {
		String queryStr = "Berlin";
		List searchResult = new ArrayList();
		searchResult.add(createLocationEntity());
		searchResult.add(createLocationEntity2());
		when(mongoTemplate.find(any(), any(), anyString())).thenReturn(searchResult);

		List<LocationDto> result = sut.searchLocations(queryStr, null);

		assertThat(result).hasSize(1);
	}

	@Test
	public void testSearchWithRadiusNotNull() {
		String queryStr = "Berlin";
		List searchResult = new ArrayList();
		searchResult.add(createLocationEntity());
		searchResult.add(createLocationEntity2());
		when(mongoTemplate.find(any(), any(), anyString())).thenReturn(searchResult);
		when(mongoTemplate.find(any(), any())).thenReturn(searchResult);
		when(mapper.locationsToLocationDtos(searchResult)).thenReturn(Arrays.asList(createLocationDto(), createLocationDto2()));

		List<LocationDto> result = sut.searchLocations(queryStr, 200d);

		assertThat(result).hasSize(2);
	}


	private LocationEntity createLocationEntity() {
		LocationEntity location = new LocationEntity();
		location.setId("1");
		location.setPosition(new GeoJsonPoint(13.405838, 52.531261));
		location.setLocationName("Berlin");
		return location;
	}

	private LocationEntity createLocationEntity2() {
		LocationEntity location = new LocationEntity();
		location.setId("2");
		location.setPosition(new GeoJsonPoint(6.921272, 50.960157));
		location.setLocationName("Cologne");
		return location;
	}

	private LocationDto createLocationDto() {
		LocationDto location = new LocationDto();
		location.setId("1");
		location.setLng(13.405838d);
		location.setLat(52.531261);
		location.setLocationName("Berlin");
		return location;
	}

	private LocationDto createLocationDto2() {
		LocationDto location = new LocationDto();
		location.setId("2");
		location.setLng(6.92127);
		location.setLat(50.960157);
		location.setLocationName("Cologne");
		return location;
	}
}
