package dam2.add.p22.service;

import dam2.add.p22.config.ConfigService;
import dam2.add.p22.dao.UserDAO;
import dam2.add.p22.dao.UserDAOHibernate;
import dam2.add.p22.dao.UserDAOJDBC;
import dam2.add.p22.dao.UserDAOinMem;

public class PersistenceService {
  public static UserDAO getUserDAO() {
    String mode = ConfigService.getInstance().getParametro("app.persistence");
    if (mode.equals("jdbc")) {
      return UserDAOJDBC.getInstance();
    } else if (mode.equals("mem")) {
      return UserDAOinMem.getInstance();
    } else {
      return UserDAOHibernate.getInstance();
    }
  }
}
