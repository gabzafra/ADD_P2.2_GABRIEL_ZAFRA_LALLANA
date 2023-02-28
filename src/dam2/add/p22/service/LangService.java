package dam2.add.p22.service;

import java.util.Locale;
import java.util.ResourceBundle;
import dam2.add.p22.config.ConfigService;
import dam2.add.p22.dao.UserDAO;
import dam2.add.p22.model.User;

public class LangService {
  private static LangService instance;
  private static final UserDAO USER_DAO = PersistenceService.getUserDAO();
  private static ConfigService appService = ConfigService.getInstance();

  private LangService() {}

  public static LangService getInstance() {
    if (instance == null) {
      instance = new LangService();
    }
    return instance;
  }

  public String getLocalError(String error) {
    return getLocalError(appService.getParametro("app.lang"), error);
  }

  public String getLocalError(int userId, String errorMsg) {
    User user = USER_DAO.getUserById(userId);

    ResourceBundle rb;
    if (user.getId() > 0) {
      rb = ResourceBundle.getBundle("idioma", new Locale(user.getLang()));
    } else {
      String defaultLang = appService.getParametro("app.lang");
      rb = ResourceBundle.getBundle("idioma", new Locale(defaultLang));
    }

    return rb.getString(errorMsg);
  }

  public String getLocalError(String localStub, String errorMsg) {
    ResourceBundle rb = ResourceBundle.getBundle("idioma", new Locale(localStub));
    return rb.getString(errorMsg);
  }
}
