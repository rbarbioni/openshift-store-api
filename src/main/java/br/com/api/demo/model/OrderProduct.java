package br.com.api.demo.model;

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
public class OrderProduct extends DomainEntity {

	private static final long serialVersionUID = 5520653547891285250L;

	@ManyToOne
	@JoinColumn ( name = "product_id" )
	@Fetch ( FetchMode.JOIN )
	private Product product;
	
	@ManyToOne
	@JoinColumn ( name = "order_id" )
	@Fetch ( FetchMode.JOIN )
	private CustomerOrder order;

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public CustomerOrder getOrder() {
		return order;
	}

	public void setOrder(CustomerOrder order) {
		this.order = order;
	}
}