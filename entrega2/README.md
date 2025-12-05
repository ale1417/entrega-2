# 🧾 Proyecto Final – Sistema de Facturación

**Autor:** Alejandro Pourcel  
**Tecnologías:** Java 21 · Spring Boot 3.5.7 · MySQL 8 · Maven · JPA · Hibernate

---

## 📌 Descripción del Proyecto

Este proyecto implementa un sistema de facturación completo como Trabajo Práctico Final de la carrera **Java** de CoderHouse.

El sistema permite:

- Registrar clientes
- Registrar productos con precio y stock
- Crear comprobantes de venta (facturas)
- Descontar stock automáticamente
- Validar existencia de cliente/producto
- Validar stock
- Mantener **precio histórico por línea**
- Obtener la fecha de la venta desde un **servicio REST externo** (con fallback)
- Consultar ventas y sus detalles

Todo el proyecto cumple estrictamente la consigna del Proyecto Final.

---

## 🏗 Arquitectura

El proyecto utiliza una arquitectura clásica de 3 capas:

- **Controllers** → reciben las solicitudes HTTP
- **Services** → contienen la lógica del negocio
- **Repositories** → manejan el acceso a la base de datos mediante Spring Data JPA

---

## 🗂 Estructura del Proyecto

```
src/
 └── main/
     ├── java/com/coderhouse/
     │    ├── controllers/
     │    ├── models/
     │    ├── repositories/
     │    ├── responses/
     │    └── services/
     └── resources/
          ├── application.properties
          └── static/

script.sql
pom.xml
```

---

## 🛢 Base de Datos

El archivo `script.sql` incluido en el proyecto crea las tablas necesarias:

- clientes
- productos
- ventas
- lineas_venta

---

## 🚀 Cómo Ejecutar el Proyecto

### ▶ Opción 1 — Ejecutar con Maven

```bash
mvn spring-boot:run
```

---

### ▶ Opción 2 — Ejecutar el JAR

Generar el JAR:

Ejecutarlo:

```bash
java -jar FacturacionEntregaProyectoFinal-Pourcel.jar
```

Servidor disponible en:

```
http://localhost:8080
```

---

# 📮 Endpoints Principales

---

## 👥 **Clientes**

### ➤ Crear cliente

`POST /api/clientes/create`

```json
{
  "nombre": "Juan Perez",
  "email": "juan.perez@example.com",
  "dni": "12345678"
}
```

### ➤ Listar clientes

`GET /api/clientes`

### ➤ Obtener cliente por ID

`GET /api/clientes/{id}`

---

## 📦 **Productos**

### ➤ Crear producto

`POST /api/productos/create`

```json
{
  "nombre": "Coca Cola 1.5L",
  "precio": 1500.0,
  "stock": 10
}
```

### ➤ Listar productos

`GET /api/productos`

### ➤ Obtener producto

`GET /api/productos/{id}`

---

## 🧾 **Ventas (Comprobantes)**

### ➤ Crear comprobante de venta

`POST /api/ventas/crear`

```json
{
  "cliente": { "clienteId": 1 },
  "lineas": [
    {
      "cantidad": 2,
      "producto": { "productoId": 1 }
    },
    {
      "cantidad": 1,
      "producto": { "productoId": 2 }
    }
  ]
}
```

### ➤ Obtener todas las ventas

`GET /api/ventas`

### ➤ Obtener una venta con detalles

`GET /api/ventas/{id}`

---

## 🧪 Colección de Postman

El repositorio incluye un archivo `.json` con la colección de Postman para probar:

- Creación de clientes
- Creación de productos
- Creación de ventas
- Validaciones de error
- Consultas de ventas y productos

Se importa desde Postman → **Import** → **File**.

---

## ✔ Validaciones Implementadas

- Cliente no existente
- Producto no existente
- Stock insuficiente
- Cálculo de precio unitario histórico
- Servicio de fecha externo con fallback
- Control de excepciones con `ErrorResponse`

---
