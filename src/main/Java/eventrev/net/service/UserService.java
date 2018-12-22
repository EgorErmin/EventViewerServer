package eventrev.net.service;

import eventrev.net.dao.UserDAO;
import eventrev.net.model.User;

import java.util.List;

public class UserService {

    private UserDAO userDAO = new UserDAO();

    public User add(User user){
        return userDAO.add(user);
    }

    public User find(Integer id){
        return userDAO.findUser(id);
    }

    public User findByEmail(String email){
        return userDAO.findByEmail(email);
    }

    public List<User> findAll(){
        return userDAO.findAllUsers();
    }

    public Boolean remove(Integer id){
        return userDAO.removeUser(id);
    }
}
