package com.example.apidownloaduploadfiles.services;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.apidownloaduploadfiles.config.AppInfo;
import com.example.apidownloaduploadfiles.models.Archive;
import com.example.apidownloaduploadfiles.repositories.ArquivoRepository;

@Service
public class ArquivoService {

	@Autowired
	private ArquivoRepository arquivoRepository;

	private final String PATH_OF_FILES = AppInfo.getCurrentWorkingDirectory() + "\\files\\";

	public Archive upload(MultipartFile file) throws IOException {
		Archive archive = new Archive();
		archive.setId(null);
		archive.setName(file.getOriginalFilename());
		archive.setType(file.getContentType());
		archive.setPath(PATH_OF_FILES + file.getOriginalFilename());
		saveFile(file);
		return arquivoRepository.save(archive);
	}
	
	private void saveFile(MultipartFile file) {
		
		String path = PATH_OF_FILES + file.getOriginalFilename();
		File localFile = new File(path);
		try {
			localFile.createNewFile();
			FileOutputStream fos = new FileOutputStream(localFile);
			fos.write(file.getBytes());
			fos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	
	/*
	public MultipartFile download(Long id) {
		Archive arquivo = arquivoRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Object not found by id = " + id));
		Path path = Paths.get(arquivo.getDiretorio());
		String contentType = "text/plain";
		byte[] content = null;
		try {
			content = Files.readAllBytes(path);
		}
		catch (IOException e){
			e.printStackTrace();
		}
		return new MockMultipartFile("file", arquivo.getNome(), contentType, content);
	}
	*/
	

}
