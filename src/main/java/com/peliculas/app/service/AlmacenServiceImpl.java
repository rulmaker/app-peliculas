package com.peliculas.app.service;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.Resource;

import com.peliculas.app.exeptions.AlmacenException;
import com.peliculas.app.exeptions.FileNotFoundException;

@Service
public class AlmacenServiceImpl implements AlmacenServicio {
	
	@Value("${storage.location}")
	private String storageLocation;
	//indica locación a donde se subirán archivos
	
	
	//indica que se va a ejecutar cada vez que haya una nueva instancia de esta clase
	@PostConstruct
	@Override
	public void iniciarAlmacenDeArchivos() {
		try {
			Files.createDirectories(Paths.get(storageLocation));
			//creamos el directorio, con el nombre que le dimos en .properties
		}catch(IOException exception) {
			throw new AlmacenException("Error al inicializar la ubicación en el almacén de archivos");
		}
	}
	

	@Override
	public String almacenarArchivo(MultipartFile archivo) {
		String nombreArchivo = archivo.getOriginalFilename();
		
		if(archivo.isEmpty()) {
			throw new AlmacenException("No se puede almacenar un archivo vacío");
		}
		
		try {
			InputStream inputStream = archivo.getInputStream();
			Files.copy(inputStream, Paths.get(storageLocation).resolve(nombreArchivo), StandardCopyOption.REPLACE_EXISTING);
			//inputStream recibe lo que se está escribiendo
			//standardCopy indica que si hay algún archivo con el mismo nombre, lo va a reemplazar
		}catch(IOException exception) {
			throw new AlmacenException("Error al almacenar el archivo " + nombreArchivo, exception);
		}
		
		return nombreArchivo;
	}
	

	@Override
	public Path cargarArchivo(String nombreArchivo) {
		return Paths.get(storageLocation).resolve(nombreArchivo);
	}
	

	@Override
	public Resource cargarComoRecurso(String nombreArchivo) {
		try {
			Path archivo = cargarArchivo(nombreArchivo);
			Resource recurso = new UrlResource(archivo.toUri());
			
			if(recurso.exists() || recurso.isReadable()) {
				return recurso;
			}else {
				throw new FileNotFoundException("No se pudo encontrar el archivo " + nombreArchivo);
			}
			
		} catch (MalformedURLException exception) {
			throw new FileNotFoundException("No se pudo encontrar el archivo " + nombreArchivo, exception);
		}
	}

	@Override
	public void eliminarArchivo(String nombreArchivo) {
		Path archivo = cargarArchivo(nombreArchivo);
		
		try {
			FileSystemUtils.deleteRecursively(archivo);
		} catch (Exception exception) {
			System.out.println(exception);
		}
		
	}

}
