package br.com.api.demo.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Created by root on 10/12/15.
 */
@SuppressWarnings("unused")
@Entity
public class Product {
	
	private static final long serialVersionUID = -2142641923557789412L;
	
	@Id
	@GeneratedValue ( strategy = GenerationType.AUTO )
	@Column ( name = "id", nullable=false )
	private Long id;
	
    @Column(name = "uuid", nullable = false, updatable = false)
    private String uuid = java.util.UUID.randomUUID().toString();	
    
	@Column ( name = "creation_date", nullable = false )
    private Date creationDate = new Date();
	
	@Column ( name = "active", nullable=false )
    private boolean active = true;
	
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}
}
