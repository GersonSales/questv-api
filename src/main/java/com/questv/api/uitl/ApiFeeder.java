package com.questv.api.uitl;

import com.google.gson.Gson;
import com.questv.api.series.SeriesDTO;
import net.minidev.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.nio.charset.StandardCharsets.UTF_8;

public class ApiFeeder {

  private static final String TOKEN = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJnZXJzb25zYWxlcyIsImlkIjoiMmM5MWZiYTY2YTJlOGY3ZTAxNmEyZTk5YTMyMDAwMDAiLCJleHAiOjE1NjIyOTk5MTR9.H1ZM5hFi_o4Vy3aWXTlfiTn_SGPtkgzKXpz9GTPRk2qviWa4ZVACwY69_fDmBZsn0Itf5hA2V2_ud9XznxKdAg";
  private static final String BASE_URL = "https://questv-api.herokuapp.com";
  //  private static final String BASE_URL = "http://localhost:5000";
  private static final String BASE_PATH = "/home/gersonsales/github/questv-api/artifacts/images";
  private final List<SeriesDTO> seriesDTOList;
  private final List<SeriesDTO> seriesDTOListResponse;
  private final Map<String, File> promoImageFileMap;
  private final Map<String, File> coverImageFileMap;


  private final String USER_AGENT = "Mozilla/5.0";

  public static void main(String[] args) throws Exception {
    ApiFeeder apiFeeder = new ApiFeeder();
//    apiFeeder.postSeries();
    apiFeeder.postSeriesImages();
  }

  private void postSeriesPromoImage() {
    for (final SeriesDTO seriesDTO : this.seriesDTOListResponse) {
      File promoImage;
      if (this.promoImageFileMap.containsKey(seriesDTO.getName().toLowerCase())) {
        promoImage = this.promoImageFileMap.get(seriesDTO.getName().toLowerCase());
      } else if (this.promoImageFileMap.containsKey(seriesDTO.getAbbreviation().toLowerCase())) {
        promoImage = this.promoImageFileMap.get(seriesDTO.getAbbreviation().toLowerCase());
      } else {
        continue;
      }

      MultipartUtility.sendFile(BASE_URL + "/series/" + seriesDTO.getId() + "/promoImage", TOKEN, promoImage);
    }
  }

  private void postSeriesImages() {
    this.seriesDTOListResponse.clear();
    this.seriesDTOListResponse.addAll(getMockedSeries());
    for (final SeriesDTO seriesDTO : this.seriesDTOListResponse) {
      String seriesName = seriesDTO
          .getAbbreviation()
          .toLowerCase()
          .replace(" ", "_")
          .replace("", "");
      if (this.promoImageFileMap.containsKey(seriesName)) {
        final File promoImage = this.promoImageFileMap.get(seriesName);
        MultipartUtility.sendFile(BASE_URL + "/series/" + seriesDTO.getId() + "/promoImage", TOKEN, promoImage);
      }

      if (this.coverImageFileMap.containsKey(seriesName)) {
        final File coverImage = this.coverImageFileMap.get(seriesName);
        MultipartUtility.sendFile(BASE_URL + "/series/" + seriesDTO.getId() + "/cover", TOKEN, coverImage);
      }

    }
  }


  public ApiFeeder() {
    this.seriesDTOList = new ArrayList<>();
    this.seriesDTOListResponse = new ArrayList<>();
    this.promoImageFileMap = new HashMap<>();
    this.coverImageFileMap = new HashMap<>();
    populateInitialSeriesList();
    populateImageFileMap("cover");
    populateImageFileMap("promo");
  }


