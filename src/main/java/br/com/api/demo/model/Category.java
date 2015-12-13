package br.com.api.demo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import br.com.rbarbioni.application.core.models.DomainEntity;

/**
 * Created by root on 10/12/15.
 */
@Entity
public class Category extends DomainEntity {
	
	private static final long serialVersionUID = -2142641923557789412L;
	
	@Column(name="name")
	private String name;
	
	@ManyToOne
	@JoinColumn ( name = "parent_id", nullable = true )
	@Fetch ( FetchMode.JOIN )
	private Category parent;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Category getParent() {
		return parent;
	}

	public void setParent(Category parent) {
		this.parent = parent;
	}
}