package com.coderhouse.services;

import com.coderhouse.dto.CrearVentaDTO;
import com.coderhouse.models.Cliente;
import com.coderhouse.models.Producto;
import com.coderhouse.models.Venta;
import com.coderhouse.repositories.ClienteRepository;
import com.coderhouse.repositories.ProductoRepository;
import com.coderhouse.repositories.VentaRepository;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

@Service
public class VentaService {
    @Autowired private VentaRepository ventaRepo;
    @Autowired private ClienteRepository clienteRepo;
    @Autowired private ProductoRepository productoRepo;

    public List<Venta> findAll(){ return ventaRepo.findAll(); }
    public Venta findById(Long id){ return ventaRepo.findById(id).orElseThrow(()-> new IllegalArgumentException("Venta no encontrada")); }

    public Venta crearVenta(CrearVentaDTO dto){
        Cliente cliente = clienteRepo.findById(dto.getClienteId()).orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado"));
        java.util.List<Producto> productos = productoRepo.findAllById(dto.getProductosIds());
        if(productos.isEmpty()) throw new IllegalStateException("La venta debe incluir al menos un producto");
        Venta v = new Venta();
        v.setCliente(cliente);
        v.setProductos(productos);
        return ventaRepo.save(v);
    }

    public void deleteById(Long id){ if(!ventaRepo.existsById(id)) throw new IllegalArgumentException("Venta no encontrada"); ventaRepo.deleteById(id); }
}
