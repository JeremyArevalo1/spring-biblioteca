package com.jeremyarevalo.webapp.biblioteca.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.jeremyarevalo.webapp.biblioteca.model.Libro;

public interface LibroRepository extends JpaRepository<Libro, Long>{
    
}
