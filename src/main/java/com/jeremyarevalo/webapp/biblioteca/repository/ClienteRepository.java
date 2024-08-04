package com.jeremyarevalo.webapp.biblioteca.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.jeremyarevalo.webapp.biblioteca.model.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long>{

}
