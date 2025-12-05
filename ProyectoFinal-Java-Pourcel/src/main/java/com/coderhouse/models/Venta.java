package com.coderhouse.models;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "ventas")
public class Venta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Cliente del comprobante
    @ManyToOne(optional = false)
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    // Líneas del comprobante
    @OneToMany(mappedBy = "venta", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LineaVenta> lineas = new ArrayList<>();

    // Fecha del comprobante (API externa o Date local)
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date fecha;

    // Precio total de la venta
    @Column(nullable = false)
    private BigDecimal total;

    // Cantidad total de productos vendidos
    @Column(name = "cantidad_total_productos", nullable = false)
    private Integer cantidadTotalProductos;

    // ===== Getters & Setters =====

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public List<LineaVenta> getLineas() {
        return lineas;
    }

    public void setLineas(List<LineaVenta> lineas) {
        this.lineas = lineas;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public Integer getCantidadTotalProductos() {
        return cantidadTotalProductos;
    }

    public void setCantidadTotalProductos(Integer cantidadTotalProductos) {
        this.cantidadTotalProductos = cantidadTotalProductos;
    }
}
