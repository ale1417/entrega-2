package com.coderhouse.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coderhouse.dto.InscripcionAlumnoDTO;
import com.coderhouse.interfaces.CRUDInterface;
import com.coderhouse.models.Cliente;
import com.coderhouse.models.Producto;
import com.coderhouse.repositories.AlumnoRepository;
import com.coderhouse.repositories.CursoRepository;

import jakarta.transaction.Transactional;

@Service
public class AlumnoService implements CRUDInterface<Cliente, Long> {
	
	private final String message = "Cliente no encontrado";
	
	@Autowired
	private AlumnoRepository repo;
	@Autowired
	private CursoRepository cursoRepo;
	
	@Override
	public List<Cliente> findAll() {
		return repo.findAll();
	}

	@Override
	public Cliente findById(Long id) {
		return repo.findById(id)
				.orElseThrow(() -> new IllegalArgumentException(message));
	}

	@Override
	@Transactional
	public Cliente save(Cliente nuevoAlumno) {
		if(nuevoAlumno.getDni() != 0 && repo.existsByDni(nuevoAlumno.getDni())) {
			throw new IllegalStateException("Este DNI ya existe");
		}
		
		if(nuevoAlumno.getLegajo() != null && !nuevoAlumno.getLegajo().isEmpty()
				&& repo.existsByLegajo(nuevoAlumno.getLegajo())) {
			throw new IllegalStateException("Este Legajo ya existe");
		}
		return repo.save(nuevoAlumno);
	}

	@Override
	@Transactional
	public Cliente update(Long id, Cliente alumnoActualizado) {
		Cliente cliente = findById(id);
		
		if(alumnoActualizado.getNombre() != null && !alumnoActualizado.getNombre().isEmpty()) {
			cliente.setNombre(alumnoActualizado.getNombre());
		}
		
		if(alumnoActualizado.getApellido() != null && !alumnoActualizado.getApellido().isEmpty()) {
			cliente.setApellido(alumnoActualizado.getApellido());
		}
		
		if(alumnoActualizado.getDni() != 0) {
			cliente.setDni(alumnoActualizado.getDni());
		}
		
		if(alumnoActualizado.getEdad() != 0) {
			cliente.setEdad(alumnoActualizado.getEdad());
		}
		
		if(alumnoActualizado.getLegajo() != null && !alumnoActualizado.getLegajo().isEmpty()) {
			cliente.setLegajo(alumnoActualizado.getLegajo());
		}
				
		return repo.save(cliente);
	}

	@Override
	public void deleteById(Long id) {
		if(!repo.existsById(id)) {
			throw new IllegalArgumentException(message);
		}
		repo.deleteById(id);
	}

	
	@Transactional
	public Cliente inscribirAlumnoACursos(InscripcionAlumnoDTO dto) {
		Cliente cliente = findById(dto.getAlumnoId());
		
		for(Long cursoId : dto.getCursoIds()) {
			
			Producto producto = cursoRepo.findById(cursoId)
					.orElseThrow(() -> new IllegalArgumentException("El Producto con ID " + cursoId + ", no existe"));
			
			// Verificar si el Cliente esta inscripto a este Producto
			if(cliente.getCursos().contains(producto)) {
				throw new IllegalStateException("El Cliente ya esta inscripto a este Producto con ID: " + cursoId);
			}
			
			cliente.getCursos().add(producto);
			producto.getAlumnos().add(cliente);
			
			cursoRepo.save(producto);
		}
			
		
		return repo.save(cliente);
	}
}
