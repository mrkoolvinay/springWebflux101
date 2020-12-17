package com.coffeebrain.productionapicontroller.repository;

import com.coffeebrain.productionapicontroller.model.Product;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends ReactiveMongoRepository<Product, String> {
}
