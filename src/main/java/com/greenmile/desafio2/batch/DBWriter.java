package com.greenmile.desafio2.batch;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.greenmile.desafio2.ApiResponseObject;
import com.greenmile.desafio2.Utils;
import com.greenmile.desafio2.entities.CSV;
import com.greenmile.desafio2.entities.Employee;
import com.greenmile.desafio2.entities.Project;
import com.greenmile.desafio2.entities.Skill;
import com.greenmile.desafio2.entities.Team;
import com.greenmile.desafio2.repository.EmployeeRepository;
import com.greenmile.desafio2.repository.ProjectRepository;
import com.greenmile.desafio2.repository.SkillRepository;
import com.greenmile.desafio2.repository.TeamRepository;

@Component
public class DBWriter implements ItemWriter<CSV> {

	@Autowired
	EmployeeRepository er;

	@Autowired
	ProjectRepository pr;

	@Autowired
	SkillRepository sr;

	@Autowired
	TeamRepository tr;

	@Override
	public void write(List<? extends CSV> items) throws Exception {

		List<Project> projects = new ArrayList<Project>();
		List<Employee> employees = new ArrayList<Employee>();
		List<Skill> skills = new ArrayList<Skill>();
		List<Team> teams = new ArrayList<Team>();

		for (CSV csv : items) {

			Employee e = new Employee();
			e.setName(csv.getEmployeeName());
			e.setEmail(csv.getEmployeeEmail());

			Project p = new Project();
			p.setProjectName(csv.getProjectName());
			p.setPlannedStart(csv.getPlannedStart());
			p.setPlannedEnd(csv.getPlannedEnd());

			String[] arraySkillsEmployee = csv.getEmployeeSkills().split(",");
			for (String name : arraySkillsEmployee) {
				Skill s = new Skill();
				s.setName(name);
				skills.add(s);
			}

			String[] arraySkillsPM = csv.getPMSkills().split(",");
			for (String name : arraySkillsPM) {
				Skill s = new Skill();
				s.setName(name);
				skills.add(s);
			}

			Team t = new Team();
			t.setName(csv.getEmployeeTeam());

			Employee pm = new Employee();
			pm.setName(csv.getPM());
			pm.setEmail(csv.getPMEmail());

			employees.add(pm);
			teams.add(t);
			employees.add(e);
			projects.add(p);
		}

		// salvando os employees
		List<Employee> employeesForSave = new ArrayList<Employee>();
		employees = employees.stream().filter(Utils.distinctByKey(p -> p.getEmail())).collect(Collectors.toList());
		Iterable<Employee> eIterable = er.findAll();
		if (eIterable.iterator().hasNext()) {
			for (Employee e : eIterable) {
				for (Employee employee : employees) {
					if (!e.getEmail().equals(employee.getEmail())) {
						employeesForSave.add(e);
					} else
						break;
				}
			}
			if (employeesForSave != null)
				eIterable = er.saveAll(employeesForSave);
		} else {
			eIterable = er.saveAll(employees);
		}

		// salvando os projetos
		List<Project> projectForSave = new ArrayList<Project>();
		projects = projects.stream().filter(Utils.distinctByKey(p -> p.getProjectName())).collect(Collectors.toList());
		Iterable<Project> pIterable = pr.findAll();
		if (pIterable.iterator().hasNext()) {
			for (Project p : pIterable) {
				for (Project project : projects) {
					if (!p.getProjectName().equals(project.getProjectName())) {
						projectForSave.add(p);
					}
				}
			}
			if (projectForSave != null)
				pIterable = pr.saveAll(projectForSave);
		} else {
			pIterable = pr.saveAll(projects);
		}

		// salvando skills
		List<Skill> skillsForSave = new ArrayList<Skill>();
		skills = skills.stream().filter(Utils.distinctByKey(p -> p.getName())).collect(Collectors.toList());
		Iterable<Skill> sIterable = sr.findAll();
		if (sIterable.iterator().hasNext()) {
			for (Skill s : sIterable) {
				for (Skill skill : skills) {
					if (!s.getName().equals(skill.getName())) {
						skillsForSave.add(s);
					}
				}
			}

			if (skillsForSave != null)
				sIterable = sr.saveAll(skillsForSave);
		} else {
			sIterable = sr.saveAll(skills);
		}

//		salvando teams
		List<Team> teamsForSave = new ArrayList<Team>();
		teams = teams.stream().filter(Utils.distinctByKey(p -> p.getName())).collect(Collectors.toList());
		Iterable<Team> tIterable = tr.findAll();
		if (tIterable.iterator().hasNext()) {
			for (Team t : tIterable) {
				for (Team team : teams) {
					if (!t.getName().equals(team.getName())) {
						teamsForSave.add(t);
					}
				}
			}

			if (teamsForSave != null)
				tIterable = tr.saveAll(teamsForSave);
		} else {
			tIterable = tr.saveAll(teams);
		}

		for (CSV csv : items) {

			for (Project p : pr.findAll()) {
				if (p.getProjectName().equals(csv.getProjectName())) {
					
					for (Employee e : er.findAll()) {

						if (e.getEmail().equals(csv.getPMEmail())) {
							
							// PM
							
							List<Skill> skillsByPm = new ArrayList<Skill>();
							String[] arraySkillsPM = csv.getPMSkills().split(",");
							
							for (Skill s : sr.findAll()) {

								for (String string : arraySkillsPM) {

									if (s.getName().equals(string)) {
										skillsByPm.add(s);
									}
								}
								e.setSkills(skillsByPm);
								p.setPm(e);
							
							}

							pr.save(p);
						} else if(e.getEmail().equals(csv.getEmployeeEmail())){
							
							// employee
							
							List<Skill> skillsByEmployee = new ArrayList<Skill>();
							String[] arraySkillsEmployees = csv.getPMSkills().split(",");
							
							for (Skill s : sr.findAll()) {
								
								for (String string : arraySkillsEmployees) {

									if (s.getName().equals(string)) {
										skillsByEmployee.add(s);
									}
								}
								e.setSkills(skillsByEmployee);
								er.save(e);	
							}
							p.getEmployees().add(e);
							
							for(Team t : tr.findAll()) {
								if(t.getName().equals(csv.getEmployeeTeam())) {
									e.setTeam(t);
									er.save(e);
								}
							}
							
						}

					}
					
				}
				pr.save(p);
			}
		}
	}

}
