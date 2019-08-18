package com.greenmile.desafio2.repository;

import org.springframework.data.repository.CrudRepository;

import com.greenmile.desafio2.entities.Project;

public interface ProjectRepository extends CrudRepository<Project, Long> {

}
