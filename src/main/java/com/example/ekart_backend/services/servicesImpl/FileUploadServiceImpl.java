package com.example.ekart_backend.services.servicesImpl;

import com.example.ekart_backend.services.serviceInterface.FileUploadService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class FileUploadServiceImpl implements FileUploadService {
    @Override
    public String uploadImage(String path, MultipartFile file) throws IOException {

        String imageName = file.getOriginalFilename();

        String randomId = UUID.randomUUID().toString();

        String newFileName = randomId.concat(imageName.substring(imageName.lastIndexOf(".")));

        String filePath = path + newFileName;

        System.out.println("The full path of the file is : " + filePath);

        File f = new File(path);

        if(!f.exists()){
            System.out.printf("Creating directory..");
            f.mkdir();
        }

        Files.copy(file.getInputStream(), Paths.get(filePath), StandardCopyOption.REPLACE_EXISTING);

        return newFileName;
    }

    @Override
    public InputStream getImage(String path, String fileName) throws FileNotFoundException {
        String fullPath = path + fileName;
        return new FileInputStream(fullPath);
    }
}
