package com.jeremyarevalo.webapp.biblioteca.service;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jeremyarevalo.webapp.biblioteca.model.Prestamo;
import com.jeremyarevalo.webapp.biblioteca.repository.PrestamoRepository;

@Service
public class PrestamoService implements IPrestamoService{

    @Autowired
    private PrestamoRepository prestamoRepository;

    @Override
    public List<Prestamo> listarPrestamo() {
        return prestamoRepository.findAll();
    }

    @Override
    public Prestamo buscarPrestamo(Long id) {
        return prestamoRepository.findById(id).orElse(null);
    }

    @Override
    public Boolean guardarPrestamo(Prestamo prestamo) {
        if (!VerificarSiUnUsuarioTienePrestamoActivo(prestamo)) {
            prestamoRepository.save(prestamo);  
            return true; 
        }else{
            return false;
        }
    }

    @Override
    public void eliminarPrestamo(Prestamo prestamo) {
        prestamoRepository.delete(prestamo);
    }

    @Override
    public Boolean VerificarSiUnUsuarioTienePrestamoActivo(Prestamo prestamoNuevo) {
        List<Prestamo> prestamos = listarPrestamo();
        Boolean flag = false;
        for (Prestamo prestamo : prestamos) {
            if(prestamoNuevo.getCliente().getDpi().equals(prestamo.getCliente().getDpi()) && prestamo.getVigencia() == true){
                flag = true;
            }
        }
        return flag;
    }


}
