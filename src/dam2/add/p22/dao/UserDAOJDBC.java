package dam2.add.p22.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import dam2.add.p22.model.User;

public class UserDAOJDBC implements UserDAO {
  private static UserDAOJDBC instance;
  private static ConectorJDBC conector;
  private static Connection db;

  private UserDAOJDBC() {
    conector = new ConectorJDBC();
    db = conector.getConnection();
  }

  public static UserDAOJDBC getInstance() {
    if (instance == null) {
      instance = new UserDAOJDBC();
    }
    return instance;
  }

  private User getUserByEmail(String email) {
    User result = new User();

    String query = "SELECT * FROM users WHERE mail=?";

    try {
      PreparedStatement ps = db.prepareStatement(query);
      ps.setString(1, email);

      ResultSet rs = ps.executeQuery();

      while (rs.next()) {
        result.setId(rs.getInt(1));
        result.setName(rs.getString(2));
        result.setSurnames(rs.getString(3));
        result.setEmail(rs.getString(4));
        result.setPhone(rs.getString(5));
        result.setPassword(rs.getString(6));
        result.setAdmin(rs.getBoolean(7));
        result.setLang(rs.getString(8));
      }
      rs.close();
      ps.close();
    } catch (SQLException e) {
      result = null;
    }
    return result;
  }

  @Override
  public User createUser(User user) {
    int rows = 0;

    String query =
        "INSERT INTO users (name, surnames, mail, phone, pass, role, lang) VALUES (?, ?, ?, ?, ?, ?, ?)";

    try {
      PreparedStatement ps = db.prepareStatement(query);
      ps.setString(1, user.getName());
      ps.setString(2, user.getSurnames());
      ps.setString(3, user.getEmail());
      ps.setString(4, user.getPhone());
      ps.setString(5, user.getPassword());
      ps.setBoolean(6, user.isAdmin());
      ps.setString(7, user.getLang());

      rows = ps.executeUpdate();

      db.commit();
      ps.close();
    } catch (SQLException e) {
      rows = 0;
    }

    if (rows > 0) {
      return getUserByEmail(user.getEmail());
    } else {
      return new User();
    }
  }

  @Override
  public List<User> getAllUsers() {
    List<User> result = new LinkedList<User>();

    String query = "SELECT * FROM users";

    try {
      PreparedStatement ps = db.prepareStatement(query);

      ResultSet rs = ps.executeQuery();

      while (rs.next()) {
        User user = new User();
        user.setId(rs.getInt(1));
        user.setName(rs.getString(2));
        user.setSurnames(rs.getString(3));
        user.setEmail(rs.getString(4));
        user.setPhone(rs.getString(5));
        user.setPassword(rs.getString(6));
        user.setAdmin(rs.getBoolean(7));
        user.setLang(rs.getString(8));

        result.add(user);
      }
      rs.close();
      ps.close();
    } catch (SQLException e) {
      result = null;
    }
    return result;
  }

  @Override
  public User getUserById(int id) {
    User result = new User();

    String query = "SELECT * FROM users WHERE id=?";

    try {
      PreparedStatement ps = db.prepareStatement(query);
      ps.setString(1, id + "");

      ResultSet rs = ps.executeQuery();

      while (rs.next()) {
        result.setId(rs.getInt(1));
        result.setName(rs.getString(2));
        result.setSurnames(rs.getString(3));
        result.setEmail(rs.getString(4));
        result.setPhone(rs.getString(5));
        result.setPassword(rs.getString(6));
        result.setAdmin(rs.getBoolean(7));
        result.setLang(rs.getString(8));
      }
      rs.close();
      ps.close();
    } catch (SQLException e) {
      result = null;
    }
    return result;
  }

  @Override
  public boolean deleteUserById(int id) {
    int rows = 0;
    String query = "DELETE FROM users WHERE id=?";

    try {
      PreparedStatement ps = db.prepareStatement(query);
      ps.setInt(1, id);

      rows = ps.executeUpdate();

      db.commit();
      ps.close();
    } catch (SQLException e) {
      rows = 0;
    }
    return rows > 0;
  }

  @Override
  public User updateUser(User user) {
    int rows = 0;

    String query =
        "UPDATE users SET name=?, surnames=?, mail=?, phone=?, pass=?, role=?, lang=? WHERE id=?";

    try {
      PreparedStatement ps = db.prepareStatement(query);
      ps.setString(1, user.getName());
      ps.setString(2, user.getSurnames());
      ps.setString(3, user.getEmail());
      ps.setString(4, user.getPhone());
      ps.setString(5, user.getPassword());
      ps.setBoolean(6, user.isAdmin());
      ps.setString(7, user.getLang());
      ps.setInt(8, user.getId());

      rows = ps.executeUpdate();

      db.commit();
      ps.close();
    } catch (SQLException e) {
      rows = 0;
    }

    if (rows > 0) {
      return getUserById(user.getId());
    } else {
      return new User();
    }
  }
}
