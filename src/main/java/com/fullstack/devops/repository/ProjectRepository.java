package com.fullstack.devops.repository;

import org.springframework.data.repository.CrudRepository;

import com.fullstack.devops.model.Project;

public interface ProjectRepository extends CrudRepository<Project, Long> {

}
