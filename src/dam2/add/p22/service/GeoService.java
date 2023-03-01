package dam2.add.p22.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.Comparator;
import com.google.gson.Gson;
import dam2.add.p22.config.ConfigService;
import dam2.add.p22.model.GeoItem;

public class GeoService {
  private static GeoService instance;
  private static ConfigService conf = ConfigService.getInstance();

  private GeoService() {

  }

  public static GeoService getInstance() {
    if (instance == null) {
      instance = new GeoService();
    }
    return instance;
  }

  public GeoItem[] getProvincias() {
    GeoItem[] provinciasArr;

    try {
      String provinciasJSONstr = leerUrl(conf.getParametro("app.url-provincias"));
      provinciasArr = new Gson().fromJson(provinciasJSONstr, GeoItem[].class);
    } catch (Exception e) {
      return new GeoItem[0];
    }

    Arrays.sort(provinciasArr, Comparator.comparing(GeoItem::getNm));

    return provinciasArr;
  }

  public GeoItem[] getMunicipios(GeoItem provincia) {
    GeoItem[] municipiosArr;

    try {
      String municipiosJSONstr = leerUrl(conf.getParametro("app.url-municipios"));
      municipiosArr = new Gson().fromJson(municipiosJSONstr, GeoItem[].class);
    } catch (Exception e) {
      return new GeoItem[0];
    }

    return Arrays.stream(municipiosArr)
        .filter(municipio -> municipio.getId().startsWith(provincia.getId()))
        .sorted((m1, m2) -> m1.getNm().compareTo(m2.getNm())).toArray(GeoItem[]::new);
  }

  public GeoItem getLugarById(String id) {
    GeoItem result = new GeoItem();
    if (id != null) {
      if (id.length() == 2) {
        result = Arrays.stream(getProvincias()).filter(provincia -> provincia.getId().equals(id))
            .findFirst().orElse(new GeoItem());
      } else if (id.length() == 5) {
        result = Arrays.stream(getMunicipios(new GeoItem(id, "")))
            .filter(municipio -> municipio.getId().equals(id)).findFirst().orElse(new GeoItem());
      }
    }
    return result;
  }

  private static String leerUrl(String sUrl) throws Exception {
    String output = "";
    try {
      URL url = new URL(sUrl);

      HttpURLConnection conn = (HttpURLConnection) url.openConnection();
      conn.setRequestMethod("GET");

      if (conn.getResponseCode() != 200) {
        throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
      }

      BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

      StringBuilder sb = new StringBuilder();
      int cp;
      while ((cp = br.read()) != -1) {
        sb.append((char) cp);
      }

      output = sb.toString();

      conn.disconnect();
    } catch (Exception e) {
      throw e;
    }

    return output;
  }
}
