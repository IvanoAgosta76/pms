package com.fullstack.devops.model;

import java.sql.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

@Entity
@Table(name="project")
public class Project {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(name = "name")
	@NotNull
	@Size(min=2, max=50)
	private String name;

	@Column(name = "start_date")
	@NotNull
	private Date startDate;
	
	@Column(name = "end_date")
	@NotNull
	private Date endDate;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fk_user")
	@NotNull
	private User user;
	
	@OneToMany(mappedBy="project")
	@Cascade(CascadeType.DELETE)
    Set<Activity> activities;
	
	@Column(name="description")
	private String description;
	
	@Column(name="plannedHours")
	@Min(0)
	private int plannedHours = 0;
	
	public int getPlannedHours() {
		return plannedHours;
	}

	public void setPlannedHours(int plannedHours) {
		this.plannedHours = plannedHours;
	}

	public Set<Activity> getActivities() {
		return activities;
	}

	public void setActivities(Set<Activity> activities) {
		this.activities = activities;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
	@Override
	public String toString() {
		return "Project [id=" + id + ", projectName=" + name + ", startDate=" + startDate + ", endDate=" + endDate  + ", Description=" + description + "]";
	}
}
