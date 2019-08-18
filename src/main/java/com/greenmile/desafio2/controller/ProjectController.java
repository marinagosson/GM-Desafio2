package com.greenmile.desafio2.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.greenmile.desafio2.ApiResponseObject;
import com.greenmile.desafio2.Utils;
import com.greenmile.desafio2.entities.Employee;
import com.greenmile.desafio2.entities.Project;
import com.greenmile.desafio2.repository.EmployeeRepository;
import com.greenmile.desafio2.repository.ProjectRepository;

@Controller
public class ProjectController {

	@Autowired
	private ProjectRepository pr;
	
	@Autowired
	private EmployeeRepository er;

	@ResponseBody
	@GetMapping("/projects")
	public List<Project> projects() {
		List<Project> projects = new ArrayList<>();
		for(Project project: pr.findAll()) {
			projects.add(project);
		}
		return projects;
	}

	@ResponseBody
	@PatchMapping("/project/{id}")
	public ApiResponseObject<Project> patch(@RequestBody Project project, @PathVariable("id") String id) {
		
		Project p = pr.findById(new Long(id)).orElse(null);
		
		if(project.getEmployees() != null) {
			for(Employee employee : project.getEmployees()) {
				Employee e = er.findById(employee.getId()).orElse(null);
				if(e == null) 
					return new ApiResponseObject<Project>("404", "Employee with id = " + employee.getId()  + " not found", null);
				p.getEmployees().add(employee);
			}
		}
		
		if(project.getPlannedEnd() != null && !project.getPlannedEnd().isEmpty()) {
			try {
				Utils.isValidFormat(project.getPlannedEnd());
			} catch (Exception e) {
				e.printStackTrace();
				return new ApiResponseObject<Project>("400", e.getMessage(), null);
			}
		}
		
		if(project.getPlannedEnd() != null && !project.getPlannedEnd().isEmpty()) {
			try {
				Utils.isValidFormat(project.getPlannedStart());
			} catch (Exception e) {
				e.printStackTrace();
				return new ApiResponseObject<Project>("400", e.getMessage(), null);
			}
		}
		
		if (p == null) {
			return new ApiResponseObject<Project>("404", "Project not found", null);
		}
		
		if(project.getPm() != null) {
			Employee pm = er.findById(project.getPm().getId()).orElse(null);

			if (pm == null) {
				return new ApiResponseObject<Project>("404", "PM not found", null);
			} else {
				project.setPm(pm);
			}
		}
		
		p.setId(new Long(id));
		
		if(project.getProjectName() != null && !project.getProjectName().isEmpty())
			p.setProjectName(project.getProjectName());
		
		if(project.getPlannedEnd() != null && !project.getPlannedEnd().isEmpty())
			p.setPlannedEnd(project.getPlannedEnd());
		
		if(project.getPlannedStart() != null && !project.getPlannedStart().isEmpty())
			p.setPlannedStart(project.getPlannedStart());
		
		if(project.getArchived() != null)
			p.setArchived(project.getArchived());
		
		pr.save(p);
		return new ApiResponseObject<Project>("200", "Successfully updated", p);
	}

	@ResponseBody
	@PostMapping("/project")
	public ApiResponseObject<Project> project(@RequestBody Project project) {
		
		if(project.getPlannedEnd() != null && !project.getPlannedEnd().isEmpty()) {
			try {
				Utils.isValidFormat(project.getPlannedEnd());
			} catch (Exception e) {
				e.printStackTrace();
				return new ApiResponseObject<Project>("400", e.getMessage(), null);
			}
		}
		
		if(project.getPlannedEnd() != null && !project.getPlannedEnd().isEmpty()) {
			try {
				Utils.isValidFormat(project.getPlannedStart());
			} catch (Exception e) {
				e.printStackTrace();
				return new ApiResponseObject<Project>("400", e.getMessage(), null);
			}
		}

		if (project.getProjectName() == null || project.getProjectName().isEmpty())
			return new ApiResponseObject<Project>("400", "Name cannot be null or empty", null);

		if (project.getPlannedStart() == null || project.getPlannedStart().isEmpty()) {
			
			try {
				Utils.isValidFormat(project.getPlannedStart());
			} catch (Exception e) {
				e.printStackTrace();
				return new ApiResponseObject<Project>("400", e.getMessage(), null);
			}
			
			return new ApiResponseObject<Project>("400", "Start date cannot be null or empty", null);
		}

		if (project.getPlannedEnd() == null || project.getPlannedEnd().isEmpty()) {
			
			try {
				Utils.isValidFormat(project.getPlannedEnd());
			} catch (Exception e) {
				e.printStackTrace();
				return new ApiResponseObject<Project>("400", e.getMessage(), null);
			}
			
			return new ApiResponseObject<Project>("400", "End date cannot be null or empty", null);
		}

		Employee pm = er.findById(project.getPm().getId()).orElse(null);

		if (pm == null) {
			return new ApiResponseObject<Project>("404", "PM not found", null);
		} else {
			project.setPm(pm);
		}

		Project p = pr.save(project);
		pr.save(p);
		return new ApiResponseObject<Project>("200", "Saved succefully", p);
	}
	
	@ResponseBody
	@PostMapping("/project/{id}/employee")
	public ApiResponseObject<Project> project(@RequestBody Project project, @PathVariable("id") String id) {

		Project p = pr.findById(new Long(id)).orElse(null);

		if (p == null) {
			return new ApiResponseObject<Project>("404", "Project not found", null);
		} else {
			
			for(Employee employee : project.getEmployees()) {
				Employee e = er.findById(employee.getId()).orElse(null);
				if(e == null) 
					return new ApiResponseObject<Project>("404", "Employee with id = " + employee.getId()  + " not found", null);
				p.getEmployees().add(employee);
			}
			
		}

		pr.save(p);
		
		return new ApiResponseObject<Project>("200", "Saved succefully", p);
	}

}
