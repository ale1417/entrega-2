package com.coderhouse.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.coderhouse.models.Venta;

public interface CategoriaRepository extends JpaRepository<Venta, Long> {

	boolean existsByNombre(String nombre);
}
