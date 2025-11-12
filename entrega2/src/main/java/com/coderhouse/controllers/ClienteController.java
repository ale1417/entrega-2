package com.coderhouse.controllers;

import com.coderhouse.models.Cliente;
import com.coderhouse.responses.ErrorResponse;
import com.coderhouse.services.ClienteService;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {
    @Autowired private ClienteService service;
    @GetMapping public ResponseEntity<List<Cliente>> listar(){ return ResponseEntity.ok(service.findAll()); }
    @GetMapping("/{id}") public ResponseEntity<?> obtener(@PathVariable Long id){
        try{ return ResponseEntity.ok(service.findById(id)); }
        catch(IllegalArgumentException e){ return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("No encontrado","Cliente no encontrado")); }
    }
    @PostMapping("/create") public ResponseEntity<?> crear(@RequestBody Cliente c){
        try{ return ResponseEntity.status(HttpStatus.CREATED).body(service.save(c)); }
        catch(Exception e){ return ResponseEntity.internalServerError().body(new ErrorResponse("Error","No se pudo crear el cliente")); }
    }
    @PutMapping("/{id}") public ResponseEntity<?> actualizar(@PathVariable Long id, @RequestBody Cliente c){
        try{ return ResponseEntity.ok(service.update(id,c)); }
        catch(IllegalArgumentException e){ return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("No encontrado","Cliente no encontrado")); }
    }
    @DeleteMapping("/{id}") public ResponseEntity<?> eliminar(@PathVariable Long id){ service.deleteById(id); return ResponseEntity.noContent().build(); }
}
