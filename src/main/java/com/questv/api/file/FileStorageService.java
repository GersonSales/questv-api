package com.questv.api.file;

import com.questv.api.contracts.DTO;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;


public interface FileStorageService {
  String store(final MultipartFile file, final DTO dto);
  Resource loadAsResource(final String fileName);
}
