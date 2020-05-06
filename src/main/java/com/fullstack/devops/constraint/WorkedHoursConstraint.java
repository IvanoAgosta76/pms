package com.fullstack.devops.constraint;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.fullstack.devops.validator.WorkedHoursValidator;

@Documented
@Constraint(validatedBy = WorkedHoursValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface WorkedHoursConstraint {
	String message() default "Invalid worked hours";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
