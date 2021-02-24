package com.reactive.store.app.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.reactive.store.app.model.Product;
import com.reactive.store.app.repository.ProductRepository;
import com.reactive.store.app.service.ProductService;
import com.reactive.store.app.util.LoggingUtils;

import lombok.AllArgsConstructor;
import lombok.extern.apachecommons.CommonsLog;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@CommonsLog
@AllArgsConstructor
@RestController
@RequestMapping("/api/products")
public class ProductController {
	private final ProductRepository repository;
	private final ProductService productService;

	@GetMapping
	public Flux<Product> getAll() {
		log.info(LoggingUtils.getStartMessage());
		Flux<Product> result = productService.getAllProducts();
		log.info(LoggingUtils.getEndMessage());
		return result;
	}

	@GetMapping("/find")
	public Flux<Product> getAllByName(@RequestParam("search") String name) {
		log.info(LoggingUtils.getStartMessage());
		Flux<Product> result = productService.findProductsByName(name);
		log.info(LoggingUtils.getEndMessage());
		return result;
	}

	@GetMapping("{id}")
	public Mono<ResponseEntity<Product>> getById(@PathVariable String id) {
		log.info(LoggingUtils.getStartMessage());
		Mono<Product> result = productService.getProductById(id);
		log.info(LoggingUtils.getEndMessage());
		return result.map(ResponseEntity::ok).defaultIfEmpty(ResponseEntity.notFound().build());
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Mono<Product> create(@RequestBody Product product) {
		log.info(LoggingUtils.getStartMessage());
		Mono<Product> result = productService.createProduct(product);
		log.info(LoggingUtils.getEndMessage());
		return result;
	}

	@PutMapping("{id}")
	public Mono<ResponseEntity<Product>> update(@PathVariable String id, @RequestBody Product product) {
		log.info(LoggingUtils.getStartMessage());
		Mono<Product> result = productService.updateProduct(id, product);
		log.info(LoggingUtils.getEndMessage());
		return result.map(ResponseEntity::ok).defaultIfEmpty(ResponseEntity.notFound().build());
	}

	@DeleteMapping("{id}")
	public Mono<ResponseEntity<Void>> delete(@PathVariable(value = "id") String id) {
		log.info(LoggingUtils.getStartMessage());
		Mono<Void> result = productService.deleteProductById(id);
		log.info(LoggingUtils.getEndMessage());
		return result.then(Mono.just(ResponseEntity.ok().<Void>build()))
				.defaultIfEmpty(ResponseEntity.notFound().build());
	}
}
