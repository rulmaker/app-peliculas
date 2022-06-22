package com.peliculas.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.peliculas.app.model.Genero;

@Repository
public interface GeneroRepository extends JpaRepository<Genero, Integer> {

}
