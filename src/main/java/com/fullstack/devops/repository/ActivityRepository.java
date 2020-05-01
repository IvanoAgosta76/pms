package com.fullstack.devops.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.fullstack.devops.model.Activity;
import com.fullstack.devops.model.Project;

public interface ActivityRepository extends CrudRepository<Activity, Long> {
	List<Activity> findByProject(Project project);
}
