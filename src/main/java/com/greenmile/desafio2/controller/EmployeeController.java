package com.greenmile.desafio2.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.greenmile.desafio2.ApiResponseObject;
import com.greenmile.desafio2.Utils;
import com.greenmile.desafio2.entities.Employee;
import com.greenmile.desafio2.entities.Project;
import com.greenmile.desafio2.entities.Skill;
import com.greenmile.desafio2.entities.Team;
import com.greenmile.desafio2.repository.EmployeeRepository;
import com.greenmile.desafio2.repository.SkillRepository;
import com.greenmile.desafio2.repository.TeamRepository;

@Controller
public class EmployeeController {

	@Autowired
	private EmployeeRepository er;

	@Autowired
	private TeamRepository tr;

	@Autowired
	private SkillRepository sr;
	
	@ResponseBody
	@GetMapping("/employees")
	public Iterable<Employee> get() {
		return er.findAll();
	}

	@ResponseBody
	@PostMapping("/employee")
	public ApiResponseObject<Employee> newEmployee(@RequestBody Employee employee) {

		if (employee.getName() == null || employee.getName().isEmpty())
			return new ApiResponseObject<Employee>("400", "Name cannot be null or empty", null);

		if (employee.getEmail() == null || employee.getEmail().isEmpty()) {
			return new ApiResponseObject<Employee>("400", "Email cannot be null or empty", null);
		} else {
			if (!Utils.isValid(employee.getEmail()))
				return new ApiResponseObject<Employee>("400", "Email is not valid", null);
			for(Employee e: er.findAll()) {
				if(e.getEmail().equals(employee.getEmail()))
					return new ApiResponseObject<Employee>("400", "Email already registered", null);
			}
		}

		if (employee.getSkills() == null)
			return new ApiResponseObject<Employee>("400", "Skills cannot be null or empty", null);

		if (employee.getTeam() == null)
			return new ApiResponseObject<Employee>("400", "Team cannot be null or empty", null);

		if (employee.getTeam().getId() == null)
			return new ApiResponseObject<Employee>("400", "ID Team cannot be null or empty", null);

		Team team = tr.findById(employee.getTeam().getId()).orElse(null);

		List<Skill> skills = new ArrayList<Skill>();
		for (int i = 0; i < employee.getSkills().size(); i++) {
			Long id = employee.getSkills().get(i).getId();
			Skill s = sr.findById(id).orElse(null);
			if (s == null)
				return new ApiResponseObject<Employee>("404", "Skill with id = " + id  + " not found", null);
			skills.add(s);
		}
		
		employee.setSkills(skills);

		if (team == null) {
			return new ApiResponseObject<Employee>("404", "Team not found", null);
		} else {
			employee.setTeam(team);
		}

		er.save(employee);
		return new ApiResponseObject<Employee>("200", "Saved successfully!!!", employee);
	}

}
