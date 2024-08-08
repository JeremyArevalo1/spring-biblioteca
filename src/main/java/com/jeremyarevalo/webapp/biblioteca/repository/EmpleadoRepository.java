package com.jeremyarevalo.webapp.biblioteca.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.jeremyarevalo.webapp.biblioteca.model.Empleado;

public interface EmpleadoRepository extends JpaRepository<Empleado, Long>{

}
