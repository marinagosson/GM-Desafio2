package com.greenmile.desafio2.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.greenmile.desafio2.ApiResponseObject;
import com.greenmile.desafio2.entities.Project;
import com.greenmile.desafio2.entities.Skill;
import com.greenmile.desafio2.entities.Team;
import com.greenmile.desafio2.repository.SkillRepository;

@Controller
public class SkillController {

	@Autowired
	private SkillRepository sr;

	@ResponseBody
	@GetMapping("/skills")
	public List<Skill> projects() {
		List<Skill> skills = new ArrayList<>();
		for(Skill project: sr.findAll()) {
			skills.add(project);
		}
		return skills;
	}
	
	@ResponseBody
	@PostMapping("/skill")
	public ApiResponseObject<Skill> team(@RequestBody Skill skill) {
		if (skill.getName() == null || skill.getName().isEmpty())
			return new ApiResponseObject<Skill>("400", "Name cannot be null or empty", null);
		Skill s = sr.save(skill);
		return new ApiResponseObject<Skill>("200", "Saved successfully", s);
	}
	
}