  private void populateImageFileMap(final String type) {
    for (final SeriesDTO seriesDTO : this.seriesDTOList) {
      final String imageName
          = seriesDTO
          .getAbbreviation()
          .replace("'", "")
          .replace(" ", "_")
          .toLowerCase();

      File imageFile = null;
      try {
        imageFile
            = FileUtil.readImage(BASE_PATH + "/" + type + "Image/" + imageName + "_" + type + ".jpg");
      } catch (Exception a) {
        a.printStackTrace();
      }

      switch (type) {
        case "promo":
          this.promoImageFileMap.put(imageName, imageFile);
          break;
        case "cover":
          this.coverImageFileMap.put(imageName, imageFile);
          break;
      }
    }
  }

  private void postSeries() {
    final String url = BASE_URL + "/series";
    for (final SeriesDTO seriesDTO : this.seriesDTOList) {
      try {
        JSONObject seriesJson = new JSONObject();
        seriesJson.put("name", seriesDTO.getName());
        seriesJson.put("abbreviation", seriesDTO.getAbbreviation());
        seriesJson.put("category", seriesDTO.getCategory());

        final String response = sendPost(url, seriesJson.toJSONString());
        Gson gson = new Gson();
        final SeriesDTO seriesDTOResponse = gson.fromJson(response, SeriesDTO.class);
        this.seriesDTOListResponse.add(seriesDTOResponse);
      } catch (final Exception exception) {
        exception.printStackTrace();
      }
    }
  }

  private void populateInitialSeriesList() {
    //Action
    this.seriesDTOList.add(new SeriesDTO("The Waling Dead", "TWD", "Action"));
    this.seriesDTOList.add(new SeriesDTO("Dexter", "Dexter", "Action"));
    this.seriesDTOList.add(new SeriesDTO("Game of Thrones", "GOT", "Action"));
    this.seriesDTOList.add(new SeriesDTO("Money Heist", "Money Heist", "Action"));
    this.seriesDTOList.add(new SeriesDTO("Arrow", "Arrow", "Action"));
    this.seriesDTOList.add(new SeriesDTO("The Flash", "The Flash", "Action"));
    this.seriesDTOList.add(new SeriesDTO("Vikings", "Vikings", "Action"));

    //Comedy
    this.seriesDTOList.add(new SeriesDTO("How I Met Your Mother", "HIMYM", "Comedy"));
//    this.seriesDTOList.add(new SeriesDTO("Grey's Anatomy", "Grey's Anatomy", "Comedy"));
    this.seriesDTOList.add(new SeriesDTO("The Big Bang Theory", "TBBT", "Comedy"));
    this.seriesDTOList.add(new SeriesDTO("Two And A Half Man", "TAAHM", "Comedy"));
    this.seriesDTOList.add(new SeriesDTO("Modern Family", "Modern Family", "Comedy"));
    this.seriesDTOList.add(new SeriesDTO("The Simpsons", "The Simpsons", "Comedy"));
    this.seriesDTOList.add(new SeriesDTO("Veep", "Veep", "Comedy"));

    //Anime
    this.seriesDTOList.add(new SeriesDTO("Death Note", "Death Note", "Anime"));
    this.seriesDTOList.add(new SeriesDTO("Boku No Hero", "BNH", "Anime"));

    //Suspense
    this.seriesDTOList.add(new SeriesDTO("Stranger Things", "Stranger Things", "Suspense"));
    this.seriesDTOList.add(new SeriesDTO("Black Mirror", "Black Mirror", "Suspense"));
  }

  // HTTP GET request
  private void sendGet(final String url) throws Exception {

    URL obj = new URL(url);
    HttpURLConnection con = (HttpURLConnection) obj.openConnection();

    // optional default is GET
    con.setRequestMethod("GET");

    //add request header
    con.setRequestProperty("User-Agent", USER_AGENT);
    con.setRequestProperty("Authorization", TOKEN);


    int responseCode = con.getResponseCode();
    System.out.println("\nSending 'GET' request to URL : " + url);
    System.out.println("Response Code : " + responseCode);

    BufferedReader in = new BufferedReader(
        new InputStreamReader(con.getInputStream()));
    String inputLine;
    StringBuilder response = new StringBuilder();

    while ((inputLine = in.readLine()) != null) {
      response.append(inputLine);
    }
    in.close();

    //print result
    System.out.println(response.toString());

  }

