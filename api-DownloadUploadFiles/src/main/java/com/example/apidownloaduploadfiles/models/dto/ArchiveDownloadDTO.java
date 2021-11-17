package com.example.apidownloaduploadfiles.models.dto;

import java.io.File;
import java.io.Serializable;

import org.springframework.core.io.InputStreamResource;

public class ArchiveDownloadDTO implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String name;
	private String contentType;
	private File file;
	private InputStreamResource resource;
	
	public ArchiveDownloadDTO() {
		
	}

	public ArchiveDownloadDTO(String name, String contentType, File file, InputStreamResource resource) {
		super();
		this.name = name;
		this.contentType = contentType;
		this.file = file;
		this.resource = resource;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public InputStreamResource getResource() {
		return resource;
	}

	public void setResource(InputStreamResource resource) {
		this.resource = resource;
	}
	
	

}
