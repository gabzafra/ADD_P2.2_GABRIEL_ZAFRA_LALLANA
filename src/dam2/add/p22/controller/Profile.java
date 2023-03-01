package dam2.add.p22.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import dam2.add.p22.model.User;
import dam2.add.p22.service.LangService;
import dam2.add.p22.service.UserService;

@WebServlet("/profile")
public class Profile extends HttpServlet {
  private static final long serialVersionUID = 1L;
  private LangService langSrv = LangService.getInstance();
  private UserService userSrv = UserService.getInstance();

  public Profile() {
    super();
  }

  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    Integer idAuth = (Integer) request.getSession().getAttribute("id");
    if (idAuth != null) {
      // esta logado
      if (request.getParameter("upd") != null) {
        // update
        User user = userSrv.getUserById(request.getParameter("upd"));
        if (user.getId() == idAuth || userSrv.getUserById(idAuth).isAdmin()) {
          request.setAttribute("user", user);
          request.getRequestDispatcher("update-profile.jsp").forward(request, response);
        } else {
          response.sendError(404);
        }

      } else {
        // datos desde la sesion
        User user = userSrv.getUserById(idAuth);
        if (user.getId() > 0) {
          request.setAttribute("detail", user);
          if (user.isAdmin()) {
            response.sendRedirect("./admin");
          } else {
            request.getRequestDispatcher("profile.jsp").forward(request, response);
          }
        } else {
          response.sendError(404);
        }
      }
    } else {
      // No esta logado
      response.sendError(404);
    }
  }

  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    Integer idAuth = (Integer) request.getSession().getAttribute("id");
    if (idAuth != null) {
      if (request.getParameter("update") != null) {
        User user = new User();
        int userId = userSrv.parseId(request.getParameter("update"));
        user.setId(userId);
        user.setName(request.getParameter("name"));
        user.setSurnames(request.getParameter("surnames"));
        user.setEmail(request.getParameter("mail"));
        user.setPhone(request.getParameter("phone"));
        user.setProvince(request.getParameter("provincia"));
        String municipio = request.getParameter("municipio");
        user.setTown(municipio == null ? "" : municipio);

        String error = userSrv.validateUpdateFields(user, idAuth);

        if (error.length() > 0) {
          request.setAttribute("user", user);
          error = langSrv.getLocalError(idAuth, error);
          request.setAttribute("error", error);
          request.getRequestDispatcher("update-profile.jsp").forward(request, response);
        } else {
          response.sendRedirect(request.getContextPath() + "/profile");
        }

      } else if (request.getParameter("pass") != null) {
        String pass1 = request.getParameter("new-pass1");
        String pass2 = request.getParameter("new-pass2");
        if (!pass1.equals(pass2)) {
          String error = langSrv.getLocalError(idAuth, "err_not_same_pw");
          Logger.getLogger("generic").error(error);
          request.setAttribute("error", error);
          request.getRequestDispatcher("change-pass.jsp").forward(request, response);
        } else {
          String oldPass = request.getParameter("old-pass");
          String error = userSrv.changePass(idAuth, pass1, oldPass);
          if (error.length() > 0) {
            error = langSrv.getLocalError(idAuth, error);
            request.setAttribute("error", error);
            request.getRequestDispatcher("change-pass.jsp").forward(request, response);
          } else {
            response.sendRedirect(request.getContextPath() + "/profile");
          }
        }
      } else {
        response.sendError(404);
      }
    } else {
      response.sendError(404);
    }
  }

}
