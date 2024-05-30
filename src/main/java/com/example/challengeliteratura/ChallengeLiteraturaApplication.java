package com.example.challengeliteratura;

import com.example.challengeliteratura.model.Datos;
import com.example.challengeliteratura.model.DatosLibro;
import com.example.challengeliteratura.principal.Principal;
import com.example.challengeliteratura.repository.AutorRepository;
import com.example.challengeliteratura.repository.LibroRepository;
import com.example.challengeliteratura.service.ConsumoAPI;
import com.example.challengeliteratura.service.ConvierteDatos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.InputMismatchException;

@SpringBootApplication
public class ChallengeLiteraturaApplication implements CommandLineRunner {

	@Autowired
	private LibroRepository libroRepository;
	@Autowired
	private AutorRepository autorRepository;
	public static void main(String[] args) {
		SpringApplication.run(ChallengeLiteraturaApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Principal principal = new Principal(libroRepository, autorRepository);
		principal.muestraOpciones();

	}
}
