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

import com.jeremyarevalo.webapp.biblioteca.model.Cliente;
import com.jeremyarevalo.webapp.biblioteca.service.ClienteService;




@Controller
@RestController
@RequestMapping(value = "cliente")
public class ClienteController {
    
    @Autowired
    ClienteService clienteService;

    @GetMapping("/")
    public List<Cliente> listarCliente(){
        return clienteService.listarCliente();
    }

    

    @GetMapping("/{id}")
    public ResponseEntity<Cliente> buscarClientePorId(@PathVariable Long id){
        try{
            return ResponseEntity.ok(clienteService.buscarClientePorId(id));
        }catch(Exception e){
            return ResponseEntity.badRequest().body(null);
        }
    }



    @PostMapping("/")
    public ResponseEntity<Map<String, Boolean>> agregarCliente(@RequestBody Cliente cliente){
        Map<String, Boolean> response = new HashMap<>();
        try{
            clienteService.GuardarCliente(cliente);
            response.put("Se agrego con exito", Boolean.TRUE);
            return ResponseEntity.ok(response);
        } catch (Exception e){
            response.put("no se pudo agregar", Boolean.FALSE);
            return ResponseEntity.badRequest().body(response);
        }

    }



    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Boolean>> editarCliente(@PathVariable Long id, @RequestBody Cliente clienteNuevo){
        Map<String, Boolean> response = new HashMap<>();
        try{
            Cliente clienteViejo = clienteService.buscarClientePorId(id);
            clienteViejo.setNombre(clienteNuevo.getNombre());
            clienteViejo.setApellido(clienteNuevo.getApellido());
            clienteViejo.setNumero(clienteNuevo.getNumero());
            clienteService.GuardarCliente(clienteViejo);
            response.put("Se edito con exito", Boolean.TRUE);
            return ResponseEntity.ok(response);
        }catch(Exception e){
            response.put("no se pudo editar", Boolean.FALSE);
            return ResponseEntity.badRequest().body(response);
        }
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Boolean>> eliminarClientePorId(@PathVariable Long id){
        Map<String, Boolean> response = new HashMap<>();
        try{
            Cliente cliente = clienteService.buscarClientePorId(id);
            clienteService.eliminarCliente(cliente);
            response.put("Se elimino con exito", Boolean.TRUE);
            return ResponseEntity.ok(response);
        }catch(Exception e){
            response.put("no se pudo eliminar", Boolean.TRUE);
            return ResponseEntity.badRequest().body(response);
        }
    }
}
