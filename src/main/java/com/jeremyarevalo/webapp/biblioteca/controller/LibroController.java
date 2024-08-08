package com.jeremyarevalo.webapp.biblioteca.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jeremyarevalo.webapp.biblioteca.model.Libro;
import com.jeremyarevalo.webapp.biblioteca.service.LibroService;

@Controller
@RestController
@RequestMapping(value = "libro")
public class LibroController {
    
    @Autowired
    LibroService libroService;

    @GetMapping("/")
    public ResponseEntity<List<Libro>> listaLibros(){
        try {
            return ResponseEntity.ok(libroService.listarLibros());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Libro> buscarLibroPorId(@PathVariable Long id){
        try{
            return ResponseEntity.ok(libroService.buscarLibro(id));
        }catch(Exception e){
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PostMapping("/")
    public ResponseEntity<Map<String, Boolean>> agregarLibro(@RequestBody Libro libro){
        Map<String, Boolean> response = new HashMap<>();
        try {
            libroService.guardarLibro(libro);
            response.put("Se agrego el libro", Boolean.TRUE);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("Error", Boolean.FALSE);
            response.put("No se pudo crear el libro", Boolean.FALSE);
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Boolean>> editarLibro(@PathVariable Long id, @RequestBody Libro libroNuevo){
        Map<String, Boolean> response = new HashMap<>();
        try{
            Libro libroViejo = libroService.buscarLibro(id);
            libroViejo.setIsbn(libroNuevo.getIsbn());
            libroViejo.setNombre(libroNuevo.getNombre());
            libroViejo.setSinopsis(libroNuevo.getSinopsis());
            libroViejo.setAutor(libroNuevo.getAutor());
            libroViejo.setEditorial(libroNuevo.getEditorial());
            libroViejo.setDisponibilidad(libroNuevo.getDisponibilidad());
            libroViejo.setNumeroEstanteria(libroNuevo.getNumeroEstanteria());
            libroViejo.setCluster(libroNuevo.getCluster());
            libroViejo.setCategoria(libroNuevo.getCategoria());
            libroService.guardarLibro(libroViejo);
            response.put("Se edito con exito", Boolean.TRUE);
            return ResponseEntity.ok(response);
        }catch(Exception e){
            response.put("no se pudo editar", Boolean.FALSE);
            return ResponseEntity.badRequest().body(response);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Boolean>> eliminarLibroPorId(@PathVariable Long id){
        Map<String, Boolean> response = new HashMap<>();
        try{
            Libro libro = libroService.buscarLibro(id);
            libroService.eliminarLibro(libro);
            response.put("Se elimino con exito", Boolean.TRUE);
            return ResponseEntity.ok(response);
        }catch(Exception e){
            response.put("no se pudo eliminar", Boolean.TRUE);
            return ResponseEntity.badRequest().body(response);
        }
    }


}
