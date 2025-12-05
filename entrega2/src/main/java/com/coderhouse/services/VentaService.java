package com.coderhouse.services;

import com.coderhouse.dto.CrearVentaDTO;
import com.coderhouse.dto.VentaResponseDTO;
import com.coderhouse.models.Cliente;
import com.coderhouse.models.LineaVenta;
import com.coderhouse.models.Producto;
import com.coderhouse.models.Venta;
import com.coderhouse.repositories.ClienteRepository;
import com.coderhouse.repositories.ProductoRepository;
import com.coderhouse.repositories.VentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.*;

@Service
public class VentaService {

    @Autowired
    private VentaRepository ventaRepo;

    @Autowired
    private ClienteRepository clienteRepo;

    @Autowired
    private ProductoRepository productoRepo;

    public List<Venta> findAll() {
        return ventaRepo.findAll();
    }

    public Venta findById(Long id) {
        return ventaRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Venta no encontrada"));
    }

    public void deleteById(Long id) {
        if (!ventaRepo.existsById(id)) {
            throw new IllegalArgumentException("Venta no encontrada");
        }
        ventaRepo.deleteById(id);
    }

    // ===== LÓGICA DEL PROYECTO FINAL =====

    public VentaResponseDTO crearVenta(CrearVentaDTO dto) {
        if (dto.getCliente() == null || dto.getCliente().getClienteId() == null) {
            throw new IllegalArgumentException("Debe indicar el clienteId");
        }
        if (dto.getLineas() == null || dto.getLineas().isEmpty()) {
            throw new IllegalStateException("La venta debe incluir al menos una línea");
        }

        // 1) Cliente existente
        Long clienteId = dto.getCliente().getClienteId();
        Cliente cliente = clienteRepo.findById(clienteId)
                .orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado con id " + clienteId));

        // 2) Crear venta vacía
        Venta venta = new Venta();
        venta.setCliente(cliente);
        venta.setFecha(obtenerFechaDesdeApi()); // Fecha desde API (o Date local)
        venta.setLineas(new ArrayList<>());

        BigDecimal total = BigDecimal.ZERO;
        int cantidadTotal = 0;

        // 3) Procesar cada línea
        for (CrearVentaDTO.LineaDTO lineaDTO : dto.getLineas()) {

            if (lineaDTO.getProducto() == null || lineaDTO.getProducto().getProductoId() == null) {
                throw new IllegalArgumentException("Cada línea debe tener un productoId");
            }
            if (lineaDTO.getCantidad() == null || lineaDTO.getCantidad() <= 0) {
                throw new IllegalStateException("La cantidad debe ser mayor a cero");
            }

            Long productoId = lineaDTO.getProducto().getProductoId();
            Producto producto = productoRepo.findById(productoId)
                    .orElseThrow(() -> new IllegalArgumentException("Producto no encontrado con id " + productoId));

            int cantidad = lineaDTO.getCantidad();

            // 4) Validación de stock
            if (producto.getStock() == null || producto.getStock() < cantidad) {
                throw new IllegalStateException("Stock insuficiente para el producto con id " + productoId);
            }

            // 5) Reducir stock
            producto.setStock(producto.getStock() - cantidad);
            productoRepo.save(producto);

            // 6) Crear línea con precio unitario histórico
            LineaVenta linea = new LineaVenta();
            linea.setVenta(venta);
            linea.setProducto(producto);
            linea.setCantidad(cantidad);
            linea.setPrecioUnitario(producto.getPrecio());

            venta.getLineas().add(linea);

            BigDecimal subtotal = producto.getPrecio().multiply(BigDecimal.valueOf(cantidad));
            total = total.add(subtotal);
            cantidadTotal += cantidad;
        }

        venta.setTotal(total);
        venta.setCantidadTotalProductos(cantidadTotal);

        Venta guardada = ventaRepo.save(venta);

        return mapearVentaResponse(guardada);
    }

    private Date obtenerFechaDesdeApi() {
        try {
            RestTemplate restTemplate = new RestTemplate();
            Map<?, ?> response = restTemplate.getForObject(
                    "http://worldclockapi.com/api/json/utc/now", Map.class);

            if (response != null && response.get("currentDateTime") != null) {
                String currentDateTime = response.get("currentDateTime").toString();
                try {
                    java.time.Instant instant = java.time.Instant.parse(currentDateTime);
                    return Date.from(instant);
                } catch (Exception e) {
                    // si falla el parseo, uso Date local
                }
            }
        } catch (Exception e) {
            // si falla la llamada, uso Date local
        }
        return new Date();
    }

    private VentaResponseDTO mapearVentaResponse(Venta venta) {
        VentaResponseDTO dto = new VentaResponseDTO();
        dto.setId(venta.getId());
        dto.setFecha(venta.getFecha());
        dto.setTotal(venta.getTotal());
        dto.setCantidadTotalProductos(venta.getCantidadTotalProductos());
        dto.setClienteId(venta.getCliente().getId());

        List<VentaResponseDTO.LineaDetalleDTO> lineasDTO = new ArrayList<>();
        for (LineaVenta linea : venta.getLineas()) {
            VentaResponseDTO.LineaDetalleDTO l = new VentaResponseDTO.LineaDetalleDTO();
            l.setProductoId(linea.getProducto().getId());
            l.setProductoNombre(linea.getProducto().getNombre());
            l.setPrecioUnitario(linea.getPrecioUnitario());
            l.setCantidad(linea.getCantidad());
            BigDecimal subtotal = linea.getPrecioUnitario()
                    .multiply(BigDecimal.valueOf(linea.getCantidad()));
            l.setSubtotal(subtotal);
            lineasDTO.add(l);
        }
        dto.setLineas(lineasDTO);

        return dto;
    }
}
