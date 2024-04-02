package com.example.ekart_backend.services.serviceInterface;

import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public interface FileUploadService {

    public String uploadImage(String path, MultipartFile file) throws IOException;

    public InputStream getImage(String path, String fileName) throws FileNotFoundException;
}
