package dam2.add.p22.config;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import org.apache.log4j.Logger;

@WebListener
public class ConfigServletContextListener implements ServletContextListener {

  public ConfigServletContextListener() {}


  public void contextDestroyed(ServletContextEvent event) {
    Logger.getLogger("generic").info("ServletContext down");
  }

  public void contextInitialized(ServletContextEvent event) {
    try {
      ConfigService.getInstance().initConfig();
    } catch (Exception e) {
      e.printStackTrace();
    }
    Logger.getLogger("generic").info("ServletContext up");
  }

}
