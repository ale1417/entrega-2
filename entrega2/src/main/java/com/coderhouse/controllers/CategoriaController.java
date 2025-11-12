package com.coderhouse.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.coderhouse.models.Venta;
import com.coderhouse.responses.ErrorResponse;
import com.coderhouse.services.CategoriaService;

@RestController
@RequestMapping("/api/ventas")
public class CategoriaController {

	@Autowired
	private CategoriaService svc;
	
	@GetMapping
	public ResponseEntity<List<Venta>> getAllCategorias(){
		try {
			List<Venta> ventas = svc.findAll();
			return ResponseEntity.ok(ventas); // Code 200
		} catch (Exception e) {
			return ResponseEntity.internalServerError().build(); // Error 500
		}
	}
	
	@GetMapping("/{categoriaId}")
	public ResponseEntity<Venta> getCategoriaById(@PathVariable Long categoriaId){
		try {
			Venta venta = svc.findById(categoriaId);
			return ResponseEntity.ok(venta); // 200
		} catch (IllegalArgumentException e) {
			return ResponseEntity.notFound().build(); // 404			
		} catch (Exception e) {
			return ResponseEntity.internalServerError().build(); // Error 500
		}
	}
	
	@PostMapping("/create")
	public ResponseEntity<?> createCategoria(@RequestBody Venta venta) {
		try {
			Venta categoriaCreada = svc.save(venta);
			return ResponseEntity.status(HttpStatus.CREATED).body(categoriaCreada); // 201
		} catch (IllegalStateException e) {
			ErrorResponse error = new ErrorResponse("Conflicto", e.getMessage());
			return ResponseEntity.status(HttpStatus.CONFLICT).body(error);	// 409
		}catch (Exception e) {
			return ResponseEntity.internalServerError().build(); // Error 500
		}
	}
	
	
	@PutMapping("/{categoriaId}")
	public ResponseEntity<Venta> updateCategoriaById(
			@PathVariable Long categoriaId, @RequestBody Venta categoriaActualizada){
		try {
			Venta venta = svc.update(categoriaId, categoriaActualizada);
			return ResponseEntity.ok(venta); // 200
		} catch (IllegalArgumentException e) {
			return ResponseEntity.notFound().build(); // 404			
		} catch (Exception e) {
			return ResponseEntity.internalServerError().build(); // Error 500
		}
	}
	
	@DeleteMapping("/{categoriaId}")
	public ResponseEntity<Void> deleteCategoriaById(@PathVariable Long categoriaId){
		try {
			svc.deleteById(categoriaId);
			return ResponseEntity.noContent().build(); // 204
		} catch (IllegalArgumentException e) {
			return ResponseEntity.notFound().build(); // 404			
		} catch (Exception e) {
			return ResponseEntity.internalServerError().build(); // Error 500
		}
	}
}
