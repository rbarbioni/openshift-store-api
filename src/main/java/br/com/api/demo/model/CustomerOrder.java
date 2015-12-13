package br.com.api.demo.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import br.com.rbarbioni.application.core.models.DomainEntity;

/**
 * Created by root on 10/12/15.
 */
@Entity
public class CustomerOrder extends DomainEntity {

	private static final long serialVersionUID = 6716508463715294167L;

	@ManyToOne
	@JoinColumn ( name = "customer_id" )
	@Fetch ( FetchMode.JOIN )
	private Customer customer;
	
	@OneToMany(mappedBy="product")
	private List< OrderProduct > products;

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public List<OrderProduct> getProducts() {
		return products;
	}

	public void setProducts(List<OrderProduct> products) {
		this.products = products;
	}
}