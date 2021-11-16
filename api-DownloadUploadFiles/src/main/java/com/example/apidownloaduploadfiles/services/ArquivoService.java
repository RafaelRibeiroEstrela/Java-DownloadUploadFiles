package com.example.apidownloaduploadfiles.services;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.apidownloaduploadfiles.models.Arquivo;
import com.example.apidownloaduploadfiles.repositories.ArquivoRepository;

@Service
public class ArquivoService {

	@Autowired
	private ArquivoRepository arquivoRepository;

	private final String DIRETORIO_GERAL = "C:\\git\\Java-DownloadUploadFiles\\arquivos\\";

	public Arquivo upload(MultipartFile file) {
		Arquivo arquivo = new Arquivo();
		arquivo.setId(null);
		arquivo.setNome(file.getOriginalFilename());
		arquivo.setTipo(file.getContentType());
		arquivo.setDiretorio(DIRETORIO_GERAL + file.getOriginalFilename());
		saveFile(file);
		return arquivoRepository.save(arquivo);
	}

	public MultipartFile download(Long id) {
		Arquivo arquivo = arquivoRepository.findById(id)
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

	private void saveFile(MultipartFile file) {
		String diretorio = DIRETORIO_GERAL + file.getOriginalFilename();
		File localFile = new File(diretorio);
		try {
			localFile.createNewFile();
			FileOutputStream fos = new FileOutputStream(localFile);
			fos.write(file.getBytes());
			fos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
