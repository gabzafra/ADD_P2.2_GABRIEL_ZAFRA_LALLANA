package dam2.add.p22.config;

import java.io.File;
import java.util.Properties;

public class PropBundle extends Properties {
  private static final long serialVersionUID = 1L;
  private static final String FILE_EXTENSION = ".properties";
  private String alias;
  private File propFile;

  public PropBundle(File propFile) {
    super();
    this.alias = generateAlias(propFile.getName());
    this.propFile = propFile;
  }

  private String generateAlias(String fileName) {
    return fileName.substring(0, fileName.length() - FILE_EXTENSION.length());
  }

  public String getAlias() {
    return alias;
  }

  public void setAlias(String alias) {
    this.alias = alias;
  }

  public File getPropFile() {
    return propFile;
  }

  public void setPropFile(File propFile) {
    this.propFile = propFile;
  }
}
