package dam2.add.p22.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class ConfigService {

  private static HashMap<String, PropBundle> propList = new HashMap<String, PropBundle>();
  private static HashMap<String, String> propertyMap = new HashMap<String, String>();
  private static ConfigService instance;

  private ConfigService() {

  }

  public static ConfigService getInstance() {
    if (instance == null) {
      instance = new ConfigService();
    }
    return instance;
  }

  public void initConfig() throws Exception {

    String propsPath = getRootPath();
    if (propsPath.length() > 0) {
      List<File> propFilesList = getPropFiles(propsPath);

      propFilesList.forEach(propFile -> {
        PropBundle prop = new PropBundle(propFile);
        try (FileInputStream fis = new FileInputStream(propFile)) {
          prop.load(fis);
        } catch (Exception e) {
          System.out.println("NO existe el fichero de propiedades");
          prop = null;
        }
        if (prop != null) {
          propList.put(prop.getAlias(), prop);
        }
      });
    } else {
      initLogger(propsPath);
      throw new Exception("No se pudo inicializar la configuración.");
    }
    initLogger(propsPath);
  }

  private String getRootPath() {
    File rootFile;
    try {
      rootFile = new File(getClass().getResource("/").toURI());
      return rootFile.getAbsolutePath();
    } catch (URISyntaxException e) {
      e.printStackTrace();
      return "";
    }
  }


  private void initLogger(String sysPath) {
    String logPropsPath = sysPath + File.separator + getParametro("paths.log") + ".properties";
    Properties props = new Properties();
    // Set server log file location
    try (FileInputStream fis = new FileInputStream(logPropsPath)) {
      props.load(fis);

      String actionLogFilePath = sysPath + getParametro("app.log-action-file");
      props.setProperty("log4j.appender.file.File", actionLogFilePath);

      PropertyConfigurator.configure(props);
      Logger.getLogger("generic")
          .warn("Path del archivo de log -> " + props.getProperty("log4j.appender.file.File"));
    } catch (Exception e) {
      System.out.println("No se encuentra el archivo de configuración de logs");
      System.out.println("Logger en configuración por defecto.");
      BasicConfigurator.configure();
    }
  }

  private static List<File> getPropFiles(String pathFile) {
    Properties pathProps = new Properties();

    File pathsFile = new File(pathFile + File.separator + "paths.properties");
    try (FileInputStream fis = new FileInputStream(pathsFile)) {
      pathProps.load(fis);
    } catch (Exception e) {
      pathProps = null;
    }

    return pathProps.values().stream()
        .map(propValue -> new File(pathFile + File.separator + propValue + ".properties"))
        .collect(Collectors.toList());
  }

  private static String getAlias(String propKey) {
    return propKey.split("\\.")[0];
  }

  public String getParametro(String propertyKey) {

    String propertyValue = null;
    if (propertyMap.containsKey(propertyKey)) {
      propertyValue = propertyMap.get(propertyKey);
    } else {
      String propAlias = getAlias(propertyKey);
      propertyValue = propList.get(propAlias).getProperty(propertyKey);
      if (propertyValue == null) {
        System.out
            .println("El parametro <" + propertyKey + "> NO existe en el fichero de propiedades");
      } else {
        propertyMap.put(propertyKey, propertyValue);
      }
    }
    return propertyValue;
  }

  public boolean updateProperty(String key, String value) {
    PropBundle props = propList.get(getAlias(key));
    if (props != null) {
      // Mirar si es un valor distinto
      if (!props.getProperty(key).equals(value)) {
        props.setProperty(key, value);// Actualiza el bundle
        propertyMap.put(key, value);// Actualiza la cache
        // Intenta guardar en disco el bundle
        return storePropsBundle(props);
      } else {
        // No hace falta realizar cambios
        return true;
      }
    } else {
      // Propiedad no existe
      return false;
    }
  }

  private static boolean storePropsBundle(PropBundle props) {
    try (FileOutputStream fos = new FileOutputStream(props.getPropFile())) {
      props.store(fos, "desarrollo");
    } catch (Exception e) {
      return false;
    }
    return true;
  }


}
