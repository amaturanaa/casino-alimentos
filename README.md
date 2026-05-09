Casino de Alimentos — Sistema de Gestión de Microservicios

Sistema de gestión integral para una cadena de casinos de alimentos, desarrollado con 
arquitectura de microservicios usando Spring Boot. El sistema permite administrar usuarios, 
sucursales, menús, pedidos, pagos, inventario, proveedores, reservas y empleados de forma 
distribuida e independiente.

Proyecto desarrollado para la asignatura **Desarrollo Fullstack I (DSY1103)

---

Integrantes 

| Nombre             | GitHub |
| Maximiliano Mella  | @amaturanaa (https://github.com/amaturanaa) |
| Alejandro Maturana | @MaxiMella (https://github.com/MaxiMella) |

El sistema está compuesto por 12 servicios organizados bajo una arquitectura de 
microservicios con registro de servicios mediante Eureka y enrutamiento centralizado 
mediante API Gateway.

Microservicios

| Microservicio    | Puerto | Base de Datos      | Descripción |
| eureka-server      | 8761 | ------------       | Registro y descubrimiento de servicios |
| api-gateway        | 8080 | ------------       | Punto de entrada único al sistema |
| ms-usuarios        | 8082 | db_usuarios        | Gestión de usuarios y roles del sistema |
| ms-sucursales      | 8083 | db_sucursales      | Gestión de sedes y empleados de casino |
| ms-menu            | 8084 | db_menu            | Gestión de platos, tipos y programación diaria |
| ms-pedidos         | 8085 | db_pedidos         | Gestión de pedidos y sus detalles |
| ms-pagos           | 8086 | db_pagos           | Procesamiento de transacciones y pagos |
| ms-categorias-menu | 8087 | db_categorias_menu | Categorías y etiquetas nutricionales |
| ms-proveedores     | 8088 | db_proveedores     | Gestión de proveedores y órdenes de compra |
| ms-reservas        | 8089 | db_reservas        | Gestión de turnos y reservas de comensales |
| ms-empleados       | 8090 | db_empleados       | Gestión de empleados y turnos de trabajo |
| ms-inventario      | 8091 | db_inventario      | Control de stock e ingredientes por sede |


Tecnologías Utilizadas

| Tecnología                  | Versión      | Uso |
| Java                        | 21 (Temurin) | Lenguaje principal |
| Spring Boot                 | 3.x          | Framework base |
| Spring Cloud Netflix Eureka | ------------ | Registro de servicios |
| Spring Cloud OpenFeign      | ------------ | Comunicación entre microservicios |
| Spring Data JPA             | ------------ | Persistencia de datos |
| Hibernate                   | ------------ | ORM |
| MySQL / MariaDB             | ------------ | Base de datos |
| XAMPP                       | ------------ | Servidor de base de datos local |
| Lombok                      | ------------ | Reducción de código boilerplate |
| Bean Validation (JSR 380)   | ------------ | Validación de datos |
| SLF4J + Logback             | ------------ | Logs estructurados |
| Maven                       | ------------ | Gestión de dependencias |
| IntelliJ IDEA Ultimate      | ------------ | IDE de desarrollo |
| Postman                     | ------------ | Pruebas de endpoints REST |

---

Funcionalidades Implementadas

### Gestión de Usuarios
- CRUD completo de usuarios con roles
- Validación de email y RUT únicos
- Encriptación de contraseñas con BCrypt
- Activación/desactivación de usuarios

### Gestión de Sucursales
- CRUD completo de sedes de casino
- Control de estado operativo por sede
- Gestión de horarios de apertura y cierre

### Gestión de Menú
- CRUD de tipos de plato y platos
- Programación diaria de menú por sede
- Control de disponibilidad de platos
- Descuento automático de raciones
- Validación de categoría activa via Feign

### Gestión de Pedidos
- Creación de pedidos con múltiples detalles
- Validación de plato disponible via Feign
- Validación de sede operativa via Feign
- Flujo de estados: RECIBIDO → EN_PREPARACION → LISTO_RETIRO → ENTREGADO

### Gestión de Pagos
- Procesamiento de transacciones
- Métodos de pago: TARJETA_CREDITO, JUNAEB, SUBSIDIO_EMPRESA, EFECTIVO
- Validación de pedido y monto via Feign
- Estados de pago: PENDIENTE, APROBADO, RECHAZADO

### Gestión de Inventario
- CRUD de ingredientes por sede
- Registro de movimientos de stock (ENTRADA/SALIDA/MERMA)
- Control de stock mínimo y alertas de stock bajo
- Validación de sede operativa via Feign

### Gestión de Proveedores
- CRUD completo de proveedores
- Creación de órdenes de compra con detalles
- Validación de sede operativa via Feign
- Estados de orden: PENDIENTE, RECIBIDA, CANCELADA

### Gestión de Reservas
- Creación de turnos disponibles por sede
- Sistema de reservas con control de cupos
- Validación de usuario activo via Feign
- Validación de sede operativa via Feign
- Cancelación automática con devolución de cupo

### Gestión de Empleados
- CRUD completo de empleados
- Asignación de turnos por sede y fecha
- Validación de sede operativa via Feign
- Control de estado activo/inactivo

### Gestión de Categorías de Menú
- CRUD de categorías con estado activo/inactivo
- Etiquetas nutricionales por categoría
- Información de calorías, proteínas, carbohidratos y grasas
