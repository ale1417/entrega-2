package com.coderhouse.services;

import com.coderhouse.interfaces.CRUDInterface;
import com.coderhouse.models.Producto;
import com.coderhouse.repositories.ProductoRepository;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service
public class ProductoService implements CRUDInterface<Producto, Long> {

    @Autowired
    private ProductoRepository repo;

    public List<Producto> findAll() {
        return repo.findAll();
    }

    public Producto findById(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado"));
    }

    public Producto save(Producto p) {
        return repo.save(p);
    }

    public Producto update(Long id, Producto p) {
        Producto existente = findById(id);
        existente.setNombre(p.getNombre());
        existente.setPrecio(p.getPrecio());
        existente.setStock(p.getStock());
        return repo.save(existente);
    }

    public void deleteById(Long id) {
        if (!repo.existsById(id)) {
            throw new IllegalArgumentException("Producto no encontrado");
        }
        repo.deleteById(id);
    }
}
