package com.reactive.store.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.reactive.store.app.model.Professor;
import com.reactive.store.app.repository.ProfessorRepository;
import com.reactive.store.app.util.LoggingUtils;

import lombok.extern.apachecommons.CommonsLog;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@CommonsLog
public class ProfessorService {
	@Autowired
	private ProfessorRepository professorRepository;

	public Mono<Professor> getProfessorById(String id) {
		log.info(LoggingUtils.getMessage(id));
		Mono<Professor> result = professorRepository.findById(id);
		return result;
	}

	public Flux<Professor> getAllProfessors() {
		Flux<Professor> result = professorRepository.findAll();
		return result;
	}

	public Mono<Professor> createProfessor(Professor professor) {
		log.info(LoggingUtils.getMessage(professor));
		Mono<Professor> result = professorRepository.save(professor);
		return result;
	}

	public Mono<Professor> updateProfessor(String id, Professor professor) {
		log.info(LoggingUtils.getMessage(id));
		log.info(LoggingUtils.getMessage(professor));
		Mono<Professor> result = professorRepository.findById(id).flatMap(existingProfessor -> {
			existingProfessor.setFirstName(professor.getFirstName());
			existingProfessor.setLastName(professor.getLastName());
			existingProfessor.setPhoneNumber(professor.getPhoneNumber());
			existingProfessor.setCity(professor.getCity());
			existingProfessor.setAddress(professor.getAddress());
			existingProfessor.setSpecialty(professor.getSpecialty());
			existingProfessor.setAboutMe(professor.getAboutMe());
			return professorRepository.save(existingProfessor);
		});
		log.info(LoggingUtils.getMessage(result.block()));
		return result;

	}

	public Mono<Void> deleteProfessorById(String id) {
		log.info(LoggingUtils.getMessage(id));
		Mono<Void> result = professorRepository.findById(id)
				.flatMap(existingProfessor -> professorRepository.delete(existingProfessor));
		log.info(LoggingUtils.getMessage(result));
		return result;
	}
}
