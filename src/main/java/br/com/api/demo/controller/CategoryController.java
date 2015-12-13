package br.com.api.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.api.demo.model.Category;
import br.com.api.demo.repository.CategoryRepository;

/**
 * Created by root on 10/12/15.
 */
@RestController
@RequestMapping(value="category", produces = MediaType.APPLICATION_JSON_VALUE)
public class CategoryController {
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@RequestMapping(method=RequestMethod.GET)
	public List<Category> findAll(){
		return this.categoryRepository.findAll();
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public Category findOne(@PathVariable(value="id") Long id){
		return this.categoryRepository.findOne(id);
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public Category create( @RequestBody Category category ){		
		return this.categoryRepository.save(category);
	}
	
	@RequestMapping(value="/{id}", method={RequestMethod.PUT, RequestMethod.PATCH})
	public Category update(@PathVariable(value="id") Long id, @RequestBody Category category ){
		return this.categoryRepository.save(category);
	}
	
	@RequestMapping(value="/{id}", method=RequestMethod.DELETE)
	public void delete(@PathVariable(value="id") Long id ){
		Category category = this.categoryRepository.findOne(id);
		if ( category != null ){
			this.categoryRepository.delete(category);
		}
	}
}