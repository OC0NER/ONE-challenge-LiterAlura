package com.example.challengeliteratura.model;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "libros")

public class Libro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    @Column(unique = true)
    private String titulo;
    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> idiomas;
    private Integer descargas;
    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinTable(
            name = "libros_autores",
            joinColumns =  @JoinColumn(name = "id_libro", referencedColumnName = "Id"),
            inverseJoinColumns = @JoinColumn(name = "id_autor", referencedColumnName = "Id")
    )
    private Set<Autor> autores = new HashSet<>();

    public Libro() {
    }

    public Libro(DatosLibro datosLibro) {
        this.titulo = datosLibro.titulo();
        this.idiomas = datosLibro.idiomas();
        this.descargas = datosLibro.descargas();
    }

    @Override
    public String toString() {
        return
                "Titulo = '" + titulo + '\'' +
                        ", Autores = " + autores +
                        ", Idiomas = " + idiomas +
                        ", Descargas = " + descargas;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Set<Autor> getAutores() {
        return autores;
    }

    public void setAutores(Autor autor) {
        this.autores.add(autor);
    }

    public List<String> getIdiomas() {
        return idiomas;
    }

    public void setIdioma(List<String> idiomas) {
        this.idiomas = idiomas;
    }

    public Integer getDescargas() {
        return descargas;
    }

    public void setDescargas(Integer descargas) {
        this.descargas = descargas;
    }
}


