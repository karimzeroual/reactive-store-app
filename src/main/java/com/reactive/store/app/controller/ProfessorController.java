package com.reactive.store.app.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.reactive.store.app.model.Professor;
import com.reactive.store.app.service.ProfessorService;
import com.reactive.store.app.util.LoggingUtils;

import lombok.AllArgsConstructor;
import lombok.extern.apachecommons.CommonsLog;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@CrossOrigin("*")
@AllArgsConstructor
@CommonsLog
@RequestMapping("/api/professors")
public class ProfessorController {
	private final ProfessorService professorService;

	@GetMapping
	public Flux<Professor> getAll() {
		log.info(LoggingUtils.getStartMessage());
		Flux<Professor> result = professorService.getAllProfessors();
		log.info(LoggingUtils.getEndMessage());
		return result;
	}

	@GetMapping("{id}")
	public Mono<ResponseEntity<Professor>> getById(@PathVariable String id) {
		log.info(LoggingUtils.getStartMessage());
		Mono<Professor> result = professorService.getProfessorById(id);
		log.info(LoggingUtils.getEndMessage());
		return result.map(ResponseEntity::ok).defaultIfEmpty(ResponseEntity.notFound().build());
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Mono<Professor> create(@RequestBody Professor professor) {
		log.info(LoggingUtils.getStartMessage());
		Mono<Professor> result = professorService.createProfessor(professor);
		log.info(LoggingUtils.getEndMessage());
		return result;
	}

	@PutMapping("{id}")
	public Mono<ResponseEntity<Professor>> update(@PathVariable String id, @RequestBody Professor professor) {
		log.info(LoggingUtils.getStartMessage());
		Mono<Professor> result = professorService.updateProfessor(id, professor);
		log.info(LoggingUtils.getEndMessage());
		return result.map(ResponseEntity::ok).defaultIfEmpty(ResponseEntity.notFound().build());
	}

	@DeleteMapping("{id}")
	public Mono<ResponseEntity<Void>> delete(@PathVariable(value = "id") String id) {
		log.info(LoggingUtils.getStartMessage());
		Mono<Void> result = professorService.deleteProfessorById(id);
		log.info(LoggingUtils.getEndMessage());
		return result.then(Mono.just(ResponseEntity.ok().<Void>build()))
				.defaultIfEmpty(ResponseEntity.notFound().build());
	}
}
