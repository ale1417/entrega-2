package com.coderhouse.models;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "lineas_venta")
public class LineaVenta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Venta a la que pertenece esta línea
    @ManyToOne(optional = false)
    @JoinColumn(name = "venta_id")
    private Venta venta;

    // Producto vendido en esta línea
    @ManyToOne(optional = false)
    @JoinColumn(name = "producto_id")
    private Producto producto;

    @Column(nullable = false)
    private Integer cantidad;

    // Precio unitario HISTÓRICO (no se modifica si cambia el producto)
    @Column(name = "precio_unitario", nullable = false)
    private BigDecimal precioUnitario;

    // ===== Getters & Setters =====

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Venta getVenta() {
        return venta;
    }

    public void setVenta(Venta venta) {
        this.venta = venta;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public BigDecimal getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(BigDecimal precioUnitario) {
        this.precioUnitario = precioUnitario;
    }
}
