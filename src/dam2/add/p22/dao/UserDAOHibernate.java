package dam2.add.p22.dao;

import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.resource.transaction.spi.TransactionStatus;
import dam2.add.p22.HibernateManager;
import dam2.add.p22.model.User;

public class UserDAOHibernate implements UserDAO {
  private static UserDAOHibernate instance;
  private static SessionFactory sessionFactory;

  private UserDAOHibernate() {
    sessionFactory = HibernateManager.getSessionFactory();
  }

  public static UserDAOHibernate getInstance() {
    if (instance == null) {
      instance = new UserDAOHibernate();
    }
    return instance;
  }

  @Override
  public User createUser(User user) {
    Session session = sessionFactory.openSession();
    Transaction tx = session.beginTransaction();
    // Integer id = (Integer)session.save(user);
    session.persist(user);
    tx.commit();
    session.close();
    return user;
  }

  @SuppressWarnings("unchecked")
  @Override
  public List<User> getAllUsers() {
    Session session = sessionFactory.openSession();

    List<User> userList;
    try {
      String query = "FROM users";
      userList = (List<User>) session.createQuery(query).list();
    } catch (HibernateException e) {
      session.getTransaction().rollback();
      userList = null;
    }
    return userList;
  }

  @Override
  public User getUserById(int id) {
    Session session = sessionFactory.openSession();
    User user = (User) session.get(User.class, id);
    session.close();
    return user;
  }

  @Override
  public boolean deleteUserById(int id) {
    Session session = sessionFactory.openSession();
    Transaction tx = session.beginTransaction();
    User user = getUserById(id);
    if (user != null) {
      session.remove(user);
      tx.commit();
    } else {
      tx.rollback();
    }
    TransactionStatus status = tx.getStatus();
    session.close();
    return status == TransactionStatus.COMMITTED;
  }

  @Override
  public User updateUser(User user) {
    Session session = sessionFactory.openSession();
    Transaction tx = session.beginTransaction();
    session.merge(user);
    tx.commit();
    session.close();
    return user;
  }

}
