package br.com.api.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.api.demo.model.Product;

/**
 * Created by root on 10/12/15.
 */
public interface ProductRepository extends JpaRepository<Product, Long> {

}
