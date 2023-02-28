package dam2.add.p22.dao;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import dam2.add.p22.model.User;

public class UserDAOinMem implements UserDAO {
  private static UserDAOinMem instance;
  private HashMap<Integer, User> userList;
  private int id;

  private UserDAOinMem() {
    userList = new HashMap<Integer, User>();
    id = 0;

    createUser(new User("Admin", "The Admin", "admin@mail.com", "000000000",
        "TpBOWO9Z9l0vw3b6ICMt2ha9vz/OoLp40gDxRs/LSPCWzEeWYehRhXQhInrjuHgt", true, "es"));
    createUser(new User("Adam", "Alda Almirante", "adam@mail.com", "666554433",
        "ZoglyH60ViSdTsKMBvF8iZ1uOlYMSKuJ+wv90W8VrsMihjNmaKcfJ6eS5paIU3ph", false, "en"));
    createUser(new User("Betty", "Bueno Baños", "betty@mail.com", "555443322",
        "+RkUhOfPBRrtVhw2ENAkxj+7gmCI/K2bQEa/nwHTm88605SnbwNs1xXijIEno0qS", false, "es"));
    createUser(new User("Charlie", "Corral Casar", "charlie@mail.com", "444332211",
        "4KiTMULo5Oqj6Ysz4U1Z2Xw5QMmzinZrL/60cH9N2PB5sHkm/TVV9T/GWN0bgJnz", false, "es"));
    createUser(new User("Cecil", "Cinta Coso", "cecil@mail.com", "420332811",
        "+5DAZC0gLmFuWMPf/1Fyyay8IktYj8x5gfUOvmX19ukNsIc0MLsATwcjfobiClDK", false, "es"));
    createUser(new User("Diane", "Dueñas Donoso", "diane@mail.com", "333221100",
        "Y6op3BH0iagkccymeVTibelnHmJBRU03m0FSQtgVAC7belL1bUJwPW/kBrqKEoCb", false, "en"));
    createUser(new User("Eric", "Estepa España", "eric@mail.com", "111223344",
        "6+3SMSF0VZ5I1OcBaEjpaBih05Z/KarvUPbIe1Ugcy+xzO2FXzW4dJArBBvxepkr", false, "es"));
  }

  public static UserDAOinMem getInstance() {
    if (instance == null) {
      instance = new UserDAOinMem();
    }
    return instance;
  }

  private int getNewId() {
    id++;
    return id;
  }

  private static User cloneUser(User c) {
    return new User(c.getId(), c.getName(), c.getSurnames(), c.getEmail(), c.getPhone(),
        c.getPassword(), c.isAdmin(), c.getLang());
  }

  @Override
  public User createUser(User contact) {
    contact.setId(getNewId());
    if (!userList.containsKey(contact.getId())) {
      userList.put(contact.getId(), contact);
      return cloneUser(userList.get(contact.getId()));
    } else {
      return new User();
    }
  }

  @Override
  public List<User> getAllUsers() {
    return userList.values().stream().map(UserDAOinMem::cloneUser).collect(Collectors.toList());
  }

  @Override
  public User getUserById(int id) {
    User foundUser = userList.get(id);
    return foundUser != null ? cloneUser(foundUser) : new User();
  }

  @Override
  public boolean deleteUserById(int id) {
    return userList.remove(id) != null;
  }

  @Override
  public User updateUser(User contact) {
    userList.replace(contact.getId(), contact);
    User updatedUser = getUserById(contact.getId());
    return updatedUser != null ? cloneUser(updatedUser) : new User();
  }
}
