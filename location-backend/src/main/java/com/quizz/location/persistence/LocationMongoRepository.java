package com.quizz.location.persistence;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.quizz.location.model.data.LocationEntity;

public interface LocationMongoRepository extends MongoRepository<LocationEntity, String> {
	LocationEntity findByLocationName(String locationName);
}
