package eventrev.net.dao;

import eventrev.net.patterns.Manager;
import eventrev.net.model.User;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.List;

public class UserDAO {
    public User add(User user){
        EntityManager entityManager = Manager.getEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(user);
        entityManager.getTransaction().commit();
        entityManager.close();
        return user;
    }

    public User findUser(int id) {
        EntityManager entityManager = Manager.getEntityManager();
        User user = entityManager.find(User.class, id);
        entityManager.close();
        return user;
    }

    public User findByEmail(String email){
        EntityManager entityManager = Manager.getEntityManager();
        User user;
        try {
            user = entityManager.createQuery("FROM User u WHERE u.email = :email", User.class).setParameter("email", email).getSingleResult();
        } catch (NoResultException e){
            entityManager.close();
            return null;
        }
        entityManager.close();
        return user;
    }


    public List<User> findAllUsers() {
        EntityManager entityManager = Manager.getEntityManager();
        TypedQuery<User> query = entityManager.createQuery("SELECT u FROM User u", User.class);
        List<User> user = query.getResultList();
        entityManager.close();
        return user;
    }


    public Boolean removeUser(int id) {

        EntityManager entityManager = Manager.getEntityManager();
        User user = entityManager.find(User.class, id);
        if (user != null) {
            entityManager.getTransaction().begin();
            entityManager.remove(user);
            entityManager.getTransaction().commit();
            entityManager.close();
            return true;
        }
        return false;
    }
}
