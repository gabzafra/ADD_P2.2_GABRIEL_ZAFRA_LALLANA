package dam2.add.p22.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import dam2.add.p22.config.ConfigService;

@WebServlet("/lang")
public class Lang extends HttpServlet {
  private static final long serialVersionUID = 1L;
  private ConfigService appConfig = ConfigService.getInstance();

  public Lang() {
    super();
  }

  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    String sesionLocaleStr = (String) request.getSession().getAttribute("idioma");

    String lang = request.getParameter("set");


    if (sesionLocaleStr == null) {
      sesionLocaleStr = appConfig.getParametro("app.lang");
    } else if (!sesionLocaleStr.equals(lang)) {
      sesionLocaleStr = lang;
    }

    request.getSession().setAttribute("idioma", sesionLocaleStr);
    response.sendRedirect("index.jsp");
  }

}
