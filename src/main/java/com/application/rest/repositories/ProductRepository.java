package com.application.rest.repositories;

import com.application.rest.entities.Product;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ProductRepository extends CrudRepository<Product, Long> {

    @Query("SELECT p FROM Product p WHERE p.price >= ?1 AND p.price <= ?2")
    List<Product> findProductByPriceInRange(BigDecimal minPrice, BigDecimal maxPrice); // Query

    List<Product> findProductByPriceBetween(BigDecimal minPrice, BigDecimal maxPrice); // Query methods
}
