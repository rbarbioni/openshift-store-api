package br.com.api.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.api.demo.model.Product;
import br.com.api.demo.repository.ProductRepository;

/**
 * Created by root on 10/12/15.
 */
@RestController
@RequestMapping(value="products", produces = MediaType.APPLICATION_JSON_VALUE)
public class ProductController {
	
	@Autowired
	private ProductRepository productRepository;
	
	@RequestMapping(method=RequestMethod.GET)
	public List<Product> findAll(){
		return this.productRepository.findAll();
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public Product findOne(@PathVariable(value="id") Long id){
		return this.productRepository.findOne(id);
	}
	
}
