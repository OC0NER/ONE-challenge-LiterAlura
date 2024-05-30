package com.example.challengeliteratura.repository;

import com.example.challengeliteratura.model.Autor;
import com.example.challengeliteratura.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface LibroRepository extends JpaRepository<Libro, Long> {
    List<Libro> findAllByOrderByTituloAsc();
    Optional<Libro> findByTitulo(String titulo);

    @Query("SELECT l FROM Autor a JOIN a.libros l WHERE a.Id = :id")
    List<Libro> obtenerLibrosPorAutor(Long id);

//    @Query("SELECT DISTINCT l.idiomas from Libro l ORDER BY l.idiomas")
    @Query("SELECT DISTINCT i FROM Libro l JOIN l.idiomas i")
    List<String> obtenerListaIdiomas();

}
