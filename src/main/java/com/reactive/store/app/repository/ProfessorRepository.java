package com.reactive.store.app.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import com.reactive.store.app.model.Professor;

@Repository
public interface ProfessorRepository extends ReactiveMongoRepository<Professor, String> {

}
