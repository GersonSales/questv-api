package com.questv.api.series;

import com.questv.api.contracts.ObjectService;
import com.questv.api.file.FileStorageService;
import com.questv.api.file.UploadedFileResponse;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController
public class SeriesRest {
  private final ObjectService<SeriesDTO> seriesService;

  public SeriesRest(final ObjectService<SeriesDTO> seriesService) {
    this.seriesService = seriesService;
  }

  @PostMapping("/series")
  @ResponseStatus(value = HttpStatus.CREATED)
  @ResponseBody
  public SeriesDTO postSeries(@Valid @RequestBody final SeriesDTO seriesDTO) {
    return this.seriesService.create(seriesDTO);
  }

  @PostMapping("/series/{seriesId}/cover")
  public UploadedFileResponse postSeriesCover(@PathVariable("seriesId") final Long seriesId,
                                              @RequestParam("file") final MultipartFile file) {
    return ((SeriesService) this.seriesService).attachSeriesCover(seriesId, file);
  }


  @GetMapping("/series/{seriesId}/cover")
  public ResponseEntity<Resource> getSeriesCover(@PathVariable("seriesId") final Long seriesId,
                                                 HttpServletRequest request) {
    final Resource resource = ((SeriesService) this.seriesService).findSeriesCover(seriesId);
    return getPreparedResponseEntity(request, resource);
  }

  @PostMapping("/series/{seriesId}/promoImage")
  public UploadedFileResponse postSeriesPromoImage(@PathVariable("seriesId") final Long seriesId,
                                                   @RequestParam("file") final MultipartFile file) {
    return ((SeriesService) this.seriesService).attachSeriesPromoImage(seriesId, file);
  }

  @GetMapping("/series/{seriesId}/promoImage")
  public ResponseEntity<Resource> getSeriesPromoImage(@PathVariable("seriesId") final Long seriesId,
                                                      HttpServletRequest request) {
    final Resource resource = ((SeriesService) this.seriesService).findSeriesPromoImage(seriesId);
    return getPreparedResponseEntity(request, resource);
  }

  @GetMapping("/series")
  public List<SeriesDTO> getSeries() {
    return this.seriesService.findAll();
  }

  @GetMapping("/series/{seriesId}")
  public SeriesDTO getSeriesById(@PathVariable Long seriesId) {
    return this.seriesService.findById(seriesId);
  }

  @PutMapping("/series/{seriesId}")
  public void putSeries(@PathVariable final Long seriesId, @RequestBody final SeriesDTO seriesDTO) {
    this.seriesService.updateById(seriesId, seriesDTO);
  }

  @DeleteMapping("/series/{seriesId}")
  public void deleteSeries(@PathVariable final Long seriesId) {
    this.seriesService.deleteById(seriesId);
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
