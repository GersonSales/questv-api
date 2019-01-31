package com.questv.api.file;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;


public interface FileStorageService {
  String store(final MultipartFile file);
  Resource loadAsResource(final String fileName);
}
