package com.coderhouse.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.coderhouse.models.Cliente;

public interface AlumnoRepository extends JpaRepository<Cliente, Long> {

	boolean existsByDni(int dni);
	
	boolean existsByLegajo(String legajo);
}
