package com.devsuperior.salesquery.sale.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_seller")
public class Seller {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long sellerId;

	@SuppressWarnings("unused")
	private String name;

	@SuppressWarnings("unused")
	private String email;

	@SuppressWarnings("unused")
	private String phone;

	@OneToMany(mappedBy = "seller")
	private List<Sale> sales = new ArrayList<>();

	protected Seller() {
	}

}
