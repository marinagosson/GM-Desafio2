package com.greenmile.desafio2.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class CSV {

	private Long id;
	private String ProjectName;
	private String PlannedStart;
	private String PlannedEnd;
	private String PM;
	private String PMEmail;
	private String PMSkills;
	private String EmployeeName;
	private String EmployeeEmail;
	private String EmployeeTeam;
	private String EmployeeSkills;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getProjectName() {
		return ProjectName;
	}
	public void setProjectName(String projectName) {
		ProjectName = projectName;
	}
	public String getPlannedStart() {
		return PlannedStart;
	}
	public void setPlannedStart(String plannedStart) {
		PlannedStart = plannedStart;
	}
	public String getPlannedEnd() {
		return PlannedEnd;
	}
	public void setPlannedEnd(String plannedEnd) {
		PlannedEnd = plannedEnd;
	}
	public String getPM() {
		return PM;
	}
	public void setPM(String pM) {
		PM = pM;
	}
	public String getPMEmail() {
		return PMEmail;
	}
	public void setPMEmail(String pMEmail) {
		PMEmail = pMEmail;
	}
	public String getPMSkills() {
		return PMSkills;
	}
	public void setPMSkills(String pMSkills) {
		PMSkills = pMSkills;
	}
	public String getEmployeeName() {
		return EmployeeName;
	}
	public void setEmployeeName(String employeeName) {
		EmployeeName = employeeName;
	}
	public String getEmployeeEmail() {
		return EmployeeEmail;
	}
	public void setEmployeeEmail(String employeeEmail) {
		EmployeeEmail = employeeEmail;
	}
	public String getEmployeeTeam() {
		return EmployeeTeam;
	}
	public void setEmployeeTeam(String employeeTeam) {
		EmployeeTeam = employeeTeam;
	}
	public String getEmployeeSkills() {
		return EmployeeSkills;
	}
	public void setEmployeeSkills(String employeeSkills) {
		EmployeeSkills = employeeSkills;
	}
	@Override
	public String toString() {
		return "CSV [id=" + id + ", ProjectName=" + ProjectName + ", PlannedStart=" + PlannedStart + ", PlannedEnd="
				+ PlannedEnd + ", PM=" + PM + ", PMEmail=" + PMEmail + ", PMSkills=" + PMSkills + ", EmployeeName="
				+ EmployeeName + ", EmployeeEmail=" + EmployeeEmail + ", EmployeeTeam=" + EmployeeTeam
				+ ", EmployeeSkills=" + EmployeeSkills + "]";
	}
	
}
