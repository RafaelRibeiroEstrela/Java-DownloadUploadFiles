package com.example.apidownloaduploadfiles.services;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.apidownloaduploadfiles.models.Archive;
import com.example.apidownloaduploadfiles.models.dto.ArchiveDownloadDTO;
import com.example.apidownloaduploadfiles.repositories.ArchiveRepository;

@Service
public class ArchiveService {

	@Autowired
	private ArchiveRepository archiveRepository;

	private final String PATH_OF_FILES = System.getProperty("user.dir") + File.separator + "files";

	public void save(MultipartFile[] files) throws IOException {
		for (MultipartFile file : files) {
			Archive archive = new Archive();
			archive.setId(null);
			archive.setName(file.getOriginalFilename());
			archive.setContentType(file.getContentType());
			saveFile(file);
			archive.setPath(PATH_OF_FILES + File.separator + file.getOriginalFilename());
			archiveRepository.save(archive);
		}
	}

	private void saveFile(MultipartFile file) throws IOException {

		// Obtem o diretorio
		File localFile = new File(PATH_OF_FILES);

		// Verifica se o diretório não existe
		if (!localFile.exists()) {

			// Cria o diretorio
			localFile.mkdirs();
		}

		// Obtem o diretório completo
		FileOutputStream fos = new FileOutputStream(PATH_OF_FILES + File.separator + file.getOriginalFilename());

		// Salva o arquivo no diretório
		fos.write(file.getBytes());
		fos.close();
	}

	public ArchiveDownloadDTO findById(Long id) throws IOException {

		// Encontra a referencia do arquivo no banco de dados
		Archive archive = archiveRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Object not found by id = " + id));

		// Armazena o diretorio na variavel Path
		Path path = Paths.get(archive.getPath());

		// Le e armazena os bytes do arquivo
		byte[] content = Files.readAllBytes(path);

		// Le e armazena o diretorio para conversao
		File file = new File(archive.getPath());

		// Converte o array de bytes em arquivo
		FileUtils.writeByteArrayToFile(file, content);

		// Salva o arquivo em um InputStreamResource
		InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

		// Salva os atributos na classe para usar no pacote resource
		ArchiveDownloadDTO archiveDownload = new ArchiveDownloadDTO();
		archiveDownload.setName(archive.getName());
		archiveDownload.setContentType(archive.getContentType());
		archiveDownload.setFile(file);
		archiveDownload.setResource(resource);

		return archiveDownload;
	}

	public ArchiveDownloadDTO findAll() throws IOException {

		// Encontra a referencia do arquivo no banco de dados
		List<Archive> archives = archiveRepository.findAll();
		
		// Adiciona os arquivos em Zip
		File file = addToZip(archives);

		// Armazena o diretorio na variavel Path
		Path path = Paths.get(file.getAbsolutePath());

		// Le e armazena os bytes do arquivo
		byte[] content = Files.readAllBytes(path);

		// Le e armazena o diretorio para conversao
		file = new File(file.getAbsolutePath());

		// Converte o array de bytes em arquivo
		FileUtils.writeByteArrayToFile(file, content);

		// Salva o arquivo em um InputStreamResource
		InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

		// Salva os atributos na classe para usar no pacote resource
		ArchiveDownloadDTO archiveDownload = new ArchiveDownloadDTO();
		archiveDownload.setName("temp.zip");
		archiveDownload.setContentType("file/zip");
		archiveDownload.setFile(file);
		archiveDownload.setResource(resource);

		return archiveDownload;
	}

	private File addToZip(List<Archive> archives) throws IOException {
		
		// Criando o arquivo Zip
		File file = new File(PATH_OF_FILES + File.separator + "temp.zip");
		
		ZipOutputStream out = new ZipOutputStream(new FileOutputStream(file));
		
		for (Archive archive : archives) {
			
			// Obtendo o diretorio do arquivo
			Path path = Paths.get(archive.getPath());
			
			// Obtendo os bytes do arquivo
			byte[] content = Files.readAllBytes(path);
			
			// Obtendo arquivo para salvar no Zip
			ZipEntry e = new ZipEntry(archive.getName());
			
			out.putNextEntry(e);
			
			out.write(content, 0, content.length);
			
			out.closeEntry();
		}
		out.close();
		return file;
	}
}
