package com.questv.api.series;

import com.questv.api.exception.IdNotFoundException;
import com.questv.api.file.FileStorageServiceImpl;
import com.questv.api.file.SeriesFileType;
import com.questv.api.file.UploadedFileResponse;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service(value = "seriesService")
public class SeriesService {

  private final SeriesRepository seriesRepository;
  private final FileStorageServiceImpl fileStorageService;

  public SeriesService(final SeriesRepository seriesRepository,
                       final FileStorageServiceImpl fileStorageService) {
    this.seriesRepository = seriesRepository;
    this.fileStorageService = fileStorageService;
    assert this.seriesRepository != null;
  }


  /*default*/ SeriesDTO create(final SeriesDTO seriesDTO) {
    return save(seriesDTO.convert()).convert();
  }


  /*default*/ List<SeriesDTO> findAll() {
    final List<SeriesModel> result = new ArrayList<>();
    this.seriesRepository.findAll().forEach(result::add);
    return result
        .stream()
        .map(SeriesModel::convert)
        .collect(Collectors.toList());
  }


  /*default*/ SeriesDTO findById(final Long seriesId) {
    return findModelById(seriesId).convert();
  }

  /*default*/ SeriesModel findModelById(final Long seriesId) {
    final Optional<SeriesModel> seriesModel =
        this.seriesRepository.findById(seriesId);
    if (seriesModel.isPresent()) {
      return seriesModel.get();
    }
    throw new IdNotFoundException();
  }


  /*default*/ void update(final SeriesDTO seriesDTO) {
    update(seriesDTO.convert());
  }

  /*default*/ void update(final SeriesModel seriesModel) {
    final SeriesModel foundSeries = findModelById(seriesModel.getId());
    foundSeries.update(seriesModel);
    save(foundSeries);
  }

  private SeriesModel save(final SeriesModel seriesModel) {
    return this.seriesRepository.save(seriesModel);
  }


  /*default*/ void delete(final Long seriesId) {
    this.seriesRepository.deleteById(seriesId);
  }


  public SeriesDTO createAndAttach(final SeriesDTO seriesDTO) {
    return seriesDTO;
  }

  /*default*/ UploadedFileResponse attachSeriesCover(final Long seriesId,
                                                     final MultipartFile file) {

    final SeriesModel seriesModel = findModelById(seriesId);
    final UploadedFileResponse response =
        fileStorageService.getUploadFileResponse(
            seriesId,
            file,
            SeriesFileType.COVER);

    seriesModel.setCoverImage(response.getFileName());
    seriesModel.setCoverImageUrl(response.getFileDownloadUri());
    this.update(seriesModel);

    return response;
  }

  /*default*/ UploadedFileResponse attachSeriesPromoImage(
      final Long seriesId,
      final MultipartFile file) {

    final SeriesModel seriesModel = findModelById(seriesId);
    final UploadedFileResponse response
        = fileStorageService.getUploadFileResponse(
        seriesId,
        file,
        SeriesFileType.PROMO);

    seriesModel.setPromoImage(response.getFileName());
    seriesModel.setPromoImageUrl(response.getFileDownloadUri());
    this.update(seriesModel);

    return response;
  }

  /*default*/ Resource findSeriesCover(final Long seriesId) {
    return this.fileStorageService
        .loadAsResource(findById(seriesId).getCoverImage());
  }

  /*default*/ Resource findSeriesPromoImage(final Long seriesId) {
    return this.fileStorageService
        .loadAsResource(findById(seriesId).getPromoImage());
  }
}
