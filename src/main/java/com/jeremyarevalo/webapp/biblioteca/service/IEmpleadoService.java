package com.jeremyarevalo.webapp.biblioteca.service;
import java.util.List;
import com.jeremyarevalo.webapp.biblioteca.model.Empleado;

public interface IEmpleadoService {
    public List<Empleado> listaEmpleados();

    public Empleado buscarEmpleadoPorId(Long id);
    
    public Empleado GuardarEmpleado(Empleado empleado);

    public void eliminarEmpleado(Empleado empleado);
}
