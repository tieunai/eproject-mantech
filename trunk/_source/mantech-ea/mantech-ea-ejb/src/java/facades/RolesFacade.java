/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package facades;

import entities.Roles;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author HP
 */
@Stateless
public class RolesFacade implements RolesFacadeRemote {
    @PersistenceContext(unitName = "mantech-ea-ejbPU")
    private EntityManager em;

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")

    private EntityManager getEntityManager(){
        return em;
    }

    @Override
    public void create(Roles entity) {
        getEntityManager().persist(entity);
    }

    @Override
    public void edit(Roles entity) {
        getEntityManager().merge(entity);
    }

    @Override
    public void remove(Roles entity) {
        getEntityManager().remove(getEntityManager().merge(entity));
    }

    @Override
    public Roles find(Object id) {
        return getEntityManager().find(Roles.class, id);
    }

    @Override
    public List<Roles> findAll() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(Roles.class));
        return getEntityManager().createQuery(cq).getResultList();
    }

    @Override
    public List<Roles> findRange(int[] range) {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(Roles.class));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        q.setMaxResults(range[1] - range[0]);
        q.setFirstResult(range[0]);
        return q.getResultList();
    }

    @Override
    public int count() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        javax.persistence.criteria.Root<Roles> rt = cq.from(Roles.class);
        cq.select(getEntityManager().getCriteriaBuilder().count(rt));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }
}
