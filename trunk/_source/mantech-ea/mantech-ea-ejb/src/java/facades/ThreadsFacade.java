/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package facades;

import entities.Threads;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author HP
 */
@Stateless
public class ThreadsFacade implements ThreadsFacadeRemote {
    @PersistenceContext(unitName = "mantech-ea-ejbPU")
    private EntityManager em;

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")

    private EntityManager getEntityManager(){
        return em;
    }

    @Override
    public void create(Threads entity) {
        getEntityManager().persist(entity);
    }

    @Override
    public void edit(Threads entity) {
        getEntityManager().merge(entity);
    }

    @Override
    public void remove(Threads entity) {
        getEntityManager().remove(getEntityManager().merge(entity));
    }

    @Override
    public Threads find(Object id) {
        return getEntityManager().find(Threads.class, id);
    }

    @Override
    public List<Threads> findAll() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(Threads.class));
        return getEntityManager().createQuery(cq).getResultList();
    }

    @Override
    public List<Threads> findRange(int[] range) {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(Threads.class));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        q.setMaxResults(range[1] - range[0]);
        q.setFirstResult(range[0]);
        return q.getResultList();
    }

    @Override
    public int count() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        javax.persistence.criteria.Root<Threads> rt = cq.from(Threads.class);
        cq.select(getEntityManager().getCriteriaBuilder().count(rt));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }
}
