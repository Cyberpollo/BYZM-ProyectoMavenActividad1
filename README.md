# Proyecto #1 - Controlador de Endpoints HTTP con Spring Boot

## Descripción
Este proyecto es un controlador REST hecho con Java Spring Boot. Tiene un
endpoint para cada uno de los 5 verbos HTTP principales (GET, POST, PUT,
PATCH y DELETE), trabajando sobre un recurso simple llamado "items" que se
guarda en memoria mientras la aplicación está corriendo.

## Tecnologías utilizadas
- Java 25
- Spring Boot (Spring Web)
- Maven

## Alumno
Brittany Yaretzy Zacarias Montiel

## Estructura del controlador
La clase `EndpointController` tiene la anotación `@RestController`, que le
dice a Spring que los métodos de esta clase van a devolver datos directamente
(en este caso JSON), en lugar de regresar una vista HTML como en un proyecto
web tradicional. También está `@RequestMapping("/api")` a nivel de clase, que
define el prefijo que van a compartir todos los endpoints, para no tener que
escribir "/api" en cada uno por separado.

Los datos no vienen de una base de datos real, sino de una lista en memoria en mi actividad.

Se usa `HashMap` y `ArrayList` (en vez de `Map.of()` y `List.of()` a secas)
porque estos últimos crean estructuras de solo lectura, y para que los
métodos de POST, PUT, PATCH y DELETE puedan modificar la lista (agregar,
reemplazar o quitar elementos) se necesita que sea mutable.

## Endpoints implementados

### GET /api/items

Regresa la lista completa de items tal como está en ese momento. No recibe
ningún parámetro y no modifica nada, solo lee y devuelve.

### GET /api/items/{id}

Aquí el id viene directo en la URL, no en el body. La anotación
`@PathVariable` es la que le indica a Spring que tome ese valor de la ruta
(por ejemplo, si entras a `/api/items/1`, Spring toma el 1 y lo pasa como el
parámetro `id`). El método recorre la lista buscando un item con ese id; si
no lo encuentra, regresa un mensaje de error en vez de tronar.

### POST /api/items

Este endpoint recibe los datos del nuevo item desde el cuerpo (body) de la
petición, no de la URL. Para eso se usa `@RequestBody`, que convierte
automáticamente el JSON que se manda en la petición a un objeto de Java (en
este caso, un `Map`). El id no lo manda quien hace la petición, se calcula
solo dentro del método según cuántos items ya hay en la lista.

### PUT /api/items/{id}

Este método recibe dos parámetros de dos lugares distintos: el id viene de
la URL (`@PathVariable`) y los datos nuevos vienen del body (`@RequestBody`).
Lo que hace PUT es borrar todo el contenido anterior del item (`item.clear()`)
y ponerle encima el contenido nuevo completo. Por eso si en el body no mandas
algún campo que el item ya tenía, ese campo se pierde. El id se vuelve a
poner después de limpiar, para que no se pierda ese dato en particular.

### PATCH /api/items/{id}

Se parece mucho a PUT en la firma del método, pero la diferencia está en la
línea `item.putAll(cambios)` en vez de `item.clear()` seguido de
`putAll()`. Aquí solo se sobreescriben los campos que vienen en el body,
dejando el resto del item intacto. Esa es la diferencia real entre PUT y
PATCH: uno reemplaza todo, el otro solo toca lo que le mandas.

### DELETE /api/items/{id}

Recibe el id desde la URL y usa `removeIf` para quitar de la lista cualquier
item que tenga ese id. Regresa un mensaje confirmando si se eliminó o si no
se encontró nada con ese id.

## Cómo ejecutar el proyecto
1. Clonar el repositorio.
2. Abrir el proyecto en Eclipse (o cualquier IDE con soporte para Maven).
3. Ejecutar la clase principal, ProyectoMavenActividad1Application.
4. La aplicación arranca en http://localhost:8080.

## Cómo probar los endpoints
Para GET se puede probar directo desde el navegador. Para POST, PUT, PATCH
y DELETE se necesita una herramienta como Postman, porque estos verbos
requieren mandar un body en formato JSON, y el navegador no deja hacer eso
directamente desde la barra de direcciones.