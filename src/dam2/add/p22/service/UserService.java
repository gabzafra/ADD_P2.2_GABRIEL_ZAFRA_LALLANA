package dam2.add.p22.service;

import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import org.apache.log4j.Logger;
import org.jasypt.util.password.StrongPasswordEncryptor;
import dam2.add.p22.dao.UserDAO;
import dam2.add.p22.model.User;

public class UserService {
  private static UserService instance;
  private static final UserDAO USER_DAO = PersistenceService.getUserDAO();
  private static Logger log = Logger.getLogger("generic");
  private static StrongPasswordEncryptor crypt = new StrongPasswordEncryptor();
  private static LangService langSrv = LangService.getInstance();

  private UserService() {

  }

  public static UserService getInstance() {
    if (instance == null) {
      instance = new UserService();
    }
    return instance;
  }

  public String setUserDefaultLang(int authId, int userId, String newlang) {
    String error = "";
    User user = null;
    if (userId != authId) {
      error = "err_not_authorised";
    } else {
      user = USER_DAO.getUserById(userId);
      if (user.getId() <= 0) {
        error = "err_unknown_user";
      } else if (!user.getLang().equals(newlang)) {
        user.setLang(newlang);
        user = updateUser(user);
        if (user.getId() <= 0) {
          error = "err_no_lang_update";
        }
      }
    }
    if (error.length() > 0) {
      log.error(langSrv.getLocalError(error));
    } else {
      log.info(langSrv.getLocalError("info_upd_user") + user.getId());
    }
    return error;
  }

  public User getUserById(String id) {
    int parId = parseId(id);
    if (parId > 0) {
      User user = USER_DAO.getUserById(parId);
      user.setPassword("");
      return user;
    } else {
      return null;
    }
  }

  public User getUserById(int id) {
    if (id > 0) {
      User user = USER_DAO.getUserById(id);
      user.setPassword("");
      return user;
    } else {
      return null;
    }
  }

  public User getUserByMail(String email) {
    User user = USER_DAO.getAllUsers().stream().filter(u -> u.getEmail().equals(email)).findFirst()
        .orElse(new User());
    user.setPassword("");
    return user;
  }

  public List<User> getNonAdminUsers() {
    List<User> userList = USER_DAO.getAllUsers();
    return userList = userList.stream().filter(user -> !user.isAdmin()).map(user -> {
      user.setPassword("");
      return user;
    }).collect(Collectors.toList());
  }

  public List<User> getAllUsers() {
    List<User> userList = USER_DAO.getAllUsers();
    userList.forEach(user -> user.setPassword(""));
    return userList;
  }

  public List<User> getUsersFilteredByStart(String str) {
    return getNonAdminUsers().parallelStream()
        .filter(user -> user.getName().toLowerCase().startsWith(str.trim().toLowerCase()))
        .collect(Collectors.toList());
  }

  public boolean deleteUserById(String id) {
    int parId = parseId(id);
    return parId > 0 ? USER_DAO.deleteUserById(parId) : false;
  }

  public User createUser(User user) {
    boolean emailAlrdyExists =
        !USER_DAO.getAllUsers().stream().noneMatch(item -> item.getEmail().equals(user.getEmail()));
    if (emailAlrdyExists) {
      return new User();
    } else {
      user.setPassword(crypt.encryptPassword(user.getPassword()));
      User newUser = USER_DAO.createUser(user);
      newUser.setPassword("");
      return newUser;
    }

  }

  public User updateUser(User user) {
    if (USER_DAO.getUserById(user.getId()) != null) {
      User updUser = USER_DAO.updateUser(user);
      updUser.setPassword("");
      return updUser;
    } else {
      return user;
    }
  }

  private static boolean logUserIn(int id, String pass) {
    User user = USER_DAO.getUserById(id);
    return crypt.checkPassword(pass, user.getPassword());
  }

  public int parseId(String id) {
    int result;
    try {
      result = Integer.parseInt(id);
    } catch (NumberFormatException e) {
      result = -1;
    }
    return result;
  }

  public static boolean hasEmptyFields(User user) {
    System.out.println(user.toString());
    return user.getName().equals("") || user.getSurnames().equals("") || user.getEmail().equals("")
        || user.getPhone().equals("") || user.getPassword().equals("")
        || user.getProvince().equals("") || user.getTown().equals("");
  }

