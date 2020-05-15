package com.questv.api.file;

import com.questv.api.exception.file.FileStorageException;
import com.questv.api.exception.file.ItemFileNotFoundException;
import com.questv.api.uitl.NetworkUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

@Service
public class FileStorageServiceImpl {

  private final Path path;
  private static final List<String> acceptedFormats = new ArrayList<>();

  @Autowired
  public FileStorageServiceImpl(
      final FileStorageProperties fileStorageProperties) {
    this.path = Paths.get(
        fileStorageProperties
            .getUploadDirectory())
        .toAbsolutePath().normalize();
    createDirectory();
  }

  private void createDirectory() {
    try {
      if (!Files.exists(this.path)) {
        Files.createDirectory(this.path);
      }
    } catch (final Exception exception) {
      throw new FileStorageException("Could not create directory.", exception);
    }

  }

  public UploadedFileResponse getUploadFileResponse(
      final Long seriesId,
      final MultipartFile file,
      final SeriesFileType seriesFileType) {

    final String fileName = this.store(file, seriesId);

    final String fileDownloadUri = getFileUri(seriesId, seriesFileType);

    return new UploadedFileResponse(fileName, fileDownloadUri,
        file.getContentType(), file.getSize());
  }


  public String store(final MultipartFile file, final Long fileKey) {
    String fileName = getFileName(file);
    fileName = String.valueOf(fileKey).concat("_").concat(fileName).trim();
    validateFormat(fileName);

    try {

      final Path targetLocation = this.path.resolve(fileName);
      Files.copy(
          file.getInputStream(),
          targetLocation,
          StandardCopyOption.REPLACE_EXISTING);

      return fileName;
    } catch (final IOException ex) {
      throw new FileStorageException("Could not store file " + fileName + ". " +
          "Please try again!", ex);
    }

  }

  private void validateFormat(final String fileName) {
    final String[] splitFilename = fileName.split("\\.");
    final String extension = splitFilename[splitFilename.length - 1];
    if (splitFilename.length < 2 || !isAcceptedExtension(extension)) {
      throw new FileStorageException("Extension " + extension + " is not " +
          "allowed.");

    }
  }

  private boolean isAcceptedExtension(final String extension) {
    return extension.equals("png")
        || extension.equals("jpg")
        || extension.equals("jpeg");
  }

  private String getFileName(final MultipartFile file) {
    String originalFilename = file.getOriginalFilename();
    String filename = StringUtils
        .cleanPath(originalFilename == null ? "" : originalFilename);

    if (filename.contains("..")) {
      throw new FileStorageException("Sorry! Filename contains invalid path" +
          " sequence " + filename);
    }

    return filename;
  }

  public Resource loadAsResource(final String fileName) {
    try {
      Path filePath = this.path.resolve(fileName).normalize();
      Resource resource = new UrlResource(filePath.toUri());
      if (resource.exists()) {
        return resource;
      } else {
        throw new ItemFileNotFoundException("File not found " + fileName);
      }
    } catch (final MalformedURLException ex) {
      throw new ItemFileNotFoundException("File not found " + fileName, ex);
    }
  }

  private String getFileUri(final Long seriesId, final SeriesFileType type) {
    String publicIp = NetworkUtil.getPublicIp();
    return ServletUriComponentsBuilder
        .fromCurrentContextPath()
        .path("/series/")
        .path(String.valueOf(seriesId))
        .path("/".concat(type.toString()))
        .toUriString().replace("localhost", publicIp);

  }
}
