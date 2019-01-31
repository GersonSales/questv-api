package com.questv.api.file;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "file")
public class FileStorageProperties {
  private String uploadDirectory;


  public String getUploadDirectory() {
    return uploadDirectory;
  }

  public void setUploadDirectory(final String uploadDirectory) {
    this.uploadDirectory = uploadDirectory;
  }
}
