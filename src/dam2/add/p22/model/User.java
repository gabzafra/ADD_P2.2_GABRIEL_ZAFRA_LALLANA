package dam2.add.p22.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name = "users")
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private int id;

  @Column(name = "name", length = 50, nullable = false)
  private String name;

  @Column(name = "surnames", length = 255, nullable = false)
  private String surnames;

  @Column(name = "mail", length = 255, unique = true, nullable = false)
  private String email;

  @Column(name = "phone", length = 50, nullable = false)
  private String phone;

  @Column(name = "pass", length = 255, nullable = false)
  private String password;

  @Column(name = "lang", length = 50, nullable = false)
  private String lang;

  @Column(name = "role", nullable = false)
  private boolean isAdmin;

  public User() {
    this("", "", "", "", "", false, "ES");
  }

  public User(String name, String surnames, String email, String phone, String password,
      boolean isAdmin, String lang) {
    this(-1, name, surnames, email, phone, password, isAdmin, lang);
  }

  public User(int id, String name, String surnames, String email, String phone, String password,
      boolean isAdmin, String lang) {
    this.id = id;
    this.name = name;
    this.surnames = surnames;
    this.email = email;
    this.phone = phone;
    this.password = password;
    this.isAdmin = isAdmin;
    this.lang = lang;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getSurnames() {
    return surnames;
  }

  public void setSurnames(String surnames) {
    this.surnames = surnames;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public boolean isAdmin() {
    return isAdmin;
  }

  public void setAdmin(boolean isAdmin) {
    this.isAdmin = isAdmin;
  }

  public String getLang() {
    return lang;
  }

  public void setLang(String lang) {
    this.lang = lang;
  }

  @Override
  public String toString() {
    return "User [id=" + id + ", name=" + name + ", surnames=" + surnames + ", email=" + email
        + ", phone=" + phone + ", password=" + password + ", lang=" + lang + ", isAdmin=" + isAdmin
        + "]";
  }

}
