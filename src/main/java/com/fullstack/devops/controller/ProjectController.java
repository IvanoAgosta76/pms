package com.fullstack.devops.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fullstack.devops.exception.RecordNotFoundException;
import com.fullstack.devops.model.Project;
import com.fullstack.devops.repository.ProjectRepository;
import com.fullstack.devops.repository.UserRepository;

@Controller
@RequestMapping("/pms")
public class ProjectController {

	@Autowired
	ProjectRepository projectRepository;

	@Autowired
	UserRepository userRepository;
	
	Logger logger = LoggerFactory.getLogger(this.getClass());

	/**
	 * Show All project
	 * @param model
	 * @return
	 */
	@GetMapping("/projects")
	public String getAllProjects(Model model) {
		
		logger.debug("Get all Projects...");
		List<Project> projects = new ArrayList<>();
		projectRepository.findAll().forEach(projects::add);
		logger.debug(projects.stream().map(Project::getName).collect(Collectors.joining(", ")));
		model.addAttribute("projects", projects);
		
		return "project/list-projects";
	}

	/**
	 * Add new Project
	 * @param model
	 * @return
	 */
	@GetMapping(path = { "/project/add" })
	public String addProject(Model model) {
		
		Project project = new Project();
		/**
		 *  Set start and end date to current date
		 */
		project.setStartDate(new java.sql.Date(Calendar.getInstance().getTime().getTime()));
		project.setEndDate(new java.sql.Date(Calendar.getInstance().getTime().getTime()));
		
		// Add attribures
		model.addAttribute("project", project);
		model.addAttribute("users", userRepository.findAll());
		
		return "project/add-project";
	}

	/**
	 * Save project into repository
	 * @param project
	 * @param bindingResult
	 * @return
	 */
	@PostMapping(path = "/project/create")
	public String createProject(@Valid Project project, BindingResult bindingResult) {
		
		if (bindingResult.hasErrors()) {
			return "/project/add-project";
		}
		project = projectRepository.save(project);
		
		return "redirect:/pms/projects";
	}

	/**
	 * Modify Project selected by id
	 * @param model
	 * @param id
	 * @return
	 * @throws RecordNotFoundException
	 */
	@GetMapping(path = { "/project/update/{id}" })
	public String editProjectById(Model model, @PathVariable("id") Long id) throws RecordNotFoundException {
		
		Optional<Project> project = projectRepository.findById(id);
		model.addAttribute("project", project);
		model.addAttribute("users", userRepository.findAll());
		
		return "project/update-project";
	}

	/**
	 * Save selected Project
	 * @param project
	 * @param bindingResult
	 * @return
	 */
	@PostMapping(path = "/project/update")
	public String updateProject(@Valid Project project, BindingResult bindingResult) {
		
		if (bindingResult.hasErrors()) {
			return "/project/add-project";
		}
		project = projectRepository.save(project);
		
		return "redirect:/pms/projects";
	}

	/**
	 * Delete Project by selected Id
	 * @param model
	 * @param id
	 * @return
	 * @throws RecordNotFoundException
	 */
	@GetMapping(path = "/project/delete/{id}")
	public String deleteProjectById(Model model, @PathVariable("id") Long id) throws RecordNotFoundException {
		
		projectRepository.deleteById(id);
		
		return "redirect:/pms/projects";
	}

	/**
	 * Delete All Projects
	 * @return
	 * @throws RecordNotFoundException
	 */
	@GetMapping(path = "/project/deleteall")
	public String deleteAllProjects() throws RecordNotFoundException {
		
		projectRepository.deleteAll();
		
		return "redirect:/pms/projects";
	}
}
