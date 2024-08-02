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

import com.jeremyarevalo.webapp.biblioteca.model.Categoria;
import com.jeremyarevalo.webapp.biblioteca.service.CategoriaService;

@Controller
@RestController
@RequestMapping(value = "categoria")
public class CategoriaController {
    
    @Autowired
    CategoriaService categoriaService;

    @GetMapping("/")
    public List<Categoria> listaCategorias(){
        return categoriaService.listarCategoria();
    }

    

    @GetMapping("/{id}")
    public ResponseEntity<Categoria> buscarCategoriaPorId(@PathVariable Long id){
        try{
            return ResponseEntity.ok(categoriaService.buscarCategoriaPorId(id));
        }catch(Exception e){
            return ResponseEntity.badRequest().body(null);
        }
    }



    @PostMapping("/")
    public ResponseEntity<Map<String, Boolean>> agregarCategoria(@RequestBody Categoria categoria){
        Map<String, Boolean> response = new HashMap<>();
        try{
            categoriaService.guardarCategoria(categoria);
            response.put("Se agrego con exito", Boolean.TRUE);
            return ResponseEntity.ok(response);
        } catch (Exception e){
            response.put("se agrego con exito", Boolean.FALSE);
            return ResponseEntity.badRequest().body(response);
        }

    }



    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Boolean>> editarCategoria(@PathVariable Long id, @RequestBody Categoria categoriaNueva){
        Map<String, Boolean> response = new HashMap<>();
        try{
            Categoria categoriaVieja = categoriaService.buscarCategoriaPorId(id);
            categoriaVieja.setNombreCategoria(categoriaNueva.getNombreCategoria());
            categoriaService.guardarCategoria(categoriaVieja);
            response.put("Se edito con exito", Boolean.TRUE);
            return ResponseEntity.ok(response);
        }catch(Exception e){
            response.put("no se pudo editar", Boolean.FALSE);
            return ResponseEntity.badRequest().body(response);
        }
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Boolean>> eliminarCategoriaPorId(@PathVariable Long id){
        Map<String, Boolean> response = new HashMap<>();
        try{
            Categoria categoria = categoriaService.buscarCategoriaPorId(id);
            categoriaService.eliminarCategoria(categoria);
            response.put("Se elimino con exito", Boolean.TRUE);
            return ResponseEntity.ok(response);
        }catch(Exception e){
            response.put("Se elimino con exito", Boolean.TRUE);
            return ResponseEntity.badRequest().body(response);
        }
    }

}
