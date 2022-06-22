package com.peliculas.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.peliculas.app.model.Genero;
import com.peliculas.app.repository.GeneroRepository;

@Service
public class GeneroServiceImpl implements GeneroService {
	
	@Autowired
	private GeneroRepository generoRepository;

	@Override
	public Genero saveGenero(Genero genero) {
		
		return generoRepository.save(genero);
	}

}
