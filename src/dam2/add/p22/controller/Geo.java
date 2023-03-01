package dam2.add.p22.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.gson.Gson;
import dam2.add.p22.model.GeoItem;
import dam2.add.p22.service.GeoService;

@WebServlet("/geo")
public class Geo extends HttpServlet {
  private static final long serialVersionUID = 1L;
  private GeoService geoSrv = GeoService.getInstance();

  public Geo() {
    super();
  }

  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    String id = request.getParameter("provincia");
    response.setContentType("aplication/json");
    response.setCharacterEncoding("UTF-8");
    if (id != null) {
      GeoItem[] municipiosArr = geoSrv.getMunicipios(new GeoItem(id, ""));
      String jsonResponse = new Gson().toJson(municipiosArr);
      response.getWriter().println(jsonResponse);
    } else {
      GeoItem[] provinciasArr = geoSrv.getProvincias();
      String jsonResponse = new Gson().toJson(provinciasArr);
      response.getWriter().println(jsonResponse);
    }
  }

}
