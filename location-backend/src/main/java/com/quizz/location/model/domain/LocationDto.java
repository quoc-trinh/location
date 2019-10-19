package com.quizz.location.model.domain;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class LocationDto {
	private String id;

	@NotNull
	private String locationName;

	@DecimalMax("90")
	@DecimalMin("-90")
	private double lat;

	@DecimalMax("180")
	@DecimalMin("-180")
	private double lng;
}
