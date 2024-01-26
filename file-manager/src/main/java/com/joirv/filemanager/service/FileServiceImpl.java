package com.joirv.filemanager.service;

import com.joirv.filemanager.entity.FileEntity;
import com.joirv.filemanager.repository.FileRepository;
import com.joirv.filemanager.response.ResponseFile;
import jakarta.servlet.Servlet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class FileServiceImpl implements FileService {

    @Autowired
    private FileRepository fileRepository;
    @Override
    public FileEntity almacenar(MultipartFile file) throws IOException {
        String fileNAme = StringUtils.cleanPath(file.getOriginalFilename());
        FileEntity fileEntity = FileEntity.builder()
                .name(fileNAme)
                .type(file.getContentType())
                .data(file.getBytes())
                .build();
        return fileRepository.save(fileEntity);
    }

    @Override
    public Optional<FileEntity> descargar(UUID id) throws FileNotFoundException {
        Optional<FileEntity> file = fileRepository.findById(id);
        if (file.isPresent()){
            return file;
        }
        throw new FileNotFoundException();
    }

    @Override
    public List<ResponseFile> getAllFies() {
        List<ResponseFile> files = fileRepository.findAll().stream().map(dbFile ->{
            String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("api/fileManager/files/")
                    .path(dbFile.getId().toString())
                    .toUriString();
                    return ResponseFile.builder()
                            .name(dbFile.getName())
                            .url(fileDownloadUri)
                            .type(dbFile.getType())
                            .size(dbFile.getData().length).build();
                }).collect(Collectors.toList());
        return files;
    }
}
