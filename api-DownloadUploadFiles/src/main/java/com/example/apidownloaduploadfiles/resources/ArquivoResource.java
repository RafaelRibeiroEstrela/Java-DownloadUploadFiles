package com.example.apidownloaduploadfiles.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.apidownloaduploadfiles.models.Arquivo;
import com.example.apidownloaduploadfiles.services.ArquivoService;

@RestController
@RequestMapping(value = "/files")
public class ArquivoResource {

	@Autowired
	private ArquivoService arquivoService;
	
	@PostMapping(value = "/upload")
	public ResponseEntity<Arquivo> upload(@RequestParam("File") MultipartFile file){
		return ResponseEntity.status(HttpStatus.CREATED).body(arquivoService.upload(file));
	}
	
	@GetMapping(value = "/download/{id}")
	public ResponseEntity<MultipartFile> download(@PathVariable Long id){
		return ResponseEntity.status(HttpStatus.OK).body(arquivoService.download(id));
	}
	
	
	
}