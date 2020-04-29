package com.fullstack.devops.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fullstack.devops.model.User;
import com.fullstack.devops.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository userRepository;

	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public List<User> getAllUsers() {

		logger.debug("Get all Users...");
		List<User> users = new ArrayList<>();
		try {
			userRepository.findAll().forEach(users::add);
			logger.debug(users.toString());
		} catch (Exception e) {
			logger.error("Exception: ", e);
		}
		return users;
	}

}
