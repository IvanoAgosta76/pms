package com.fullstack.devops.model;

public class ProjectProperties {

	private int workHoursForDay;
	
	public ProjectProperties(int workHoursForDay) {
		this.workHoursForDay = workHoursForDay;
	}

	public int getWorkHoursForDay() {
		return workHoursForDay;
	}

	public void setWorkHoursForDay(int workHoursForDay) {
		this.workHoursForDay = workHoursForDay;
	}
	
}
