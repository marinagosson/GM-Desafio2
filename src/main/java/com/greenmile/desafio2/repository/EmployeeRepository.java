package com.greenmile.desafio2.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.greenmile.desafio2.entities.Employee;

public interface EmployeeRepository extends CrudRepository<Employee, Long> {

	boolean existsByEmail(String email);
}
