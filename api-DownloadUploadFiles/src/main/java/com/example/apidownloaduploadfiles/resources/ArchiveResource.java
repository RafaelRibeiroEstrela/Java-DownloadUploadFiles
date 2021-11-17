package com.example.apidownloaduploadfiles.resources;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.apidownloaduploadfiles.models.dto.ArchiveDownloadDTO;
import com.example.apidownloaduploadfiles.services.ArchiveService;

@RestController
@RequestMapping(value = "/files")
public class ArchiveResource {

	@Autowired
	private ArchiveService archiveService;
	
	@PostMapping(value = "/upload")
	public ResponseEntity<String> save(@RequestParam("File") MultipartFile[] file) throws IOException{
		archiveService.save(file);
		return ResponseEntity.status(HttpStatus.CREATED).body("Arquivos salvos com sucesso");
	}
	
	
	@GetMapping(value = "/download/{id}")
	public ResponseEntity<InputStreamResource> findById(@PathVariable Long id) throws IOException{
		ArchiveDownloadDTO archiveDownload = archiveService.findById(id);
		return ResponseEntity.status(HttpStatus.OK)
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + archiveDownload.getName())
				.contentType(MediaType.parseMediaType(archiveDownload.getContentType()))
				.contentLength(archiveDownload.getFile().length())
				.body(archiveDownload.getResource());
	}
	
	@GetMapping(value = "/download")
	public ResponseEntity<InputStreamResource> findAll() throws IOException{
		ArchiveDownloadDTO archiveDownload = archiveService.findAll();
		return ResponseEntity.status(HttpStatus.OK)
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + archiveDownload.getName())
				.contentType(MediaType.parseMediaType(archiveDownload.getContentType()))
				.contentLength(archiveDownload.getFile().length())
				.body(archiveDownload.getResource());
	}
	
}
