package pl.camp.it.service;

import pl.camp.it.dao.impl.UserDAOImpl;
import pl.camp.it.model.User;

import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

public interface IUserService {
    void addUser(User user);
    User getUserById(int id);
    User getUserByIdCriteria(int id);
    List<User> getUserByCriteria();
}