  public String validateLogin(String email, String pass, String lang) {
    String error = "";
    User user = null;
    if (!isValidEmail(email)) {
      error = "err_invalid_mail";
    } else {
      user = getUserByMail(email);
      if (user.getId() <= 0) {
        error = "err_unknown_user";
      } else if (!logUserIn(user.getId(), pass)) {
        error = "err_invalid_pass";
      } else {
        if (lang != null) {
          error = setUserDefaultLang(user.getId(), user.getId(), lang);
        }
      }
    }

    if (error.length() > 0) {
      log.error(langSrv.getLocalError(error));
    } else {
      log.info(langSrv.getLocalError("info_logged_in") + user.getId());
    }
    return error;
  }

  public String validateRegister(User user, String pass2) {
    String error = "";
    User newUser = null;
    if (!user.getPassword().equals(pass2)) {
      error = "err_not_same_pw";
    } else if (hasEmptyFields(user)) {
      error = "err_no_empty_fields";
    } else if (!isValidEmail(user.getEmail())) {
      error = "err_invalid_mail";
    } else if (getUserByMail(user.getEmail()).getId() > 0) {
      error = "err_unavailable_email";
    } else {
      newUser = createUser(user);
      if (newUser.getId() <= 0) {
        error = "err_create_user";
      }
    }
    if (error.length() > 0) {
      log.error(langSrv.getLocalError(error));
    } else {
      log.info(langSrv.getLocalError("info_new_user") + newUser.getId());
    }
    return error;
  }

  public String validateUpdateFields(User userData, int idAuth) {
    String error = "";
    User newUser = null;
    if (userData.getName().equals("") || userData.getSurnames().equals("")
        || userData.getEmail().equals("") || userData.getPhone().equals("")
        || userData.getProvince().equals("") || userData.getTown().equals("")) {
      error = "err_no_empty_fields";
    } else if (!isValidEmail(userData.getEmail())) {
      error = "err_invalid_mail";
    } else {
      String oldMail = getUserById(userData.getId()).getEmail();
      String newEmail = userData.getEmail();
      if (!oldMail.equals(newEmail) && getUserByMail(newEmail).getId() > 0) {
        error = "err_unavailable_email";
      } else {
        User authUser = getUserById(idAuth);
        if (authUser.getId() == userData.getId() || authUser.isAdmin()) {
          User oldUser = USER_DAO.getUserById(userData.getId());
          oldUser.setName(userData.getName());
          oldUser.setEmail(userData.getEmail());
          oldUser.setSurnames(userData.getSurnames());
          oldUser.setPhone(userData.getPhone());
          oldUser.setProvince(userData.getProvince());
          oldUser.setTown(userData.getTown());

          newUser = updateUser(oldUser);
          if (newUser.getId() <= 0) {
            error = "err_update_user";
          }
        } else {
          error = "err_not_authorised";
        }
      }
    }
    if (error.length() > 0) {
      log.error(langSrv.getLocalError(error));
    } else {
      log.info(langSrv.getLocalError("info_upd_user") + newUser.getId());
    }

    return error;
  }

  public String validateDeleteUser(User user, int idAuth) {
    String error = "";
    if (getUserById(idAuth).isAdmin()) {
      error = deleteUserById(user.getId() + "") ? "" : "err_delete_user";
    }
    if (error.length() > 0) {
      log.error(langSrv.getLocalError(error));
    } else {
      log.info(langSrv.getLocalError("info_del_user") + user.getId());
    }
    return error;
  }

  public String changePass(int idAuth, String newPass, String oldPass) {
    String error = "";
    User user = null;
    if (newPass.length() <= 0 || oldPass.length() <= 0) {
      error = "err_no_empty_fields";
    } else {
      user = USER_DAO.getUserById(idAuth);
      if (user.getId() <= 0) {
        error = "err_unknown_user";
      } else if (!crypt.checkPassword(oldPass, user.getPassword())) {
        error = "err_invalid_pass";
      } else {
        user.setPassword(crypt.encryptPassword(newPass));
        if (updateUser(user).getId() <= 0) {
          error = "err_update_user";
        }
      }
    }
    if (error.length() > 0) {
      log.error(langSrv.getLocalError(error));
    } else {
      log.info(langSrv.getLocalError("info_upd_pass") + user.getId());
    }
    return error;
  }


  public boolean isValidEmail(String mail) {
    String regex =
        "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
    return Pattern.compile(regex).matcher(mail).matches();
  }
}
