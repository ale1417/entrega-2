package com.coderhouse.controllers;

import com.coderhouse.models.Producto;
import com.coderhouse.responses.ErrorResponse;
import com.coderhouse.services.ProductoService;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {
    @Autowired private ProductoService service;
    @GetMapping public ResponseEntity<List<Producto>> listar(){ return ResponseEntity.ok(service.findAll()); }
    @GetMapping("/{id}") public ResponseEntity<?> obtener(@PathVariable Long id){
        try{ return ResponseEntity.ok(service.findById(id)); }
        catch(IllegalArgumentException e){ return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("No encontrado","Producto no encontrado")); }
    }
    @PostMapping("/create") public ResponseEntity<?> crear(@RequestBody Producto p){
        try{ return ResponseEntity.status(HttpStatus.CREATED).body(service.save(p)); }
        catch(Exception e){ return ResponseEntity.internalServerError().body(new ErrorResponse("Error","No se pudo crear el producto")); }
    }
    @PutMapping("/{id}") public ResponseEntity<?> actualizar(@PathVariable Long id, @RequestBody Producto p){
        try{ return ResponseEntity.ok(service.update(id,p)); }
        catch(IllegalArgumentException e){ return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("No encontrado","Producto no encontrado")); }
    }
    @DeleteMapping("/{id}") public ResponseEntity<?> eliminar(@PathVariable Long id){ service.deleteById(id); return ResponseEntity.noContent().build(); }
}
