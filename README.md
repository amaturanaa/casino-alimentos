# Casino de Alimentos — Sistema de Gestión de Microservicios

Sistema de gestión integral para una cadena de casinos de alimentos, desarrollado con arquitectura de microservicios usando Spring Boot 4. El sistema permite administrar usuarios, sucursales, menús, pedidos, pagos, inventario, proveedores, reservas y empleados de forma distribuida e independiente.

Proyecto desarrollado para la asignatura **Desarrollo Fullstack I (DSY1103)** — DuocUC.

---

## Integrantes

| Nombre | GitHub |
|---|---|
| Maximiliano Mella | [@amaturanaa](https://github.com/amaturanaa) |
| Alejandro Maturana | [@MaxiMella](https://github.com/MaxiMella) |

---

## Arquitectura

El sistema está compuesto por 12 servicios organizados bajo una arquitectura de microservicios con registro de servicios mediante **Eureka Server** y enrutamiento centralizado mediante **API Gateway** en el puerto 80.

---

## Microservicios

| Microservicio | Puerto | Base de Datos | Descripción |
|---|---|---|---|
| eureka-server | 8761 | — | Registro y descubrimiento de servicios |
| api-gateway | 8080 | — | Punto de entrada único al sistema |
| ms-usuarios | 8082 | db_usuarios | Gestión de usuarios y roles del sistema |
| ms-sucursales | 8083 | db_sucursales | Gestión de sedes del casino |
| ms-menu | 8084 | db_menu | Gestión de platos, tipos y programación diaria |
| ms-pedidos | 8085 | db_pedidos | Gestión de pedidos y sus detalles |
| ms-pagos | 8086 | db_pagos | Procesamiento de transacciones y pagos |
| ms-categorias-menu | 8087 | db_categorias_menu | Categorías y etiquetas nutricionales |
| ms-proveedores | 8088 | db_proveedores | Gestión de proveedores y órdenes de compra |
| ms-reservas | 8089 | db_reservas | Gestión de turnos y reservas de comensales |
| ms-empleados | 8090 | db_empleados | Gestión de empleados y turnos de trabajo |
| ms-inventario | 8091 | db_inventario | Control de stock e ingredientes por sede |

---

## Rutas principales del API Gateway (puerto 8080)

| Ruta | Microservicio destino |
|---|---|
| /api/usuarios/** | ms-usuarios |
| /api/roles/** | ms-usuarios |
| /api/sedes/** | ms-sucursales |
| /api/categorias/** | ms-categorias-menu |
| /api/etiquetas/** | ms-categorias-menu |
| /api/platos/** | ms-menu |
| /api/tipos-plato/** | ms-menu |
| /api/programacion/** | ms-menu |
| /api/pedidos/** | ms-pedidos |
| /api/pagos/** | ms-pagos |
| /api/ingredientes/** | ms-inventario |
| /api/movimientos/** | ms-inventario |
| /api/tipos-movimiento/** | ms-inventario |
| /api/proveedores/** | ms-proveedores |
| /api/ordenes/** | ms-proveedores |
| /api/empleados/** | ms-empleados |
| /api/turnos-empleado/** | ms-empleados |
| /api/reservas/** | ms-reservas |
| /api/turnos/** | ms-reservas |

---

## Documentación Swagger / OpenAPI

Cada microservicio expone su documentación en `/swagger-ui.html`:

| Microservicio | URL Swagger (local) |
|---|---|
| ms-usuarios | http://localhost:8082/swagger-ui.html |
| ms-sucursales | http://localhost:8083/swagger-ui.html |
| ms-menu | http://localhost:8084/swagger-ui.html |
| ms-pedidos | http://localhost:8085/swagger-ui.html |
| ms-pagos | http://localhost:8086/swagger-ui.html |
| ms-categorias-menu | http://localhost:8087/swagger-ui.html |
| ms-proveedores | http://localhost:8088/swagger-ui.html |
| ms-reservas | http://localhost:8089/swagger-ui.html |
| ms-empleados | http://localhost:8090/swagger-ui.html |
| ms-inventario | http://localhost:8091/swagger-ui.html |

---

## Instrucciones de ejecución local

### Requisitos previos

- Java 21 (Temurin)
- Maven 3.9+
- XAMPP con MySQL activo
- IntelliJ IDEA (recomendado)

### Pasos

1. **Clonar el repositorio**
   ```bash
   git clone https://github.com/amaturanaa/casino-alimentos.git
   cd casino-alimentos
   ```

2. **Iniciar MySQL** desde el Panel de Control de XAMPP.

3. **Arrancar los servicios en este orden:**
   ```
   1. eureka-server       (puerto 8761)
   2. api-gateway         (puerto 8080)
   3. ms-usuarios         (puerto 8082)
   4. ms-sucursales       (puerto 8083)
   5. ms-categorias-menu  (puerto 8087)
   6. ms-menu             (puerto 8084)
   7. ms-pedidos          (puerto 8085)
   8. ms-pagos            (puerto 8086)
   9. ms-inventario       (puerto 8091)
   10. ms-proveedores     (puerto 8088)
   11. ms-empleados       (puerto 8090)
   12. ms-reservas        (puerto 8089)
   ```

4. **Verificar el registro en Eureka:**
   Abrir http://localhost:8761 — deben aparecer los 10 microservicios registrados.

5. **Acceder al sistema via Gateway:**
   ```
   http://localhost/api/usuarios
   http://localhost/api/platos
   ```

### Perfiles de Spring

Cada microservicio usa perfiles separados:
- `dev` → conecta a la base de datos real (`db_xxx`)
- `test` → conecta a una base de datos de prueba aislada (`db_xxx_test`)

El perfil activo se controla en `application.properties`:
```properties
spring.profiles.active=dev
```

---

## Pruebas Unitarias

El proyecto incluye pruebas de integración con **JUnit 5** y `@SpringBootTest` para 10 microservicios. Cada microservicio tiene 3 pruebas que validan las reglas de negocio más críticas del dominio, ejecutadas contra una base de datos de prueba aislada.



### Ejecutar pruebas

En IntelliJ, con XAMPP/MySQL activo, clic derecho sobre la clase de prueba → **Run**. Las bases de datos `db_xxx_test` se crean automáticamente.

---

## Tecnologías Utilizadas

| Tecnología | Versión | Uso |
|---|---|---|
| Java | 21 (Temurin) | Lenguaje principal |
| Spring Boot | 4.0.6 | Framework base |
| Spring Cloud Netflix Eureka | 5.0.1 | Registro de servicios |
| Spring Cloud OpenFeign | 5.0.1 | Comunicación entre microservicios |
| Spring Cloud Gateway (Reactive) | 5.0.1 | API Gateway |
| Spring Data JPA | 4.0.5 | Persistencia de datos |
| Hibernate | 7.2.12 | ORM |
| MySQL / MariaDB | — | Base de datos |
| XAMPP | — | Servidor de base de datos local |
| Lombok | 1.18.46 | Reducción de código boilerplate |
| Bean Validation (JSR 380) | — | Validación de datos |
| SLF4J + Logback | — | Logs estructurados |
| springdoc-openapi | 3.0.3 | Documentación Swagger/OpenAPI |
| JUnit 5 | 6.0.3 | Pruebas de integración |
| Maven | 3.9+ | Gestión de dependencias |
| IntelliJ IDEA | 2025/2026 | IDE de desarrollo |
| Postman | — | Pruebas de endpoints REST |

---

## Comunicaciones entre Microservicios (Feign)

| Origen | Destino | Propósito |
|---|---|---|
| ms-menu | ms-categorias-menu | Verificar categoría activa al crear plato |
| ms-inventario | ms-sucursales | Verificar sede operativa al crear ingrediente |
| ms-proveedores | ms-sucursales | Verificar sede operativa al crear orden |
| ms-empleados | ms-sucursales | Verificar sede operativa al crear turno |
| ms-reservas | ms-sucursales | Verificar sede operativa al crear reserva |
| ms-reservas | ms-usuarios | Verificar usuario activo al crear reserva |
| ms-pedidos | ms-sucursales | Verificar sede operativa al crear pedido |
| ms-pedidos | ms-menu | Verificar plato disponible al crear pedido |
| ms-pagos | ms-pedidos | Verificar pedido y usuario al procesar pago |

---

## Funcionalidades Implementadas

### Gestión de Usuarios
- CRUD completo de usuarios con roles
- Validación de email y RUT únicos
- Encriptación de contraseñas con BCrypt
- Activación/desactivación de usuarios (baja lógica)

### Gestión de Sucursales
- CRUD completo de sedes de casino
- Control de estado operativo por sede
- Gestión de horarios de apertura y cierre

### Gestión de Menú
- CRUD de tipos de plato y platos
- Programación diaria de menú por sede con control de raciones
- Control de disponibilidad de platos
- Descuento automático de raciones al recibir pedidos
- Validación de categoría activa via Feign

### Gestión de Pedidos
- Creación de pedidos con múltiples detalles
- Validación de plato disponible via Feign
- Validación de sede operativa via Feign
- Flujo de estados: RECIBIDO → EN_PREPARACION → LISTO_RETIRO → ENTREGADO

### Gestión de Pagos
- Procesamiento de transacciones con validación Feign
- Métodos de pago: TARJETA_CREDITO, JUNAEB, SUBSIDIO_EMPRESA, EFECTIVO
- Relación 1 a 1 entre pedido y pago
- Estados de pago: PENDIENTE, APROBADO, RECHAZADO

### Gestión de Inventario
- CRUD de ingredientes por sede
- Registro de movimientos de stock (ENTRADA/SALIDA/MERMA)
- Actualización automática de stock al registrar movimientos
- Control de stock mínimo y alertas de stock bajo
- Validación de sede operativa via Feign

### Gestión de Proveedores
- CRUD completo de proveedores con baja lógica
- Creación de órdenes de compra con detalles y cálculo de totales
- Validación de sede operativa via Feign
- Estados de orden: PENDIENTE, RECIBIDA, CANCELADA

### Gestión de Reservas
- Creación de turnos disponibles por sede con control de cupos
- Sistema de reservas con validación de usuario y sede via Feign
- Cancelación automática con devolución de cupo al turno

### Gestión de Empleados
- CRUD completo de empleados con baja lógica
- Asignación de turnos por sede, fecha y tipo (MAÑANA/TARDE/NOCHE)
- Validación de sede operativa via Feign

### Gestión de Categorías de Menú
- CRUD de categorías con estado activo/inactivo
- Etiquetas nutricionales por categoría (relación 1 a 1)
- Información de calorías, proteínas, carbohidratos, grasas y alergenos
