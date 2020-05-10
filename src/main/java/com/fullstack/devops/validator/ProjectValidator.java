package com.fullstack.devops.validator;

import java.time.temporal.ChronoUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.fullstack.devops.model.Project;
import com.fullstack.devops.model.ProjectProperties;
import com.fullstack.devops.repository.ProjectRepository;

@Component
public class ProjectValidator implements Validator {

	private static final int DAY_HOURS = 24;
	private static final int ONE_DAY = 1;

	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	ProjectRepository projectRepository;

	@Override
	public void validate(Object target, Errors errors) {

		try {
			
			Project project = (Project) target;
			
			if( project.getStartDate().compareTo(project.getEndDate()) > 0 )
				errors.rejectValue("startDate", "StartDateGreatherEndDate","Start date must be greather that end date");
			
			if ( project.getPlannedHours() > ( ChronoUnit.DAYS.between(project.getStartDate().toLocalDate(), project.getEndDate().toLocalDate()) + ONE_DAY ) * DAY_HOURS )
				errors.rejectValue("plannedHours", "ExceedDailyWorkedHours", "You exceed daily working hours for the period selected.");

		} catch (Exception e) {
			logger.error("Exception:", e);
			errors.reject("Internal error, please call support");
		}
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return Project.class.equals(clazz)  || ProjectProperties.class.equals(clazz);
	}
}
