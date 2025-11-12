package com.coderhouse.controllers;

import com.coderhouse.dto.CrearVentaDTO;
import com.coderhouse.models.Venta;
import com.coderhouse.responses.ErrorResponse;
import com.coderhouse.services.VentaService;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

@RestController
@RequestMapping("/api/ventas")
public class VentaController {
    @Autowired private VentaService service;
    @GetMapping public ResponseEntity<List<Venta>> listar(){ return ResponseEntity.ok(service.findAll()); }
    @GetMapping("/{id}") public ResponseEntity<?> obtener(@PathVariable Long id){
        try{ return ResponseEntity.ok(service.findById(id)); }
        catch(IllegalArgumentException e){ return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("No encontrado","Venta no encontrada")); }
    }
    @PostMapping("/crear") public ResponseEntity<?> crear(@RequestBody CrearVentaDTO dto){
        try{ return ResponseEntity.status(HttpStatus.CREATED).body(service.crearVenta(dto)); }
        catch(IllegalArgumentException e){ return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("No encontrado", e.getMessage())); }
        catch(IllegalStateException e){ return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorResponse("Conflicto", e.getMessage())); }
        catch(Exception e){ return ResponseEntity.internalServerError().body(new ErrorResponse("Error","No se pudo crear la venta")); }
    }
}
