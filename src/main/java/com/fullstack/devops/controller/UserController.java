package com.fullstack.devops.controller;

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
import com.fullstack.devops.model.User;
import com.fullstack.devops.repository.ProjectRepository;
import com.fullstack.devops.repository.UserRepository;
import com.fullstack.devops.service.ProjectService;
import com.fullstack.devops.service.UserService;

@Controller
@RequestMapping("/pms")
public class UserController {

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	ProjectRepository projetRepository;

	@Autowired
	ProjectService projectService;

	@Autowired
	UserService userService;

	Logger logger = LoggerFactory.getLogger(this.getClass());

	/**
	 * Show All Users
	 * 
	 * @param model
	 * @return
	 */
	@GetMapping("/users")
	public String getAllUsers(Model model) {
		List<User> users = userService.getAllUsers();
		model.addAttribute("users", users);
		return "user/list-users";
	}

	/**
	 * Add new User
	 * 
	 * @param model
	 * @return
	 */
	@GetMapping(path = { "/user/add" })
	public String addUser(Model model) {
		model.addAttribute("user", new User());
		return "user/add-user";
	}

	/**
	 * Save new User
	 * 
	 * @param user
	 * @param bindingResult
	 * @return
	 */
	@PostMapping(path = "/user/create")
	public String createUser(@Valid User user, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "/user/add-user";
		}
		user = userRepository.save(user);
		return "redirect:/pms/users";
	}

	/**
	 * Edit selected User by Id
	 * 
	 * @param model
	 * @param id
	 * @return
	 * @throws RecordNotFoundException
	 */
	@GetMapping(path = { "/user/update/{id}" })
	public String editUserById(Model model, @PathVariable("id") Long id) throws RecordNotFoundException {

		Optional<User> user = userRepository.findById(id);
		model.addAttribute("user", user);
		return "user/update-user";
	}

	/**
	 * Save selected User by Id
	 * 
	 * @param user
	 * @param bindingResult
	 * @return
	 */
	@PostMapping(path = "/user/update")
	public String updateUser(@Valid User user, BindingResult bindingResult) {

		if (bindingResult.hasErrors()) {
			return "user/update-user";
		}
		user = userRepository.save(user);
		return "redirect:/pms/users";
	}

	/**
	 * Delete seleted User by Id
	 * 
	 * @param model
	 * @param id
	 * @return
	 * @throws RecordNotFoundException
	 */
	@GetMapping(path = "/user/delete/{id}")
	public String deleteUserById(Model model, @PathVariable("id") Long id) throws RecordNotFoundException {
		List<Project> linkedProjects;
		
		Optional<User> opt = userRepository.findById(id);
		if( !opt.isPresent() ) {
			model.addAttribute("users", userService.getAllUsers());
			model.addAttribute("message", "User does not exist!");
			return "user/list-users";
		}
		
		linkedProjects = projectService.getProjectsByUser(opt.get());
		if (!linkedProjects.isEmpty()) {
			model.addAttribute("users", userService.getAllUsers());
			model.addAttribute("message", opt.get().getFirstname() + " " + opt.get().getLastname() + " delete not allowed due to the linked projects [" + linkedProjects.stream().map(Project::getName).collect(Collectors.joining(", ")) + "]");
			return "user/list-users";
		}
		
		userRepository.deleteById(id);
		return "redirect:/pms/users";
	}

	/**
	 * Delete All Users
	 * 
	 * @return
	 * @throws RecordNotFoundException
	 */
	@GetMapping(path = "/user/deleteall")
	public String deleteAllUsers(Model model) throws RecordNotFoundException {
		
		if (projetRepository.count()>0) {
			model.addAttribute("users", userService.getAllUsers());
			model.addAttribute("message", "Delete not allowed due to the linked user's projects");
			return "user/list-users";
		}
		userRepository.deleteAll();
		return "redirect:/pms/users";
	}
}
