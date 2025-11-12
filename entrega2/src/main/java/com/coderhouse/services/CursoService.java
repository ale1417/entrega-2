package com.coderhouse.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coderhouse.interfaces.CRUDInterface;
import com.coderhouse.models.Venta;
import com.coderhouse.models.Producto;
import com.coderhouse.repositories.CategoriaRepository;
import com.coderhouse.repositories.CursoRepository;

import jakarta.transaction.Transactional;

@Service
public class CursoService implements CRUDInterface<Producto, Long> {

	private final String message = "Producto no encontrado";
	private final String messageCat = "Venta no encontrado";
	@Autowired
	private CursoRepository repo;
	
	@Autowired
	private CategoriaRepository repoCategoria;
	
	@Override
	public List<Producto> findAll() {
		return repo.findAll();
	}

	@Override
	public Producto findById(Long id) {
		return repo.findById(id)
				.orElseThrow(() -> new IllegalArgumentException(message));
	}

	@Override
	@Transactional
	public Producto save(Producto cursoNuevo) {
		if(cursoNuevo.getNombre() != null && repo.existsByNombre(cursoNuevo.getNombre())) {
			throw new IllegalStateException("El producto con este Nombre ya existe");
		}
		return repo.save(cursoNuevo);
	}

	@Override
	@Transactional
	public Producto update(Long id, Producto cursoActualizado) {
		Producto producto = findById(id);
		
		if(cursoActualizado.getNombre() != null && !cursoActualizado.getNombre().isEmpty()) {
			producto.setNombre(cursoActualizado.getNombre());
		}
		
		return repo.save(producto);		
	}

	@Override
	public void deleteById(Long id) {
		if(!repo.existsById(id)) {
			throw new IllegalArgumentException(message);
		}
		repo.deleteById(id);
	}
	
	
	@Transactional
	public Producto asignarCategoriaAUnCurso(Long cursoId, Long categoriaId) {
		
		Producto producto = repo.findById(cursoId)
				.orElseThrow(() -> new IllegalArgumentException(message));
		
		Venta venta = repoCategoria.findById(categoriaId)
				.orElseThrow(() -> new IllegalArgumentException(messageCat));
		
		if(producto.getCategoria() != null && producto.getCategoria().getId().equals(categoriaId)) {
			throw new IllegalStateException("El producto ya tiene esta venta asignada");
		}
		
		producto.setCategoria(venta);
		
		return repo.save(producto);
	}

}
