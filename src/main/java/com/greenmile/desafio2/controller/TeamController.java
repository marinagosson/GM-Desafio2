package com.greenmile.desafio2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.greenmile.desafio2.ApiResponseObject;
import com.greenmile.desafio2.entities.Employee;
import com.greenmile.desafio2.entities.Team;
import com.greenmile.desafio2.repository.TeamRepository;

@Controller
public class TeamController {

	@Autowired
	private TeamRepository tr;
	
	@ResponseBody
	@GetMapping("/teams")
	public Iterable<Team> teams() {
		return tr.findAll();
	}
	
	@ResponseBody
	@PostMapping("/team")
	public ApiResponseObject<Team> team(@RequestBody Team team) {
		if (team.getName() == null || team.getName().isEmpty())
			return new ApiResponseObject<Team>("400", "Name cannot be null or empty", null);
		Team t = tr.save(team);
		return new ApiResponseObject<Team>("200", "Saved successfully", t);
	}
	
}

