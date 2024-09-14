package com.jeremyarevalo.webapp.biblioteca.model;

import java.sql.Date;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "Prestamos")
public class Prestamo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date fechaDePrestamo;
    private Date fechaDeDevolucion;
    private Boolean vigencia;
    @ManyToOne
    private Empleado empleado;
    @ManyToOne
    private Cliente cliente;
    @ManyToMany
    @JoinTable(name = "pretamos_libros",
    joinColumns = @JoinColumn(name = "prestamo_id", referencedColumnName = "id"),
    inverseJoinColumns = @JoinColumn(name = "libro_id", referencedColumnName = "id"))
    private List<Libro> libro;


    public void setLibro(List<Libro> libro) {
        this.libro = libro;
    }


}
