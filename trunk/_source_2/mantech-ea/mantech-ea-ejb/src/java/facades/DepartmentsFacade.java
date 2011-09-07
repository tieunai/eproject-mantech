/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package facades;

import entities.Departments;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author HP
 */
@Stateless
public class DepartmentsFacade implements DepartmentsFacadeRemote {
    @PersistenceContext(unitName = "mantech-ea-ejbPU")
    private EntityManager em;

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")

    private EntityManager getEntityManager(){
        return em;
    }

    @Override
    public void create(Departments entity) {
        getEntityManager().persist(entity);
    }

    @Override
    public void edit(Departments entity) {
        getEntityManager().merge(entity);
    }

    @Override
    public void remove(Departments entity) {
        getEntityManager().remove(getEntityManager().merge(entity));
    }

    @Override
    public Departments find(Object id) {
        return getEntityManager().find(Departments.class, id);
    }

    @Override
    public List<Departments> findAll() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(Departments.class));
        return getEntityManager().createQuery(cq).getResultList();
    }

    @Override
    public List<Departments> findRange(int[] range) {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(Departments.class));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        q.setMaxResults(range[1] - range[0]);
        q.setFirstResult(range[0]);
        return q.getResultList();
    }

    @Override
    public int count() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        javax.persistence.criteria.Root<Departments> rt = cq.from(Departments.class);
        cq.select(getEntityManager().getCriteriaBuilder().count(rt));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }
}
