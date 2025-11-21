package com.shabeer.routing.domain;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface RoutePlanRepository extends MongoRepository<RoutePlan, String> {
}
