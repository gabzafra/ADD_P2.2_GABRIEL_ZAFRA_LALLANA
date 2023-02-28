package dam2.add.p22.controller;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import dam2.add.p22.config.ConfigService;
import dam2.add.p22.model.User;
import dam2.add.p22.service.LangService;
import dam2.add.p22.service.UserService;

@WebServlet("/admin")
public class Admin extends HttpServlet {
  private static final long serialVersionUID = 1L;
  private LangService langSrv = LangService.getInstance();
  private UserService userSrv = UserService.getInstance();
  private ConfigService confSrv = ConfigService.getInstance();

  public Admin() {
    super();
  }

  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    Integer idAuth = (Integer) request.getSession().getAttribute("id");
    if (idAuth != null) {
      User user = userSrv.getUserById(idAuth);
      if (user.isAdmin()) {
        List<User> userList = userSrv.getNonAdminUsers();
        String updId = request.getParameter("upd");
        String delId = request.getParameter("del");
        if (updId == null && delId == null) {
          request.setAttribute("error", null);
          request.setAttribute("list", userList);
          request.setAttribute("detail", user);
          request.setAttribute("storemode", confSrv.getParametro("app.persistence"));
          request.setAttribute("deflang", confSrv.getParametro("app.lang"));
          request.getRequestDispatcher("admin.jsp").forward(request, response);
        } else if (updId != null) {
          User updateUser = userList.stream().filter(u -> updId.equals(u.getId() + "")).findFirst()
              .orElse(new User());
          if (updateUser.getId() > 0) {
            request.setAttribute("user", updateUser);
            request.getRequestDispatcher("update-profile.jsp").forward(request, response);
          } else {
            response.sendError(404);
          }
        } else if (delId != null) {
          User targetUser = userSrv.getUserById(delId);
          if (request.getParameter("conf") != null) {
            // confirmado delete
            String error = userSrv.validateDeleteUser(targetUser, idAuth);
            if (error.length() > 0) {
              error = langSrv.getLocalError(idAuth, error);
              request.setAttribute("error", error);
            }
            userList = userSrv.getNonAdminUsers();
            request.setAttribute("list", userList);
            request.setAttribute("storemode", confSrv.getParametro("app.persistence"));
            request.setAttribute("deflang", confSrv.getParametro("app.lang"));
            request.setAttribute("detail", user);
            request.getRequestDispatcher("admin.jsp").forward(request, response);
          } else {
            // mostrar SI/NO
            request.setAttribute("user", targetUser);
            request.getRequestDispatcher("confirmation.jsp").forward(request, response);
          }
        } else {
          response.sendError(404);
        }
      } else {
        response.sendError(404);
      }
    } else {
      response.sendError(404);
    }

  }

  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    String filterStr = request.getParameter("filter");
    String storeMode = request.getParameter("storemode");
    String defLang = request.getParameter("deflang");

    Integer idAuth = (Integer) request.getSession().getAttribute("id");
    if (idAuth != null) {
      User user = userSrv.getUserById(idAuth);
      if (user.isAdmin()) {
        if (filterStr != null) {
          request.setAttribute("list", userSrv.getUsersFilteredByStart(filterStr));
        } else {
          request.setAttribute("list", userSrv.getNonAdminUsers());
        }
        if (storeMode != null) {
          confSrv.updateProperty("app.persistence", storeMode);
        }
        if (defLang != null) {
          confSrv.updateProperty("app.lang", defLang);
        }

        request.setAttribute("filter", filterStr);
        request.setAttribute("detail", user);
        request.setAttribute("storemode", confSrv.getParametro("app.persistence"));
        request.setAttribute("deflang", confSrv.getParametro("app.lang"));
        request.getRequestDispatcher("admin.jsp").forward(request, response);
      } else {
        response.sendError(404);
      }
    } else {
      response.sendError(404);
    }
  }

}
