package com.si.ProyectoMavenActividad1;

import org.springframework.web.bind.annotation.*;
import java.util.*;

// @RestController maneja peticiones HTTP y devuelve datos directamente (JSON)
@RestController
@RequestMapping("/api")
public class EdpointControladores {

    // Simulacion de base de datos
    private List<Map<String, Object>> items = new ArrayList<>(List.of(
        new HashMap<>(Map.of("id", 1, "nombre", "Item de prueba"))
    ));

    // ---------- GET ---------- Obtener
    @GetMapping("/items")
    public List<Map<String, Object>> obtenerItems() {
        return items;
    }

    // GET con parámetros especificos
    @GetMapping("/items/{id}")
    public Map<String, Object> obtenerItemPorId(@PathVariable int id) {
        return items.stream()
                .filter(item -> (int) item.get("id") == id)
                .findFirst()
                .orElse(Map.of("error", "Item no encontrado"));
    }

    // ---------- POST ---------- Crear
    @PostMapping("/items")
    public Map<String, Object> crearItem(@RequestBody Map<String, Object> nuevoItem) {
        nuevoItem.put("id", items.size() + 1); 
        items.add(nuevoItem);
        return nuevoItem; 
    }

    // ---------- PUT ---------- Remplaza
    @PutMapping("/items/{id}")
    public Map<String, Object> actualizarItem(@PathVariable int id, @RequestBody Map<String, Object> itemActualizado) {
        for (Map<String, Object> item : items) {
            if ((int) item.get("id") == id) {
                item.clear();              
                item.putAll(itemActualizado); 
                item.put("id", id);        
                return item;
            }
        }
        return Map.of("error", "Item no encontrado");
    }

    // ---------- PATCH ---------- Actualiza parcialmente
    @PatchMapping("/items/{id}")
    public Map<String, Object> actualizarParcial(@PathVariable int id, @RequestBody Map<String, Object> cambios) {
        for (Map<String, Object> item : items) {
            if ((int) item.get("id") == id) {
                item.putAll(cambios); 
                return item;
            }
        }
        return Map.of("error", "Item no encontrado");
    }

    // ---------- DELETE ---------- Elimina un recurso
    @DeleteMapping("/items/{id}")
    public Map<String, String> eliminarItem(@PathVariable int id) {
        boolean eliminado = items.removeIf(item -> (int) item.get("id") == id);
        if (eliminado) {
            return Map.of("mensaje", "Item eliminado correctamente");
        }
        return Map.of("error", "Item no encontrado");
    }
}