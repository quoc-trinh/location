package com.quizz.location.model.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;

import com.quizz.location.model.data.LocationEntity;
import com.quizz.location.model.domain.LocationDto;

@Mapper(componentModel = "spring")
public abstract class LocationMapper {

	@Mapping(target = "lng", source = "position.x")
	@Mapping(target = "lat", source = "position.y")
	public abstract LocationDto locationToLocationDto(LocationEntity locationEntity);

	public abstract List<LocationDto> locationsToLocationDtos(List<LocationEntity> locationEntities);

	@Mapping(source = "dto", target = "position", qualifiedByName = "latlngToposition")
	public abstract LocationEntity locationDtoToLocation(LocationDto dto);

	@Named("latlngToposition")
	GeoJsonPoint toPosition(LocationDto dto) {
		return new GeoJsonPoint(dto.getLng(), dto.getLat());
	}
}
