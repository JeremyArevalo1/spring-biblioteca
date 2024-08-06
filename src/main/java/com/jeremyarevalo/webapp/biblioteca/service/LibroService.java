package com.jeremyarevalo.webapp.biblioteca.service;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jeremyarevalo.webapp.biblioteca.model.Libro;
import com.jeremyarevalo.webapp.biblioteca.repository.LibroRepository;

@Service
public class LibroService implements ILibroService{

    @Autowired
    private LibroRepository libroRepository;

    @Override
    public List<Libro> listarLibros() {
        return libroRepository.findAll();
    }

    @Override
    public Libro buscarLibro(Long id) {
        return libroRepository.findById(id).orElse(null);
    }


    @Override
    public Libro buscarLibro(Long id) {
        return libroRepository.findById(id).orElse(null);
    }

    @Override
    public void eliminarLibro(Libro libro) {
        libroRepository.delete(libro);
    }
    
}