  // HTTP POST request
  private String sendPost(final String url, final String body) throws Exception {

    URL obj = new URL(url);
    HttpURLConnection con = (HttpURLConnection) obj.openConnection();

    //add reuqest header
    con.setRequestMethod("POST");
    con.setRequestProperty("User-Agent", USER_AGENT);
    con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
    con.setRequestProperty("Authorization", TOKEN);
    con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
    con.setRequestProperty("Accept", "application/json");


    // Send post request
    con.setDoOutput(true);
    final OutputStream outputStream = con.getOutputStream();
    final OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream, UTF_8);
    outputStreamWriter.write(body);
    outputStreamWriter.flush();
    outputStreamWriter.close();

    int responseCode = con.getResponseCode();
    System.out.println("\nSending 'POST' request to URL : " + url);
    System.out.println("Request Body: " + body);
    System.out.println("Response Code : " + responseCode);

    BufferedReader in = new BufferedReader(
        new InputStreamReader(con.getInputStream()));
    String inputLine;
    StringBuilder response = new StringBuilder();

    while ((inputLine = in.readLine()) != null) {
      response.append(inputLine);
    }
    in.close();

    //print result
    return response.toString();

  }

  private List<SeriesDTO> getMockedSeries() {
    final List<SeriesDTO> result = new ArrayList<>();
    SeriesDTO flash = new SeriesDTO("The Flash", "The Flash", "Action");
    flash.setId(6L);
    result.add(flash);

    SeriesDTO veep = new SeriesDTO("Veep", "Veep", "Comedy");
    veep.setId(13L);
    result.add(veep);

    SeriesDTO got = new SeriesDTO("Game of Thrones", "GOT", "Action");
    got.setId(3L);
    result.add(got);

    SeriesDTO vikings = new SeriesDTO("Vikings", "Vikings", "Action");
    vikings.setId(7L);
    result.add(vikings);

    SeriesDTO dn =  new SeriesDTO("Death Note", "Death Note", "Anime");
    dn.setId(14L);
    result.add(dn);

    SeriesDTO himym =  new SeriesDTO("How I Met Your Mother", "himym", "Comedy");
    himym.setId(8L);
    result.add(himym);

    SeriesDTO tbbt =  new SeriesDTO("The Big Bang Theory", "tbbt", "Comedy");
    tbbt.setId(9L);
    result.add(tbbt);

    SeriesDTO bnh =  new SeriesDTO("Boku No Hero", "bnh", "Anime");
    bnh.setId(15L);
    result.add(bnh);

    SeriesDTO st =  new SeriesDTO("Stranger Things", "Stranger Things", "Terror");
    st.setId(16L);
    result.add(st);

    SeriesDTO taahm =  new SeriesDTO("Two And A Half Man", "taahm", "Comedy");
    taahm.setId(10L);
    result.add(taahm);

    SeriesDTO dexter =  new SeriesDTO("Dexter", "dexter", "Action");
    dexter.setId(2L);
    result.add(dexter);

    SeriesDTO twd =  new SeriesDTO("The Waling Dead", "twd", "Action");
    twd.setId(1L);
    result.add(twd);

    SeriesDTO mf =  new SeriesDTO("Modern Family", "Modern Family", "Comedy");
    mf.setId(11L);
    result.add(mf);

    SeriesDTO bm =  new SeriesDTO("Black Mirror", "bm", "Action");
    bm.setId(17L);
    result.add(bm);

    SeriesDTO arrow =  new SeriesDTO("Arrow", "Arrow", "Action");
    arrow.setId(5L);
    result.add(arrow);

    SeriesDTO ts =  new SeriesDTO("The Simpsons", "ts", "Comedy");
    ts.setId(12L);
    result.add(ts);

    return result;

  }

}


