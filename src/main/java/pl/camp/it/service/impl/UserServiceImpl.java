package pl.camp.it.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.camp.it.dao.IUserDAO;
import pl.camp.it.dao.impl.UserDAOImpl;
import pl.camp.it.model.User;
import pl.camp.it.service.IUserService;

import javax.persistence.criteria.Root;
import java.util.List;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    IUserDAO userDAO;

    @Override
    public void addUser(User user) {
        this.userDAO.addUser(user);
    }

    @Override
    public User getUserById(int id) {
        return this.userDAO.getUserById(id);
    }

    @Override
    public User getUserByIdCriteria(int id) {
        return this.userDAO.getUserByIdCriteria(id);
    }

    @Override
    public List<User> getUserByCriteria() {
        UserDAOImpl.CriteriaWrapper criteriaWrapper = userDAO.getCriteriaQuery();
        criteriaWrapper.getCriteriaQuery().select(criteriaWrapper.getRoot());

        criteriaWrapper.getCriteriaQuery().where(
                criteriaWrapper
                        .getCriteriaBuilder()
                        .lt(criteriaWrapper.getRoot().get("id"), 2)
        );

        setLoginLimits(criteriaWrapper);

        return this.userDAO.getUserByCriteria(criteriaWrapper);
    }

    private void setLoginLimits(UserDAOImpl.CriteriaWrapper criteriaWrapper) {

        criteriaWrapper.getCriteriaQuery().where(
                criteriaWrapper
                        .getCriteriaBuilder()
                        .like(criteriaWrapper.getRoot().get("login"), "%mac%")
        );
    }
}
