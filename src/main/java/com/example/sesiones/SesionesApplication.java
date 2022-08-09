package com.example.sesiones;

import com.example.sesiones.Repository.LaptopRepository;
import com.example.sesiones.entities.Laptop;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class SesionesApplication {

	public static void main(String[] args) {
		
		ApplicationContext context = SpringApplication.run(SesionesApplication.class, args);
		LaptopRepository repository = context.getBean(LaptopRepository.class);
		
		// crear objetos de tipo laptop
		Laptop laptop1 = new Laptop(null,"Dell",1200.50,"portatil dell coreI7");
		Laptop laptop2 = new Laptop(null,"Hp",1500.50,"portatil hp coreI7");

		//guardar en BD
		repository.save(laptop1);
		repository.save(laptop2);

		System.out.println("Num objetos en base de datos: " + repository.findAll().size());
	}

}
