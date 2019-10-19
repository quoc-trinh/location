package com.quizz.location.model.data;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.core.index.TextIndexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "location")
public class LocationEntity {
	@Id
	private String id;

	@TextIndexed
	private String locationName;

	private GeoJsonPoint position;
}
