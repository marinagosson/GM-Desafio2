package com.greenmile.desafio2.repository;

import org.springframework.data.repository.CrudRepository;

import com.greenmile.desafio2.entities.Team;

public interface TeamRepository extends CrudRepository<Team, Long> {

}