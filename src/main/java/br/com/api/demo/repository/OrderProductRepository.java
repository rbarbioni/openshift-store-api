package br.com.api.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.api.demo.model.OrderProduct;

/**
 * Created by root on 10/12/15.
 */
public interface OrderProductRepository extends JpaRepository<OrderProduct, Long> {

}
