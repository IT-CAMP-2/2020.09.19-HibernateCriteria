package pl.camp.it.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import pl.camp.it.dao.IUserDAO;
import pl.camp.it.model.User;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
public class UserDAOImpl implements IUserDAO {

    @Autowired
    SessionFactory sessionFactory;

    @Override
    public void addUser(User user) {
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.save(user);
            tx.commit();
        } catch (Exception e) {
            if(tx != null){
                tx.rollback();
            }
        } finally {
            session.close();
        }
    }

    @Override
    public User getUserById(int id) {
        Session session = this.sessionFactory.openSession();
        Query<User> query = session
                .createQuery("FROM pl.camp.it.model.User WHERE id = :id");
        query.setParameter("id", id);
        User result = query.getSingleResult();
        session.close();
        return result;
    }

    @Override
    public User getUserByIdCriteria(int id) {
        Session session = this.sessionFactory.openSession();

        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<User> criteriaQuery = builder.createQuery(User.class);

        Root<User> userRoot = criteriaQuery.from(User.class);

        criteriaQuery.select(userRoot);

        //metoda(builder, criteriaQuery);

        criteriaQuery.where(builder.equal(userRoot.get("id"), id));

        //metoda2(builder, criteriaQuery);

        //criteriaQuery.groupBy()

        Query<User> query = session.createQuery(criteriaQuery);
        User result = query.getSingleResult();
        session.close();
        return result;
    }

    @Override
    public List<User> getUserByCriteria(CriteriaWrapper criteriaWrapper) {
        Query<User> query = criteriaWrapper.getSession()
                .createQuery(criteriaWrapper.getCriteriaQuery());
        List<User> result = query.getResultList();
        criteriaWrapper.getSession().close();
        return result;
    }

    public CriteriaWrapper getCriteriaQuery() {
        CriteriaWrapper criteriaWrapper = new CriteriaWrapper();

        criteriaWrapper.setSession(this.sessionFactory.openSession());
        criteriaWrapper.setCriteriaBuilder(criteriaWrapper.getSession()
                .getCriteriaBuilder());
        criteriaWrapper.setCriteriaQuery(criteriaWrapper.getCriteriaBuilder()
                .createQuery(User.class));
        criteriaWrapper.setRoot(criteriaWrapper.getCriteriaQuery().from(User.class));

        return criteriaWrapper;
    }

    public class CriteriaWrapper {

        /*public CriteriaWrapper() {
            session = sessionFactory.openSession();
            criteriaBuilder = sessionFactory.getCriteriaBuilder();
            criteriaQuery = criteriaBuilder.createQuery(User.class);
            root = criteriaQuery.from(User.class);
        }*/

        private Root<User> root;
        private CriteriaBuilder criteriaBuilder;
        private Session session;
        private CriteriaQuery criteriaQuery;

        public Root<User> getRoot() {
            return root;
        }

        public void setRoot(Root<User> root) {
            this.root = root;
        }

        public CriteriaBuilder getCriteriaBuilder() {
            return criteriaBuilder;
        }

        public void setCriteriaBuilder(CriteriaBuilder criteriaBuilder) {
            this.criteriaBuilder = criteriaBuilder;
        }

        public Session getSession() {
            return session;
        }

        public void setSession(Session session) {
            this.session = session;
        }

        public CriteriaQuery getCriteriaQuery() {
            return criteriaQuery;
        }

        public void setCriteriaQuery(CriteriaQuery criteriaQuery) {
            this.criteriaQuery = criteriaQuery;
        }
    }
}
