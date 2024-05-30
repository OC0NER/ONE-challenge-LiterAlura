package com.example.challengeliteratura.repository;

import com.example.challengeliteratura.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AutorRepository extends JpaRepository<Autor, Long> {
    List<Autor> findAllByOrderByNombreAsc();
    Optional<Autor> findByNombre(String nombre);

    @Query("SELECT a FROM Autor a WHERE a.nacimiento < :anio AND a.fallecimiento > :anio ORDER BY a.nombre ASC")
    List<Autor> obtenerAutoresPorAnio(int anio);
}
