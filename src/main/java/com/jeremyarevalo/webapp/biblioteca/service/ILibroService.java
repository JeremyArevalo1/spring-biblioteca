package com.jeremyarevalo.webapp.biblioteca.service;
import java.util.List;
import com.jeremyarevalo.webapp.biblioteca.model.Libro;

public interface ILibroService {

    public List<Libro> listarLibros();
    
    public Libro guardarLibro(Long id);

    public Libro buscarLibro(Libro libro);

    public void eliminarLibro(Libro libro);
}
