package com.questv.api.file;

import com.questv.api.contracts.DTO;
import com.questv.api.file.exception.FileStorageException;
import com.questv.api.file.exception.QtvFileNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileStorageServiceImpl implements FileStorageService {

  private final Path path;

  @Autowired
  public FileStorageServiceImpl(final FileStorageProperties fileStorageProperties) {
    this.path = Paths.get(fileStorageProperties.getUploadDirectory()).toAbsolutePath().normalize();
    createDirectory();
  }

  private void createDirectory() {
    try {
      Files.createDirectory(this.path);
    } catch (final Exception exception) {
      throw new RuntimeException(exception);
    }

  }

  @Override
  public String store(final MultipartFile file, final DTO dto) {
    String fileName = StringUtils
        .cleanPath(file.getOriginalFilename());

    fileName = "cover_image_of_id_".concat(String.valueOf(dto.getId())).concat("_").concat(fileName);


    try {
      if (fileName.contains("..")) {
        throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
      }

      Path targetLocation = this.path.resolve(fileName);
      Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

      return fileName;
    } catch (IOException ex) {
      throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
    }

  }

  @Override
  public Resource loadAsResource(final String fileName) {
    try {
      Path filePath = this.path.resolve(fileName).normalize();
      Resource resource = new UrlResource(filePath.toUri());
      if (resource.exists()) {
        return resource;
      } else {
        throw new QtvFileNotFoundException("File not found " + fileName);
      }
    } catch (MalformedURLException ex) {
      throw new QtvFileNotFoundException("File not found " + fileName, ex);
    }
  }
}
