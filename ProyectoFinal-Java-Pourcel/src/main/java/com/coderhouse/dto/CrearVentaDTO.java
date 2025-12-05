package com.coderhouse.dto;

import java.util.List;

public class CrearVentaDTO {

    private ClienteDTO cliente;
    private List<LineaDTO> lineas;

    // ===== DTOs internos =====

    public static class ClienteDTO {
        private Long clienteId;

        public Long getClienteId() {
            return clienteId;
        }

        public void setClienteId(Long clienteId) {
            this.clienteId = clienteId;
        }
    }

    public static class ProductoDTO {
        private Long productoId;

        public Long getProductoId() {
            return productoId;
        }

        public void setProductoId(Long productoId) {
            this.productoId = productoId;
        }
    }

    public static class LineaDTO {
        private Integer cantidad;
        private ProductoDTO producto;

        public Integer getCantidad() {
            return cantidad;
        }

        public void setCantidad(Integer cantidad) {
            this.cantidad = cantidad;
        }

        public ProductoDTO getProducto() {
            return producto;
        }

        public void setProducto(ProductoDTO producto) {
            this.producto = producto;
        }
    }

    // ===== Getters & Setters top-level =====

    public ClienteDTO getCliente() {
        return cliente;
    }

    public void setCliente(ClienteDTO cliente) {
        this.cliente = cliente;
    }

    public List<LineaDTO> getLineas() {
        return lineas;
    }

    public void setLineas(List<LineaDTO> lineas) {
        this.lineas = lineas;
    }
}
