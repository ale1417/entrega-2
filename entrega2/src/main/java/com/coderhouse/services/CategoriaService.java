package com.coderhouse.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coderhouse.interfaces.CRUDInterface;
import com.coderhouse.models.Venta;
import com.coderhouse.repositories.CategoriaRepository;

import jakarta.transaction.Transactional;

@Service
public class CategoriaService implements CRUDInterface<Venta, Long> {

	private final String message = "Venta no encontrada";
	
	@Autowired
	private CategoriaRepository repo;
	
	@Override
	public List<Venta> findAll() {
		return repo.findAll();
	}

	@Override
	public Venta findById(Long id) {
		return repo.findById(id)
				.orElseThrow(() -> new IllegalArgumentException(message));
	}

	@Override
	public Venta save(Venta categoriaNueva) {
		if(categoriaNueva.getNombre() != null && repo.existsByNombre(categoriaNueva.getNombre())) {
			throw new IllegalStateException("La Venta con este Nombre ya existe");
		}
		return repo.save(categoriaNueva);
	}

	@Override
	@Transactional
	public Venta update(Long id, Venta categoriaActualizada) {
		Venta venta = findById(id);
		
		if(categoriaActualizada.getNombre() != null && !categoriaActualizada.getNombre().isEmpty()) {
			venta.setNombre(categoriaActualizada.getNombre());
		}
		return repo.save(venta);
	}

	@Override
	public void deleteById(Long id) {
		if(!repo.existsById(id)) {
			throw new IllegalArgumentException(message);
		}
		repo.deleteById(id);		
	}

}
