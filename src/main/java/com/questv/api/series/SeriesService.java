package com.questv.api.series;

import com.questv.api.contracts.ObjectService;
import com.questv.api.exception.IdNotFoundException;
import com.questv.api.file.FileStorageServiceImpl;
import com.questv.api.file.UploadedFileResponse;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
  public SeriesDTO create(final SeriesDTO seriesDTO) {
    return save(seriesDTO.convert()).convert();
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
  public SeriesDTO findById(final String seriesId) {
    return findModelById(seriesId).convert();
  }

  private SeriesModel findModelById(final String seriesId) {
    final Optional<SeriesModel> seriesModel = this.seriesRepository.findById(seriesId);
    if (seriesModel.isPresent()) {
      return seriesModel.get();
    }
    throw new IdNotFoundException();
  }

  @Override
  public void update(final SeriesDTO seriesDTO) {
    update(seriesDTO.convert());
  }

  public void update(final SeriesModel seriesModel) {
    final SeriesModel foundSeries = findModelById(seriesModel.getId());
    foundSeries.update(seriesModel);
    save(foundSeries);
  }

  private SeriesModel save(final SeriesModel seriesModel) {
    return this.seriesRepository.save(seriesModel);
  }

  @Override
  public void delete(final String seriesId) {
    this.seriesRepository.deleteById(seriesId);
  }

  @Override
  public SeriesDTO createAndAttach(final SeriesDTO seriesDTO) {
    return  seriesDTO;
  }

  /*default*/ UploadedFileResponse attachSeriesCover(final String seriesId, final MultipartFile file) {

    final SeriesModel seriesModel = findModelById(seriesId);
    final String fileName = fileStorageService.store(file, seriesModel);

    final String fileDownloadUri = getFileUri(seriesId, "cover");

    seriesModel.setCoverImage(fileName);
    seriesModel.setCoverImageUrl(fileDownloadUri);
    this.update(seriesModel);

    return new UploadedFileResponse(fileName, fileDownloadUri,
        file.getContentType(), file.getSize());
  }

  /*default*/ UploadedFileResponse attachSeriesPromoImage(final String seriesId, final MultipartFile file) {

    final SeriesModel seriesModel = findModelById(seriesId);
    final String fileName = fileStorageService.store(file, seriesModel);

    final String fileDownloadUri = getFileUri(seriesId, "promoImage");

    seriesModel.setPromoImage(fileName);
    seriesModel.setPromoImageUrl(fileDownloadUri);
    this.update(seriesModel);

    return new UploadedFileResponse(fileName, fileDownloadUri,
        file.getContentType(), file.getSize());
  }

  /*default*/ Resource findSeriesCover(final String seriesId) {
    return this.fileStorageService.loadAsResource(findById(seriesId).getCoverImage());
  }

  /*default*/ Resource findSeriesPromoImage(final String seriesId) {
    return this.fileStorageService.loadAsResource(findById(seriesId).getPromoImage());
  }

  private String getFileUri(final String seriesId, final String type) {
    return ServletUriComponentsBuilder.fromCurrentContextPath()
        .path("/series/")
        .path(String.valueOf(seriesId))
        .path("/".concat(type))
        .toUriString();
  }

  @Override
  public List<SeriesDTO> findAllByParent(final String seriesId) {
    return new ArrayList<>();
  }
}
