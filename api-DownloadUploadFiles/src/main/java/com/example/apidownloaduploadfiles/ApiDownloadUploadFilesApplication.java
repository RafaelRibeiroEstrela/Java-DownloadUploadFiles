package com.example.apidownloaduploadfiles;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.apidownloaduploadfiles.config.AppInfo;

@SpringBootApplication
public class ApiDownloadUploadFilesApplication implements CommandLineRunner{

	public static void main(String[] args) {
		SpringApplication.run(ApiDownloadUploadFilesApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println(AppInfo.getCurrentWorkingDirectory());
	}

}
