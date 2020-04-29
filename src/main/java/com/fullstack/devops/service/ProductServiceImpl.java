package com.fullstack.devops.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fullstack.devops.model.Project;
import com.fullstack.devops.model.User;
import com.fullstack.devops.repository.ProjectRepository;

@Service
public class ProductServiceImpl implements ProjectService {

	@Autowired
	ProjectRepository projectRepository;

	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public List<Project> getProjectsByUser(User user) {

		List<Project> projects = new ArrayList<>();
		try {
			projectRepository.findAll().forEach(project -> {
				if (project.getUser().getId() == user.getId())
					projects.add(project);
			});
		} catch (Exception e) {
			logger.error("Exception: ", e);
		}
		return projects;
	}

}
