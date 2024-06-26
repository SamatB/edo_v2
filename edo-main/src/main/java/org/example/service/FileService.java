package org.example.service;

import org.example.utils.FilePoolType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {

    ResponseEntity<String> saveFile(MultipartFile multipartFile, FilePoolType filetype);
}
