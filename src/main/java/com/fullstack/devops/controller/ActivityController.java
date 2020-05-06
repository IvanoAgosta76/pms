package com.fullstack.devops.controller;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fullstack.devops.exception.RecordNotFoundException;
import com.fullstack.devops.model.Activity;
import com.fullstack.devops.model.Project;
import com.fullstack.devops.repository.ActivityRepository;
import com.fullstack.devops.repository.ProjectRepository;
import com.fullstack.devops.validator.ActivityValidator;

@Controller
@RequestMapping("/pms")
public class ActivityController {

	@Autowired
	ActivityRepository activityRepository;

	@Autowired
	ProjectRepository projectRepository;

	@Autowired
	private ActivityValidator validator;

	Logger logger = LoggerFactory.getLogger(this.getClass());

	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		binder.addValidators(validator);
	}

	/**
	 * Show All activity activities
	 * 
	 * @param model
	 * @return
	 */
	@GetMapping(path = "/activities/{id}")
	public String getAllProjectActivites(Model model, @PathVariable("id") Long id) throws RecordNotFoundException {

		logger.debug("Get all Project activities...");
		List<Activity> activities = new ArrayList<>();

		projectRepository.findById(id).ifPresent(project -> project.getActivities().forEach(activities::add));
		logger.debug(activities.stream().map(Activity::getDescription).collect(Collectors.joining(", ")));

		activities.sort(Comparator.comparing(Activity::getDate));
		model.addAttribute("activities", activities);
		model.addAttribute("project", id);
		model.addAttribute("totWorkedHours", activities.stream().mapToInt(Activity::getWorkedHours).sum());

		return "activity/list-activities";
	}

	/**
	 * Add new Activity
	 * 
	 * @param model
	 * @return
	 */
	@GetMapping(path = { "/activity/add/{id}" })
	public String addActivity(Model model, @PathVariable("id") Long id) {

		Activity activity = new Activity();
		activity.setProject(projectRepository.findById(id).get());
		activity.setDate(new Date(Calendar.getInstance().getTime().getTime()));

		// Add attribures
		model.addAttribute("activity", activity);

		return "activity/add-activity";
	}

	/**
	 * Save activity into repository
	 * 
	 * @param activity
	 * @param bindingResult
	 * @return
	 */
	@PostMapping(path = "/activity/create")
	public String createActivity(@Valid Activity activity, BindingResult bindingResult) {

		if (bindingResult.hasErrors()) {
			return "/activity/add-activity";
		}
		activity = activityRepository.save(activity);

		return "redirect:/pms/activities/" + activity.getProject().getId();
	}

	/**
	 * Modify Activity selected by id
	 * 
	 * @param model
	 * @param id
	 * @return
	 * @throws RecordNotFoundException
	 */
	@GetMapping(path = { "/activity/update/{id}" })
	public String editActivitytById(Model model, @PathVariable("id") Long id) throws RecordNotFoundException {

		Activity activity = activityRepository.findById(id).get();
		model.addAttribute("activity", activity);

		return "activity/update-activity";
	}

	/**
	 * Save selected Activity
	 * 
	 * @param activity
	 * @param bindingResult
	 * @return
	 */
	@PostMapping(path = "/activity/update")
	public String updateActivity(@Valid Activity activity, BindingResult bindingResult) {

		if (bindingResult.hasErrors()) {
			return "/activity/update-activity";
		}
		activity = activityRepository.save(activity);

		return "redirect:/pms/activities/" + activity.getProject().getId();
	}

	/**
	 * Delete Activity by selected Id
	 * 
	 * @param model
	 * @param id
	 * @return
	 * @throws RecordNotFoundException
	 */
	@GetMapping(path = "/activity/delete/{id}")
	public String deleteActivityById(Model model, @PathVariable("id") Long id) throws RecordNotFoundException {

		Project project = activityRepository.findById(id).get().getProject();
		activityRepository.deleteById(id);

		return "redirect:/pms/activities/" + project.getId();
	}

	/**
	 * Delete All Activities
	 * 
	 * @return
	 * @throws RecordNotFoundException
	 */
	@GetMapping(path = "/activity/deleteall/{id}")
	public String deleteAllActivities(Model model, @PathVariable("id") Long id) throws RecordNotFoundException {

		List<Activity> activities = activityRepository.findByProject(projectRepository.findById(id).orElse(new Project()));
		activityRepository.deleteAll(activities);

		return "redirect:/pms/activities/" + id;
	}
}
