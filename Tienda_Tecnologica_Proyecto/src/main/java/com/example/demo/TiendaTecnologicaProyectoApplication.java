package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

//Marcar mi clase Principal como aplicación Spring y lo configura automáticamente
@SpringBootApplication
//Le indica a Spring dónde buscar las entidades JPA
@EntityScan(basePackages = { "pe.edu.cibertec.modelos" })
//Habilita el repositorio y dónde encontrarlo
@EnableJpaRepositories(basePackages = { "pe.edu.cibertec.repositorios" })
//Le indica a Spring dónde buscar los controladores
@ComponentScan(basePackages = { "pe.edu.cibertec.controladores" })
public class TiendaTecnologicaProyectoApplication {

	public static void main(String[] args) {
		SpringApplication.run(TiendaTecnologicaProyectoApplication.class, args);
	}

}
