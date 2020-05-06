package com.fullstack.devops.validator;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.fullstack.devops.model.Activity;
import com.fullstack.devops.repository.ActivityRepository;

@Component
public class ActivityValidator implements Validator {

	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	ActivityRepository activityRepository;

	@Override
	public void validate(Object target, Errors errors) {

		try {
			Activity activity = (Activity) target;
			List<Activity> activities = activityRepository.findByProjectAndDate(activity.getProject(), activity.getDate());

			if (activities.stream().filter(e -> e.getId() != activity.getId()).mapToInt(Activity::getWorkedHours).sum() + activity.getWorkedHours() > 24)
				errors.rejectValue("workedHours", "ExceedDailyWorkedHours", "You exceed daily working hours. Please verify if you have more than one activities on this day.");

		} catch (Exception e) {
			logger.error("Exception:", e);
			errors.reject("Internal error, please call support");
		}
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return Activity.class.equals(clazz);
	}
}
