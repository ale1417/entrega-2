package com.coderhouse.dto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class VentaResponseDTO {

    private Long id;
    private Date fecha;
    private BigDecimal total;
    private Integer cantidadTotalProductos;
    private Long clienteId;
    private List<LineaDetalleDTO> lineas;

    public static class LineaDetalleDTO {
        private Long productoId;
        private String productoNombre;
        private BigDecimal precioUnitario;
        private Integer cantidad;
        private BigDecimal subtotal;

        public Long getProductoId() {
            return productoId;
        }

        public void setProductoId(Long productoId) {
            this.productoId = productoId;
        }

        public String getProductoNombre() {
            return productoNombre;
        }

        public void setProductoNombre(String productoNombre) {
            this.productoNombre = productoNombre;
        }

        public BigDecimal getPrecioUnitario() {
            return precioUnitario;
        }

        public void setPrecioUnitario(BigDecimal precioUnitario) {
            this.precioUnitario = precioUnitario;
        }

        public Integer getCantidad() {
            return cantidad;
        }

        public void setCantidad(Integer cantidad) {
            this.cantidad = cantidad;
        }

        public BigDecimal getSubtotal() {
            return subtotal;
        }

        public void setSubtotal(BigDecimal subtotal) {
            this.subtotal = subtotal;
        }
    }

    // ===== Getters & Setters =====

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Long getClienteId() {
        return clienteId;
    }

    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
    }

    public List<LineaDetalleDTO> getLineas() {
        return lineas;
    }

    public void setLineas(List<LineaDetalleDTO> lineas) {
        this.lineas = lineas;
    }
}
