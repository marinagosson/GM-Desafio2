package com.greenmile.desafio2.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@JsonAutoDetect(fieldVisibility = Visibility.ANY)
public class Project implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String projectName;
	private String plannedStart;
	private String plannedEnd;
	private Boolean archived = false;
	
	@OneToOne(fetch = FetchType.EAGER)
	private Employee pm;
	
	@OneToMany(fetch = FetchType.EAGER)
	private List<Employee> employees = new ArrayList<Employee>();
	
	public Long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getPlannedEnd() {
		return plannedEnd;
	}

	public void setPlannedEnd(String plannedEnd) {
		this.plannedEnd = plannedEnd;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getPlannedStart() {
		return plannedStart;
	}

	public void setPlannedStart(String plannedStart) {
		this.plannedStart = plannedStart;
	}

	public Boolean getArchived() {
		return archived;
	}

	public void setArchived(Boolean archived) {
		this.archived = archived;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Employee getPm() {
		return pm;
	}

	public void setPm(Employee pm) {
		this.pm = pm;
	}

	public List<Employee> getEmployees() {
		return employees;
	}

	public void setEmployees(List<Employee> employess) {
		this.employees = employess;
	}
	
	

}
