package com.reactive.store.app.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.reactive.store.app.service.PhotoService;
import com.reactive.store.app.util.LoggingUtils;

import lombok.AllArgsConstructor;
import lombok.extern.apachecommons.CommonsLog;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@AllArgsConstructor
@CommonsLog
@RestController
@RequestMapping("/api/photos")
@CrossOrigin("*")
public class PhotoController {
	private final PhotoService photoService;
	private static final String UPLOAD_DIRECTORY = System.getProperty("user.home") + "/app/products/images/";

	@PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	@ResponseStatus(HttpStatus.CREATED)
	public Mono<Void> upload(@RequestPart("file") final FilePart filePart) {
		log.info(LoggingUtils.getStartMessage());
		final File directory = new File(UPLOAD_DIRECTORY);
		if (!directory.exists()) {
			directory.mkdirs();
		}
		final File file = new File(directory, filePart.filename());
		Mono<Void> result = filePart.transferTo(file);
		log.info(LoggingUtils.getEndMessage());
		return result;
	}

	@PostMapping(value = "/multi-upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	@ResponseStatus(HttpStatus.CREATED)
	public Mono<String> uploadMultiple(@RequestPart("files") Flux<FilePart> filePartFlux) {
		log.info(LoggingUtils.getStartMessage());
		final File directory = new File(UPLOAD_DIRECTORY);
		if (!directory.exists()) {
			directory.mkdirs();
		}

		Mono<String> result = filePartFlux
				.flatMap(filePart -> filePart.transferTo(new File(directory, filePart.filename())))
				.then(Mono.just("OK"));
		log.info(LoggingUtils.getEndMessage());
		return result;
	}

	@GetMapping(value = "/{photoName}", produces = MediaType.IMAGE_PNG_VALUE)
	public Flux<byte[]> getByName(@PathVariable String photoName) throws IOException {
		log.info(LoggingUtils.getStartMessage());
		byte[] imageBytes = Files.readAllBytes(Paths.get(UPLOAD_DIRECTORY + photoName));
		log.info(LoggingUtils.getEndMessage());
		return Flux.just(imageBytes);
	}

	@DeleteMapping(value = "/{photoName}")
	@ResponseStatus(HttpStatus.OK)
	public Mono<String> deleteByName(@PathVariable String photoName) throws IOException {
		log.info(LoggingUtils.getStartMessage());
		final File directory = new File(UPLOAD_DIRECTORY);
		final File file = new File(directory, photoName);
		boolean result = file.delete();
		log.info(LoggingUtils.getEndMessage());
		return Mono.just(result ? "OK" : "FAILD");
	}

}
