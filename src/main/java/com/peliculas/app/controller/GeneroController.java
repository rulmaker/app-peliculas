package com.peliculas.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.peliculas.app.model.Genero;
import com.peliculas.app.service.GeneroServiceImpl;

@RestController
@RequestMapping("/genero")
public class GeneroController {

	@Autowired
	private GeneroServiceImpl service;
	
	@PostMapping
	public Genero saveGenero(@RequestBody Genero genero) {
		return service.saveGenero(genero);
	}
	
}
