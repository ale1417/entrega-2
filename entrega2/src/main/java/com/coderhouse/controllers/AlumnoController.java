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

import com.coderhouse.dto.InscripcionAlumnoDTO;
import com.coderhouse.models.Cliente;
import com.coderhouse.responses.ErrorResponse;
import com.coderhouse.services.AlumnoService;

@RestController
@RequestMapping("/api/clientes")
public class AlumnoController {

	@Autowired
	private AlumnoService alumnoService;

	@GetMapping
	public ResponseEntity<List<Cliente>> getAllAlumnos() {
		try {
			List<Cliente> clientes = alumnoService.findAll();
			return ResponseEntity.ok(clientes); // Code 200
		} catch (Exception e) {
			return ResponseEntity.internalServerError().build(); // Error 500
		}
	}

	@GetMapping("/{alumnoId}")
	public ResponseEntity<Cliente> getAlumnoById(@PathVariable Long alumnoId) {
		try {
			Cliente cliente = alumnoService.findById(alumnoId);
			return ResponseEntity.ok(cliente); // 200
		} catch (IllegalArgumentException e) {
			return ResponseEntity.notFound().build(); // 404
		} catch (Exception e) {
			return ResponseEntity.internalServerError().build(); // Error 500
		}
	}

	@PostMapping("/create")
	public ResponseEntity<?> createAlumno(@RequestBody Cliente cliente) {
		try {
			Cliente alumnoCreado = alumnoService.save(cliente);
			return ResponseEntity.status(HttpStatus.CREATED).body(alumnoCreado); // 201
		} catch (IllegalStateException e) {
			ErrorResponse error = new ErrorResponse("Conflicto", e.getMessage());
			return ResponseEntity.status(HttpStatus.CONFLICT).body(error);	// 409	
		} catch (Exception e) {
			return ResponseEntity.internalServerError().build(); // Error 500
		}
	}

	@PutMapping("/{alumnoId}")
	public ResponseEntity<Cliente> updateAlumnoById(@PathVariable Long alumnoId, @RequestBody Cliente alumnoActualizado) {
		try {
			Cliente cliente = alumnoService.update(alumnoId, alumnoActualizado);
			return ResponseEntity.ok(cliente); // 200
		} catch (IllegalArgumentException e) {
			return ResponseEntity.notFound().build(); // 404
		} catch (Exception e) {
			return ResponseEntity.internalServerError().build(); // Error 500
		}
	}

	@DeleteMapping("/{alumnoId}")
	public ResponseEntity<Void> deleteAlumnoById(@PathVariable Long alumnoId) {
		try {
			alumnoService.deleteById(alumnoId);
			return ResponseEntity.noContent().build(); // 204
		} catch (IllegalArgumentException e) {
			return ResponseEntity.notFound().build(); // 404
		} catch (Exception e) {
			return ResponseEntity.internalServerError().build(); // Error 500
		}
	}

	@PostMapping("/inscribir")
	public ResponseEntity<?> inscribirAlumnoACursos(@RequestBody InscripcionAlumnoDTO dto) {
		try {
			Cliente cliente = alumnoService.inscribirAlumnoACursos(dto);
			return ResponseEntity.ok(cliente);
		} catch (IllegalStateException e) {
			ErrorResponse error = new ErrorResponse("Conflicto", e.getMessage());
			return ResponseEntity.status(HttpStatus.CONFLICT).body(error);	// 409
		} catch (IllegalArgumentException e) {
			ErrorResponse error = new ErrorResponse("Error 404", e.getMessage());
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);	// Error 404
		} catch (Exception e) {
			return ResponseEntity.internalServerError().build(); // Error 500
		}
	}

}
