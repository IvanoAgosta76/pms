package com.fullstack.devops.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fullstack.devops.exception.RecordNotFoundException;
import com.fullstack.devops.model.Activity;
import com.fullstack.devops.repository.ActivityRepository;
import com.fullstack.devops.repository.ProjectRepository;

@Controller
@RequestMapping("/pms")
public class ActivityController {

	@Autowired
	ActivityRepository activityRepository;
	
	@Autowired
	ProjectRepository projectRepository;
	
	Logger logger = LoggerFactory.getLogger(this.getClass());

	/**
	 * Show All project activities
	 * @param model
	 * @return
	 */
	@GetMapping(path = "/activities/{id}")
	public String getAllProjectActivites(Model model, @PathVariable("id") Long id) throws RecordNotFoundException {
		
		logger.debug("Get all Project activities...");
		List<Activity> activities = new ArrayList<>();
		
		projectRepository.findById(id).ifPresent(project->project.getActivities().forEach(activities::add));
		logger.debug(activities.stream().map(Activity::getDescription).collect(Collectors.joining(", ")));
		
		model.addAttribute("activities", activities);
		model.addAttribute("totWorkedHours", activities.stream().mapToInt(Activity::getWorkedHours).sum());
		
		return "activity/list-activities";
	}
}
