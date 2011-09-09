/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import entities.Users;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author HP
 */
@Stateless
public class UsersFacade implements UsersFacadeRemote {

    @PersistenceContext(unitName = "mantech-ea-ejbPU")
    private EntityManager em;

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    private EntityManager getEntityManager() {
        return em;
    }

    @Override
    public void create(Users entity) {
        getEntityManager().persist(entity);
    }

    @Override
    public void edit(Users entity) {
        getEntityManager().merge(entity);
    }

    @Override
    public void remove(Users entity) {
        getEntityManager().remove(getEntityManager().merge(entity));
    }

    @Override
    public Users find(Object id) {
        return getEntityManager().find(Users.class, id);
    }

    @Override
    public List<Users> findAll() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(Users.class));
        return getEntityManager().createQuery(cq).getResultList();
    }

    @Override
    public List<Users> findRange(int[] range) {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(Users.class));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        q.setMaxResults(range[1] - range[0]);
        q.setFirstResult(range[0]);
        return q.getResultList();
    }

    @Override
    public int count() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        javax.persistence.criteria.Root<Users> rt = cq.from(Users.class);
        cq.select(getEntityManager().getCriteriaBuilder().count(rt));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }

    @Override
    public Users find(String userName) {
        try {
            Query q = getEntityManager().createNamedQuery("Users.findByUsername");
            q.setParameter("username", userName);
            Object result = q.getSingleResult();
            return (Users) result;
        } catch (NoResultException noReEx) {
        }

        return null;
    }

    @Override
    public List<Users> FindByDepartment(int departmentID) {
        try {
            Query q = getEntityManager().createNamedQuery("Users.findByDepartmentID");
            q.setParameter("departmentID", departmentID);
            return q.getResultList();
        } catch (NoResultException noReEx) {
        }
        return null;
    }

    @Override
    public Users findByUsername(String userName) {
        try {
            Query q = getEntityManager().createNamedQuery("Users.findByUsername");
            q.setParameter("username", userName);
            return (Users)q.getSingleResult();
        } catch (NoResultException noReEx) {
        }
        return null;
    }


}
