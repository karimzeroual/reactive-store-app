package com.reactive.store.app.repository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.reactive.store.app.util.LoggingUtils;

import lombok.extern.apachecommons.CommonsLog;

@CommonsLog
@Component
public class FileRepository {

	public void saveFile(MultipartFile file, String toPath) throws Exception {
		log.info(LoggingUtils.getMessage(file));
		log.info(LoggingUtils.getMessage(toPath));
		Files.write(Paths.get(toPath + file.getOriginalFilename()), file.getBytes());
	}

	public byte[] getFile(String fromPath, String fileName) throws IOException {
		log.info(LoggingUtils.getMessage(fromPath));
		log.info(LoggingUtils.getMessage(fileName));
		byte[] result = Files.readAllBytes(Paths.get(fromPath + fileName));
		log.info(LoggingUtils.getMessage(result));
		return result;
	}

	public boolean removeFile(String fromPath, String fileName) throws IOException {
		log.info(LoggingUtils.getMessage(fromPath));
		log.info(LoggingUtils.getMessage(fileName));
		boolean result = Files.deleteIfExists(Paths.get(fromPath + fileName));
		log.info(LoggingUtils.getMessage(result));
		return result;
	}
}
