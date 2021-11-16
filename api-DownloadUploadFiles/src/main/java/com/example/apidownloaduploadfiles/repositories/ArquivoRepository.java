package com.example.apidownloaduploadfiles.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.apidownloaduploadfiles.models.Arquivo;

public interface ArquivoRepository extends JpaRepository<Arquivo, Long>{

}
