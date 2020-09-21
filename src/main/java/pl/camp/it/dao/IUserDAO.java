package pl.camp.it.dao;

import pl.camp.it.dao.impl.UserDAOImpl;
import pl.camp.it.model.User;

import java.util.List;

public interface IUserDAO {
    void addUser(User user);
    User getUserById(int id);
    User getUserByIdCriteria(int id);
    List<User> getUserByCriteria(UserDAOImpl.CriteriaWrapper criteriaWrapper);
    UserDAOImpl.CriteriaWrapper getCriteriaQuery();
}
