package com.jeremyarevalo.webapp.biblioteca.service;
import java.util.List;
import com.jeremyarevalo.webapp.biblioteca.model.Prestamo;
import com.jeremyarevalo.webapp.biblioteca.util.MethodType;

public interface IPrestamoService {

    public List<Prestamo> listarPrestamo();

    public Prestamo buscarPrestamo(Long id);

    public Prestamo guardarPrestamo(Prestamo prestamo, MethodType methodType);

    public void eliminarPrestamo(Prestamo prestamo);
}
