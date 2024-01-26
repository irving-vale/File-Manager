package com.joirv.filemanager.service;

import com.joirv.filemanager.entity.FileEntity;
import com.joirv.filemanager.response.ResponseFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface FileService {
    // permite almacenar o cargar archivos a la base de datos
    FileEntity almacenar(MultipartFile file) throws IOException;

    // permite descargar archivos de la base de datos
    Optional<FileEntity> descargar(UUID id) throws FileNotFoundException;

    // permite consultar la lista de archivos cargados a nuestra base de datos

    List<ResponseFile> getAllFies();
}
