package com.coffeebrain.productionapicontroller;

import com.coffeebrain.productionapicontroller.model.Product;
import com.coffeebrain.productionapicontroller.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import reactor.core.publisher.Flux;

@SpringBootApplication
public class ProductionApiControllerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProductionApiControllerApplication.class, args);
	}

	@Bean
	CommandLineRunner init(ProductRepository repo) {
		return args -> {
			Flux<Product> productFlux = Flux.just(
					new Product(null, "Big Latte", 2.99),
					new Product(null, "Big Decaf", 2.49),
					new Product(null, "Green Tea", 1.99))
					.flatMap(repo::save);

			productFlux.thenMany(repo.findAll())
			.subscribe(System.out::println);
		};
	}
}
