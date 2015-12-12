package br.com.api.demo.model;

import javax.persistence.Entity;

import br.com.rbarbioni.application.core.models.DomainEntity;

/**
 * Created by root on 10/12/15.
 */
@Entity
public class Product extends DomainEntity {
	
	private static final long serialVersionUID = -2142641923557789412L;
	
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
	
}
