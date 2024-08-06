package com.jeremyarevalo.webapp.biblioteca.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jeremyarevalo.webapp.biblioteca.model.Libro;
import com.jeremyarevalo.webapp.biblioteca.service.LibroService;

@Controller
@RestController
@RequestMapping(value = "")
public class LibroController {
    
    @Autowired
    LibroService libroService;

    @GetMapping("/libros")
    public ResponseEntity<List<Libro>> listarLibros(){
        try {
            return ResponseEntity.ok(libroService.listarLibros());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PostMapping
    public ResponseEntity<Map<String, Boolean>> agregarLibro(@RequestBody Libro libro){
        Map<String, Boolean> response = new HashMap<>();
        try {
            libroService.guardarLibro(libro);
            response.put("Se agrego el libro", Boolean.TRUE);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("Error", Boolean.FALSE);
            response.put("No se pudo crear el libro", Boolean.FALSE);
        }
    }
}
