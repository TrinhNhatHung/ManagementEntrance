package com.garagemanagement.api;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping(value = "/history-image")
@MultipartConfig
public class ImageAPI {
	
	@Value("${project.path}")
	private String PATH;
	@GetMapping("/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) {
		
		try {
			String rootFile = PATH + File.separator +"HistoryImage" + File.separator + fileName;
			System.out.println(rootFile);
			Path  path = Paths.get(rootFile);
			Resource resource = new UrlResource(path.toUri());
			if (resource.exists()) {
				return ResponseEntity.ok()
		                .body(resource);
			} else {
				return new ResponseEntity<Resource>(HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Resource>(HttpStatus.NOT_FOUND);
		}
		
	}
}
