package com.jeremyarevalo.webapp.biblioteca.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.jeremyarevalo.webapp.biblioteca.model.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Long>{
    
}
