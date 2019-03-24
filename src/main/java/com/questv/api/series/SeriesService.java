package com.questv.api.series;

import com.questv.api.contracts.ObjectService;
import com.questv.api.exception.IdNotFoundException;
import com.questv.api.file.FileStorageServiceImpl;
import com.questv.api.file.UploadedFileResponse;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service(value = "seriesService")
public class SeriesService {

  private final SeriesRepository seriesRepository;
  private final FileStorageServiceImpl fileStorageService;

  public SeriesService(final SeriesRepository seriesRepository, final FileStorageServiceImpl fileStorageService) {
    this.seriesRepository = seriesRepository;
    this.fileStorageService = fileStorageService;
    assert this.seriesRepository != null;
  }


  public SeriesDTO create(final SeriesDTO seriesDTO) {
    return save(seriesDTO.convert()).convert();
  }


  public List<SeriesDTO> findAll() {
    final List<SeriesModel> result = new ArrayList<>();
    this.seriesRepository.findAll().forEach(result::add);
    return result
        .stream()
        .map(SeriesModel::convert)
        .collect(Collectors.toList());
  }


  public SeriesDTO findById(final Long seriesId) {
    return findModelById(seriesId).convert();
  }

  public SeriesModel findModelById(final Long seriesId) {
    final Optional<SeriesModel> seriesModel = this.seriesRepository.findById(seriesId);
    if (seriesModel.isPresent()) {
      return seriesModel.get();
    }
    throw new IdNotFoundException();
  }


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


  public void delete(final Long seriesId) {
    this.seriesRepository.deleteById(seriesId);
  }


  public SeriesDTO createAndAttach(final SeriesDTO seriesDTO) {
    return seriesDTO;
  }

  /*default*/ UploadedFileResponse attachSeriesCover(final Long seriesId, final MultipartFile file) {

    final SeriesModel seriesModel = findModelById(seriesId);
    final String fileName = fileStorageService.store(file, seriesModel);

    final String fileDownloadUri = getFileUri(seriesId, "cover");

    seriesModel.setCoverImage(fileName);
    seriesModel.setCoverImageUrl(fileDownloadUri);
    this.update(seriesModel);

    return new UploadedFileResponse(fileName, fileDownloadUri,
        file.getContentType(), file.getSize());
  }

  /*default*/ UploadedFileResponse attachSeriesPromoImage(final Long seriesId, final MultipartFile file) {

    final SeriesModel seriesModel = findModelById(seriesId);
    final String fileName = fileStorageService.store(file, seriesModel);

    final String fileDownloadUri = getFileUri(seriesId, "promoImage");

    seriesModel.setPromoImage(fileName);
    seriesModel.setPromoImageUrl(fileDownloadUri);
    this.update(seriesModel);

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
    return ServletUriComponentsBuilder
        .fromCurrentContextPath()
        .path("/series/")
        .path(String.valueOf(seriesId))
        .path("/".concat(type))
        .toUriString();


  }


  public List<SeriesDTO> findAllByParent(final String seriesId) {
    return new ArrayList<>();
  }
}
