package com.reactive.store.app.service;

import org.springframework.stereotype.Service;

import com.reactive.store.app.model.Product;
import com.reactive.store.app.repository.ProductRepository;
import com.reactive.store.app.util.LoggingUtils;

import lombok.AllArgsConstructor;
import lombok.extern.apachecommons.CommonsLog;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
@CommonsLog
public class ProductService {

	private final ProductRepository productRepository;

	public Mono<Product> getProductById(String id) {
		log.info(LoggingUtils.getMessage(id));
		Mono<Product> result = productRepository.findById(id);
		log.info(LoggingUtils.getMessage(result));
		return result;
	}

	public Flux<Product> getAllProducts() {
		Flux<Product> result = productRepository.findAll();
		log.info(LoggingUtils.getMessage(result));
		return result;
	}

	public Flux<Product> findProductsByName(String name) {
		Flux<Product> result = productRepository.findByName(name);
		log.info(LoggingUtils.getMessage(result));
		return result;
	}

	public Mono<Product> createProduct(Product p) {
		log.info(LoggingUtils.getMessage(p));
		Mono<Product> result = productRepository.save(p);
		log.info(LoggingUtils.getMessage(result));
		return result;
	}

	public Mono<Product> updateProduct(String id, Product product) {
		log.info(LoggingUtils.getMessage(id));
		log.info(LoggingUtils.getMessage(product));
		Mono<Product> result = productRepository.findById(id).flatMap(existingProduct -> {
			existingProduct.setName(product.getName());
			return productRepository.save(existingProduct);
		});
		log.info(LoggingUtils.getMessage(result.block()));
		return result;
	}

	public Mono<Void> deleteProductById(String id) {
		log.info(LoggingUtils.getMessage(id));
		Mono<Void> result = productRepository.findById(id)
				.flatMap(existingProduct -> productRepository.delete(existingProduct));
		log.info(LoggingUtils.getMessage(result));
		return result;
	}
}
