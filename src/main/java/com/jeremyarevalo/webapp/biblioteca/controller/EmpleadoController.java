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

import com.jeremyarevalo.webapp.biblioteca.model.Empleado;
import com.jeremyarevalo.webapp.biblioteca.service.EmpleadoService;

@Controller
@RestController
@RequestMapping(value = "empleado")
public class EmpleadoController {
    
    @Autowired
    EmpleadoService empleadoService;

    @GetMapping("/")
    public List<Empleado> listarEmpleados(){
        return empleadoService.listaEmpleados();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Empleado> buscarEmpleadoPorId(@PathVariable Long id){
        try{
            return ResponseEntity.ok(empleadoService.buscarEmpleadoPorId(id));
        }catch(Exception e){
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PostMapping("/")
    public ResponseEntity<Map<String, Boolean>> agregarEmpleado(@RequestBody Empleado empleado){
        Map<String, Boolean> response = new HashMap<>();
        try{
            if (empleadoService.GuardarEmpleado(empleado)) {
                response.put("Se agrego con exito", Boolean.TRUE);
                return ResponseEntity.ok(response);
            }else{
                response.put("no se pudo crear el empleado, por Dpi Duplicado", Boolean.FALSE);
                response.put("Err", Boolean.FALSE);
                return ResponseEntity.badRequest().body(response);
            }
        } catch (Exception e){
            response.put("no se pudo agregar", Boolean.FALSE);
            response.put("Err", Boolean.FALSE);
            return ResponseEntity.badRequest().body(response);
        }

    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Boolean>> editarEmpleado(@PathVariable Long id, @RequestBody Empleado empleadoNuevo){
        Map<String, Boolean> response = new HashMap<>();
        try{
            Empleado empleadoViejo = empleadoService.buscarEmpleadoPorId(id);
            empleadoViejo.setNombre(empleadoNuevo.getNombre());
            empleadoViejo.setApellido(empleadoNuevo.getApellido());
            empleadoViejo.setTelefono(empleadoNuevo.getTelefono());
            empleadoViejo.setDireccion(empleadoNuevo.getDireccion());
            empleadoViejo.setDpi(empleadoNuevo.getDpi());
            if(empleadoService.GuardarEmpleado(empleadoViejo)){
                response.put("Se edito con exito", Boolean.TRUE);
                return ResponseEntity.ok(response);
            }else{
                response.put("no se pudo editar por un dpi duplicado    ", Boolean.FALSE);
                return ResponseEntity.badRequest().body(response);
            }

        }catch(Exception e){
            response.put("no se pudo editar", Boolean.FALSE);
            return ResponseEntity.badRequest().body(response);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Boolean>> eliminarEmpleadoPorId(@PathVariable Long id){
        Map<String, Boolean> response = new HashMap<>();
        try{
            Empleado empleado = empleadoService.buscarEmpleadoPorId(id);
            empleadoService.eliminarEmpleado(empleado);
            response.put("Se elimino con exito", Boolean.TRUE);
            return ResponseEntity.ok(response);
        }catch(Exception e){
            response.put("no se pudo eliminar", Boolean.TRUE);
            return ResponseEntity.badRequest().body(response);
        }
    }
}
