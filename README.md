# Laboratorio VI — Diseño y Desarrollo de APIs REST

Estructura del repositorio:

```
LaboratorioVI/
├── Ejercicio1/   api-libros.yaml     (diseño OpenAPI - Libros)
├── Ejercicio2/   api-cursos.yaml     (diseño OpenAPI - Cursos)
├── Ejercicio3/   api-reservas.yaml   (diseño OpenAPI - Reservas)
├── Ejercicio4/   libros-api/         (Spring Boot - implementación Ejercicio 1)
├── Ejercicio5/   cursos-api/         (Spring Boot - implementación Ejercicio 2)
└── Ejercicio6/   reservas-api/       (Spring Boot - implementación Ejercicio 3)
```

Cada proyecto Spring Boot sigue una arquitectura por capas:

```
controller/  -> expone los endpoints REST
service/     -> contiene la lógica de negocio (interfaz + implementación)
repository/  -> almacena los datos en memoria (List)
model/       -> entidades de dominio
dto/         -> objetos de request/response
exception/   -> excepciones de negocio y manejador global (@RestControllerAdvice)
```

## Requisitos

* Java 17+
* Maven 3.8+ (o el wrapper `mvnw` si lo agregas con `mvn -N wrapper:wrapper`)
* Git Bash (control de versiones)

## Cómo ejecutar cada API

Cada ejercicio es un proyecto Maven independiente. Desde la carpeta de cada uno:

```bash
cd Ejercicio4
mvn spring-boot:run
```

Puertos usados (para poder correr las 3 APIs al mismo tiempo si se desea):

| Ejercicio | API       | Puerto |
|-----------|-----------|--------|
| 4         | Libros    | 8081   |
| 5         | Cursos    | 8082   |
| 6         | Reservas  | 8083   |

## Pruebas sugeridas (para las capturas de pantalla del entregable)

### Ejercicio 4 — Libros (puerto 8081)

```bash
# Registrar libro
curl -X POST http://localhost:8081/api/libros \
  -H "Content-Type: application/json" \
  -d '{"titulo":"Cien años de soledad","autor":"Gabriel García Márquez","isbn":"978-0307474728","anioPublicacion":1967,"estado":"DISPONIBLE"}'

# Consultar todos
curl http://localhost:8081/api/libros

# Consultar por id
curl http://localhost:8081/api/libros/1

# Consultar por título
curl http://localhost:8081/api/libros/titulo/soledad

# Actualizar
curl -X PUT http://localhost:8081/api/libros/1 \
  -H "Content-Type: application/json" \
  -d '{"titulo":"Cien años de soledad","autor":"Gabriel García Márquez","isbn":"978-0307474728","anioPublicacion":1967,"estado":"PRESTADO"}'

# Eliminar
curl -X DELETE http://localhost:8081/api/libros/1
```

### Ejercicio 5 — Cursos (puerto 8082)

```bash
# Crear curso
curl -X POST http://localhost:8082/api/cursos \
  -H "Content-Type: application/json" \
  -d '{"nombre":"Estructura de Datos","codigo":"CC-201","creditos":4,"estado":"ACTIVO"}'

# Consultar todos
curl http://localhost:8082/api/cursos

# Consultar por código
curl http://localhost:8082/api/cursos/codigo/CC-201

# Actualizar
curl -X PUT http://localhost:8082/api/cursos/1 \
  -H "Content-Type: application/json" \
  -d '{"nombre":"Estructura de Datos II","codigo":"CC-201","creditos":5,"estado":"ACTIVO"}'

# Eliminar
curl -X DELETE http://localhost:8082/api/cursos/1
```

### Ejercicio 6 — Reservas (puerto 8083)

```bash
# Crear reserva
curl -X POST http://localhost:8083/api/reservas \
  -H "Content-Type: application/json" \
  -d '{"nombreCliente":"Axel Santizo","habitacion":"301","fechaEntrada":"2026-10-01","fechaSalida":"2026-10-05","estado":"CONFIRMADA"}'

# Consultar todas
curl http://localhost:8083/api/reservas

# Consultar por id
curl http://localhost:8083/api/reservas/1

# Actualizar
curl -X PUT http://localhost:8083/api/reservas/1 \
  -H "Content-Type: application/json" \
  -d '{"nombreCliente":"Axel Santizo","habitacion":"302","fechaEntrada":"2026-10-01","fechaSalida":"2026-10-06","estado":"CONFIRMADA"}'

# Cancelar
curl -X DELETE http://localhost:8083/api/reservas/1
```

