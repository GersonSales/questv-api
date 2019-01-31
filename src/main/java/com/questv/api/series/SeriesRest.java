package com.questv.api.series;

import com.questv.api.contracts.ObjectService;
import com.questv.api.file.FileStorageService;
import com.questv.api.file.UploadedFileResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.util.List;

@RestController
public class SeriesRest {
  private final ObjectService<SeriesDTO> seriesService;
  private final FileStorageService fileStorageService;

  public SeriesRest(final ObjectService<SeriesDTO> seriesService, final FileStorageService fileStorageService) {
    this.seriesService = seriesService;
    this.fileStorageService = fileStorageService;
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
    return ((SeriesService)this.seriesService).attachSeriesCover(seriesId, file);

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

}
