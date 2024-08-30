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

import com.jeremyarevalo.webapp.biblioteca.model.Prestamo;
import com.jeremyarevalo.webapp.biblioteca.service.PrestamoService;

@Controller
@RestController
@RequestMapping(value = "prestamo")
public class PrestamoController {
    @Autowired
    PrestamoService prestamoService;

    @GetMapping("/")
    public ResponseEntity<List<Prestamo>> listarPrestamo(){
        try {
            return ResponseEntity.ok(prestamoService.listarPrestamo());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Prestamo> buscarPretamoPorId(@PathVariable Long id){
        try{
            return ResponseEntity.ok(prestamoService.buscarPrestamo(id));
        }catch(Exception e){
            return ResponseEntity.badRequest().body(null);
        }
    }


    @PostMapping("/")
    public ResponseEntity<Map<String, Boolean>> agregarPrestamo(@RequestBody Prestamo prestamo){
        Map<String, Boolean> response = new HashMap<>();
        try {
            if (prestamoService.guardarPrestamo(prestamo)) {
                response.put("Se agrego el prestamo", Boolean.TRUE);
                return ResponseEntity.ok(response);
            } else {
                response.put("Error", Boolean.FALSE);
                response.put("No se pudo crear el prestamo porq el cliente ya tiene un prestamo", Boolean.FALSE);
                return ResponseEntity.badRequest().body(response);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            response.put("Error", Boolean.FALSE);
            response.put("No se pudo crear el prestamo", Boolean.FALSE);
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Boolean>> editarPrestamo(@PathVariable Long id, @RequestBody Prestamo prestamoNuevo){
        Map<String, Boolean> response = new HashMap<>();
        try{
            Prestamo prestamoViejo = prestamoService.buscarPrestamo(id);
            prestamoViejo.setFechaDePrestamo(prestamoNuevo.getFechaDePrestamo());
            prestamoViejo.setFechaDeDevolucion(prestamoNuevo.getFechaDeDevolucion());
            prestamoViejo.setVigencia(prestamoNuevo.getVigencia());
            prestamoViejo.setEmpleado(prestamoNuevo.getEmpleado());
            prestamoViejo.setCliente(prestamoNuevo.getCliente());
            prestamoViejo.setLibro(prestamoNuevo.getLibro());
            prestamoService.guardarPrestamo(prestamoViejo);
            response.put("Se edito con exito", Boolean.TRUE);
            return ResponseEntity.ok(response);
        }catch(Exception e){
            response.put("no se pudo editar", Boolean.FALSE);
            return ResponseEntity.badRequest().body(response);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Boolean>> eliminarPrestamoPorId(@PathVariable Long id){
        Map<String, Boolean> response = new HashMap<>();
        try{
            Prestamo prestamo = prestamoService.buscarPrestamo(id);
            prestamoService.eliminarPrestamo(prestamo);
            response.put("Se elimino con exito", Boolean.TRUE);
            return ResponseEntity.ok(response);
        }catch(Exception e){
            response.put("no se pudo eliminar", Boolean.TRUE);
            return ResponseEntity.badRequest().body(response);
        }
    }
}
