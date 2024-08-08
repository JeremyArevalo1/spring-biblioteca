package com.jeremyarevalo.webapp.biblioteca.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.jeremyarevalo.webapp.biblioteca.model.Prestamo;

public interface PrestamoRepository extends JpaRepository<Prestamo, Long>{
    
}
