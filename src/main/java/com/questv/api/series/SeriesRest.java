package com.questv.api.series;

import com.questv.api.contracts.ObjectService;
import com.questv.api.contracts.Restable;
import com.questv.api.exception.IdNotFoundException;
import com.questv.api.file.UploadedFileResponse;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/series")
public class SeriesRest  {
  private final SeriesService seriesService;

  public SeriesRest(final SeriesService seriesService) {
    this.seriesService = seriesService;
  }

  @PostMapping()
  public ResponseEntity<SeriesDTO> post(@Valid @RequestBody final SeriesDTO seriesDTO) {
    return ResponseEntity.ok(this.seriesService.create(seriesDTO));
  }

  @PostMapping("/{seriesId}/cover")
  public UploadedFileResponse postSeriesCover(@PathVariable("seriesId") final Long seriesId,
                                              @RequestParam("file") final MultipartFile file) {
    return ((SeriesService) this.seriesService).attachSeriesCover(seriesId, file);
  }


  @GetMapping("/{seriesId}/cover")
  public ResponseEntity<Resource> getSeriesCover(@PathVariable("seriesId") final Long seriesId,
                                                 HttpServletRequest request) {
    final Resource resource = ((SeriesService) this.seriesService).findSeriesCover(seriesId);
    return getPreparedResponseEntity(request, resource);
  }

  @PostMapping("/{seriesId}/promoImage")
  public UploadedFileResponse postSeriesPromoImage(@PathVariable("seriesId") final Long seriesId,
                                                   @RequestParam("file") final MultipartFile file) {
    return ((SeriesService) this.seriesService).attachSeriesPromoImage(seriesId, file);
  }

  @GetMapping("/{seriesId}/promoImage")
  public ResponseEntity<Resource> getSeriesPromoImage(@PathVariable("seriesId") final Long seriesId,
                                                      HttpServletRequest request) {
    final Resource resource = ((SeriesService) this.seriesService).findSeriesPromoImage(seriesId);
    return getPreparedResponseEntity(request, resource);
  }

  @GetMapping()
  public ResponseEntity<List<SeriesDTO>> get() {
    return ResponseEntity.ok(this.seriesService.findAll());
  }

  @GetMapping("/{seriesId}")
  public ResponseEntity<SeriesDTO> get(@PathVariable Long seriesId) {
    try {
      return ResponseEntity.ok(this.seriesService.findById(seriesId));
    } catch (final IdNotFoundException exception) {
      return ResponseEntity.badRequest().build();
    }
  }

  
  @PutMapping("/{seriesId}")
  public void put(@PathVariable Long seriesId, @RequestBody final SeriesDTO seriesDTO) {
    seriesDTO.setId(seriesId);
    this.seriesService.update(seriesDTO);
  }

  @DeleteMapping("/{seriesId}")
  public void delete(@PathVariable final Long seriesId) {
    this.seriesService.delete(seriesId);
  }

  private ResponseEntity<Resource> getPreparedResponseEntity(final HttpServletRequest request,
                                                             final Resource resource) {
    String filename = resource.getFilename();
    return ResponseEntity.ok()
        .contentType(MediaType.parseMediaType(getFileContent(request, resource)))
        .header("Content-Name", filename)
        .body(resource);
  }

  private String getFileContent(final HttpServletRequest request, final Resource resource) {
    String contentType;
    try {
      contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
    } catch (IOException ex) {
      throw new RuntimeException(ex);
    }

    if (contentType == null) {
      contentType = "application/octet-stream";
    }
    return contentType;
  }
}
