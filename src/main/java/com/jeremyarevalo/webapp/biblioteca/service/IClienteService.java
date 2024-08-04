package com.jeremyarevalo.webapp.biblioteca.service;
import java.util.List;
import com.jeremyarevalo.webapp.biblioteca.model.Cliente;

public interface IClienteService {

    public List<Cliente> listarCliente();

    public Cliente buscarClientePorId(Long id);
    
    public Cliente GuardarCliente(Cliente cliente);

    public void eliminarCliente(Cliente cliente);
    
}
