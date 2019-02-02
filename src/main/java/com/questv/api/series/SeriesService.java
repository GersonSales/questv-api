package com.questv.api.series;

import com.questv.api.contracts.ObjectService;
import com.questv.api.file.FileStorageServiceImpl;
import com.questv.api.file.UploadedFileResponse;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service(value = "seriesService")
public class SeriesService implements ObjectService<SeriesDTO> {

  private final SeriesRepository seriesRepository;
  private final FileStorageServiceImpl fileStorageService;

  public SeriesService(final SeriesRepository seriesRepository, final FileStorageServiceImpl fileStorageService) {
    this.seriesRepository = seriesRepository;
    this.fileStorageService = fileStorageService;
    assert this.seriesRepository != null;
  }

  @Override
  public SeriesDTO create(final SeriesDTO model) {
    return this.seriesRepository.save(model.convert()).convert();
  }

  @Override
  public List<SeriesDTO> findAll() {
    final List<SeriesModel> result = new ArrayList<>();
    this.seriesRepository.findAll().forEach(result::add);
    return result
        .stream()
        .map(SeriesModel::convert)
        .collect(Collectors.toList());
  }

  @Override
  public SeriesDTO findById(final Long seriesId) {
    return this.seriesRepository
        .findById(seriesId)
        .map(SeriesModel::convert)
        .orElse(new NullSeriesDTO());
  }

  @Override
  public void updateById(final Long seriesId, final SeriesDTO seriesDTO) {
    this.seriesRepository
        .findById(seriesId)
        .ifPresent((seriesModel) -> {
          seriesModel.update(seriesDTO.convert());
          this.seriesRepository.save(seriesModel);
        });
  }

  @Override
  public void deleteById(final Long seriesId) {
    this.seriesRepository.deleteById(seriesId);
  }

  @Override
  public void createAndAttach(SeriesDTO model) {

  }

  /*default*/ UploadedFileResponse attachSeriesCover(final Long seriesId, final MultipartFile file) {

    final SeriesDTO byId = findById(seriesId);
    final String fileName = fileStorageService.store(file, byId);

    final String fileDownloadUri = getFileUri(seriesId, "cover");

    byId.setCoverImage(fileName);
    byId.setCoverImageUrl(fileDownloadUri);
    this.updateById(seriesId, byId);

    return new UploadedFileResponse(fileName, fileDownloadUri,
        file.getContentType(), file.getSize());
  }

  /*default*/ UploadedFileResponse attachSeriesPromoImage(final Long seriesId, final MultipartFile file) {

    final SeriesDTO byId = findById(seriesId);
    final String fileName = fileStorageService.store(file, byId);

    final String fileDownloadUri = getFileUri(seriesId, "promoImage");

    byId.setPromoImage(fileName);
    byId.setPromoImageUrl(fileDownloadUri);
    this.updateById(seriesId, byId);

    return new UploadedFileResponse(fileName, fileDownloadUri,
        file.getContentType(), file.getSize());
  }

  /*default*/ Resource findSeriesCover(final Long seriesId) {
    return this.fileStorageService.loadAsResource(findById(seriesId).getCoverImage());
  }

  /*default*/ Resource findSeriesPromoImage(final Long seriesId) {
    return this.fileStorageService.loadAsResource(findById(seriesId).getPromoImage());
  }

  private String getFileUri(final Long seriesId, final String type) {
    return ServletUriComponentsBuilder.fromCurrentContextPath()
        .path("/series/")
        .path(String.valueOf(seriesId))
        .path("/".concat(type))
        .toUriString();
  }
}
