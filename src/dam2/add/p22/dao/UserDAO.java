package dam2.add.p22.dao;

import java.util.List;
import dam2.add.p22.model.User;

public interface UserDAO {
  public User createUser(User user);

  public List<User> getAllUsers();

  public User getUserById(int id);

  public boolean deleteUserById(int id);

  public User updateUser(User user);
}
