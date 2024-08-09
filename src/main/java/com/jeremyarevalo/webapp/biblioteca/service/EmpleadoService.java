package com.jeremyarevalo.webapp.biblioteca.service;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jeremyarevalo.webapp.biblioteca.model.Empleado;
import com.jeremyarevalo.webapp.biblioteca.repository.EmpleadoRepository;

@Service
public class EmpleadoService implements IEmpleadoService{

    @Autowired
    private EmpleadoRepository empleadoRepository;

    @Override
    public List<Empleado> listaEmpleados() {
        return empleadoRepository.findAll();
    }

    @Override
    public Empleado buscarEmpleadoPorId(Long id) {
        return empleadoRepository.findById(id).orElse(null);
    }

    @Override
    public Boolean GuardarEmpleado(Empleado empleado) {
        if (!verificarDpiDuplicado(empleado)) {
            empleadoRepository.save(empleado);  
            return true; 
        }else{
            return false;
        }
    }

    @Override
    public void eliminarEmpleado(Empleado empleado) {
        empleadoRepository.delete(empleado);
    }

    @Override
    public Boolean verificarDpiDuplicado(Empleado empleadoNuevo) {
        List<Empleado> empleados = listaEmpleados();
        Boolean flag = false;
        for (Empleado empleado : empleados) {
            if (empleado.getDpi().equals(empleadoNuevo.getDpi())) {
                flag = true; //si existe dpi
            }
            
        }
        return flag;
    }
    
}
