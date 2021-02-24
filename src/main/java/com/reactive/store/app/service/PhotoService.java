package com.reactive.store.app.service;

import java.io.IOException;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.reactive.store.app.repository.FileRepository;
import com.reactive.store.app.util.LoggingUtils;

import lombok.extern.apachecommons.CommonsLog;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@CommonsLog
public class PhotoService {
	private final FileRepository fileRepository;
	private final String photosPath;

	public PhotoService(FileRepository fileRepository) {
		this.fileRepository = fileRepository;
		this.photosPath = System.getProperty("user.home") + "/app/products/images/";
	}

	public void uploadPhoto(MultipartFile file) throws Exception {
		log.info(LoggingUtils.getMessage(file));
		fileRepository.saveFile(file, photosPath);
	}

	public Flux<byte[]> getPhoto(String photoName) throws IOException {
		log.info(LoggingUtils.getMessage(photoName));
		byte[] result = fileRepository.getFile(photosPath, photoName);
		log.info(LoggingUtils.getMessage(result));
		return Flux.just(result);
	}

	public Mono<Boolean> removePhoto(String photoName) throws IOException {
		log.info(LoggingUtils.getMessage(photoName));
		boolean result = fileRepository.removeFile(photosPath, photoName);
		log.info(LoggingUtils.getMessage(result));
		return Mono.just(result);
	}

}
