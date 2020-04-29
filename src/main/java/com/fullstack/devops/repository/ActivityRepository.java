package com.fullstack.devops.repository;

import org.springframework.data.repository.CrudRepository;

import com.fullstack.devops.model.Activity;

public interface ActivityRepository extends CrudRepository<Activity, Long> {
}
