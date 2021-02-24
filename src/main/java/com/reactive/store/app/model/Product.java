package com.reactive.store.app.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "products")
public class Product {
	@Id
	private String id;
	private String name;
	private String description;
	private double currentPrice;
	private boolean promotion;
	private boolean selected;
	private boolean available;
	private String photoName;
	private int quantity = 1;
}
