package com.fullstack.devops.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.fullstack.devops.constraint.WorkedHoursConstraint;
import com.fullstack.devops.repository.ActivityRepository;

public class WorkedHoursValidator implements ConstraintValidator<WorkedHoursConstraint, Integer> {

	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	ActivityRepository activityRepository;
	
	@Override
	public boolean isValid(Integer value, ConstraintValidatorContext context) {
		// TODO Auto-generated method stub
		logger.debug("Worked Hours Field Validator");
		return true;
	}

}
