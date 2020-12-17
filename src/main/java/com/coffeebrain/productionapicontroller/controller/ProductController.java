package com.coffeebrain.productionapicontroller.controller;

import com.coffeebrain.productionapicontroller.model.Product;
import com.coffeebrain.productionapicontroller.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    ProductRepository repo;

    @GetMapping
    public Flux<Product> getAllProducts() {
        return repo.findAll();
    }

    @GetMapping("{id}")
    public Mono<ResponseEntity<Product>> getProductById(@PathVariable String id) {
        return repo.findById(id).map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Product> saveProduct(@RequestBody Product product) {
        return repo.save(product);
    }

    @PutMapping("{id}")
    public Mono<ResponseEntity<Product>> updateProduct(@RequestBody Product product, @PathVariable String id) {
        return getProductById(id).flatMap(existProd -> {
            Product prd = existProd.getBody();
            if (prd != null) {
                prd.setName(product.getName());
                prd.setPrice(product.getPrice());
                return repo.save(prd);
            }
            return null;
        })
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping("{id}")
    public Mono<ResponseEntity<Void>> deleteProduct(@PathVariable String id) {
        return repo.deleteById(id).then(Mono.just(ResponseEntity.ok().<Void>build()))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping
    public Mono<Void> deleteAll() {
        return repo.deleteAll();
    }
}
