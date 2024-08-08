package com.jeremyarevalo.webapp.biblioteca.service;
import java.util.List;

import com.jeremyarevalo.webapp.biblioteca.model.Prestamo;

public interface IPrestamoService {

    public List<Prestamo> listarPrestamo();

    public Prestamo buscarPrestamo(Long id);

    public Boolean guardarPrestamo(Prestamo prestamo);

    public void eliminarPrestamo(Prestamo prestamo);
    
    public Boolean VerificarSiUnUsuarioTienePrestamoActivo(Prestamo prestamoNuevo);
}
