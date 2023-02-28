package dam2.add.p22.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import dam2.add.p22.config.ConfigService;

public class ConectorJDBC {

  private static ConfigService conf = ConfigService.getInstance();
  private Connection dbConnection;

  public ConectorJDBC() {
    dbConnection = createConnection();
  }


  private Connection createConnection() {
    Connection dbConection = null;

    try {
      String bd = conf.getParametro("bbdd.nombre");
      String login = conf.getParametro("bbdd.login");
      String password = conf.getParametro("bbdd.pass");
      String host = conf.getParametro("bbdd.host");
      String driver = conf.getParametro("bbdd.driver");
      String url = conf.getParametro("bbdd.url");

      Class.forName(driver);
      dbConection = DriverManager.getConnection(url + host + "/" + bd, login, password);

      dbConection.setAutoCommit(false);
    } catch (Exception e) {
      System.out.println(e);
      dbConection = null;
    }
    return dbConection;
  }

  public Connection getConnection() {
    return this.dbConnection;
  }

  public boolean endConnection() {
    try {
      dbConnection.close();
      dbConnection = null;
      return true;
    } catch (SQLException e) {
      return false;
    }
  }

}
