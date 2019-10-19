package com.quizz.location.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Collections;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.quizz.location.model.domain.LocationDto;
import com.quizz.location.service.api.LocationService;

public class LocationControllerUTest {
	@InjectMocks
	private LocationController sut;

	@Mock
	private LocationService service;

	private MockMvc mockMvc;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(sut).build();
	}

	@Test
	public void testGetAllLocations() throws Exception {
		when(service.getAllLocations()).thenReturn(createAllLocations());

		String result = mockMvc.perform(get("/locations/")
				.accept(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isOk())
				.andReturn()
				.getResponse().getContentAsString();

		assertThat(result).contains("\"locationName\":\"Berlin\"");
	}

	@Test
	public void testUpdateInvalidLocation() throws Exception {
		when(service.getAllLocations()).thenReturn(createAllLocations());

		String location = "{\n" +
				"\t\"locationName\" : \"Name\",\n" +
				"\t\"lat\" : 91,\n" +
				"\t\"lgn\" : 90\n" +
				"}";
		String result = mockMvc.perform(post("/locations/").content(location)
				.contentType(APPLICATION_JSON))
				.andExpect(status().isBadRequest())
				.andReturn()
				.getResponse().getContentAsString();

		assertThat(result).contains("");
	}

	@Test
	public void testUpdateValidLocation() throws Exception {
		when(service.getAllLocations()).thenReturn(createAllLocations());

		String location = "{\n" +
				"\t\"locationName\" : \"Name\",\n" +
				"\t\"lat\" : 90,\n" +
				"\t\"lgn\" : 90\n" +
				"}";
		String result = mockMvc.perform(post("/locations/").content(location)
				.contentType(APPLICATION_JSON))
				.andExpect(status().isOk())
				.andReturn()
				.getResponse().getContentAsString();

		assertThat(result).contains("");
	}

	private List<LocationDto> createAllLocations() {
		LocationDto locationDto = new LocationDto();
		locationDto.setId("1");
		locationDto.setLng(13);
		locationDto.setLat(13);
		locationDto.setLocationName("Berlin");
		return Collections.singletonList(locationDto);
	}

}
