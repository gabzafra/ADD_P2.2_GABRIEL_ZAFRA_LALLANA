package dam2.add.p22.model;

public class GeoItem {
  private String id;
  private String nm;

  public GeoItem() {
    id = "";
    nm = "";
  }

  public GeoItem(String id, String name) {
    this.id = id;
    this.nm = name;
  }

  public String getId() {
    return id;
  }

  public String getNm() {
    return nm;
  }
}
