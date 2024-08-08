package com.jeremyarevalo.webapp.biblioteca.service;
import java.util.List;
import com.jeremyarevalo.webapp.biblioteca.model.Libro;

public interface ILibroService {

    public List<Libro> listarLibros();
    
    public Libro guardarLibro(Libro libro);

    public Libro buscarLibro(Long id);

    public void eliminarLibro(Libro libro);
}
