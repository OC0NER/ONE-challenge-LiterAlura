package com.example.challengeliteratura.model;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "autores")
public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long Id;
    @Column(unique = true)
    private String nombre;
    private Integer nacimiento;
    private Integer fallecimiento;
    @ManyToMany(mappedBy = "autores", cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    private Set<Libro> libros = new HashSet<>();

    public Autor(){}

    public Autor(DatosAutor datosAutor) {
        this.nombre = datosAutor.nombre();
        this.nacimiento = datosAutor.nacimiento();
        this.fallecimiento = datosAutor.fallecimiento();
    }

    @Override
    public String toString() {
        return
                "Nombre = '" + nombre + '\'' +
                ", Nacimiento = " + nacimiento +
                ", Fallecimiento = " + fallecimiento +
                ", Libros = " + libros ;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getNacimiento() {
        return nacimiento;
    }

    public void setNacimiento(Integer nacimiento) {
        this.nacimiento = nacimiento;
    }

    public Integer getFallecimiento() {
        return fallecimiento;
    }

    public void setFallecimiento(Integer fallecimiento) {
        this.fallecimiento = fallecimiento;
    }

    public Set<Libro> getLibros() {
        return libros;
    }

    public void setLibros(Libro libro) {
        libros.add(libro);
    }
}
