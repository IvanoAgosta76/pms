package com.fullstack.devops.service;

import java.util.List;

import com.fullstack.devops.model.Project;
import com.fullstack.devops.model.User;

public interface ProjectService {
	public List<Project> getProjectsByUser(User user);
}
