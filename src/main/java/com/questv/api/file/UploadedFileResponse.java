package com.questv.api.file;

public class UploadedFileResponse {
  private final String fileName;
  private final String fileDownloadUri;
  private final String fileType;
  private long size;

  public UploadedFileResponse(final String fileName,
                              final String fileDownloadUri,
                              final String fileType,
                              final long size) {
    this.fileName = fileName;
    this.fileDownloadUri = fileDownloadUri;
    this.fileType = fileType;
    this.size = size;
  }

  public String getFileName() {
    return fileName;
  }


  public String getFileDownloadUri() {
    return fileDownloadUri;
  }

  public String getFileType() {
    return fileType;
  }

  public long getSize() {
    return size;
  }

  public void setSize(long size) {
    this.size = size;
  }
}

