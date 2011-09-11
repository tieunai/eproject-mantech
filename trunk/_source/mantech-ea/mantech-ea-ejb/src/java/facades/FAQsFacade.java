/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import entities.FAQs;
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
public class FAQsFacade implements FAQsFacadeRemote {

    @PersistenceContext(unitName = "mantech-ea-ejbPU")
    private EntityManager em;

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    private EntityManager getEntityManager() {
        return em;
    }

    @Override
    public void create(FAQs entity) {
        getEntityManager().persist(entity);
    }

    @Override
    public void edit(FAQs entity) {
        getEntityManager().merge(entity);
    }

    @Override
    public void remove(FAQs entity) {
        getEntityManager().remove(getEntityManager().merge(entity));
    }

    @Override
    public FAQs find(Object id) {
        return getEntityManager().find(FAQs.class, id);
    }

    @Override
    public List<FAQs> findAll() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(FAQs.class));
        return getEntityManager().createQuery(cq).getResultList();
    }

    @Override
    public List<FAQs> findRange(int[] range) {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(FAQs.class));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        q.setMaxResults(range[1] - range[0]);
        q.setFirstResult(range[0]);
        return q.getResultList();
    }

    @Override
    public int count() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        javax.persistence.criteria.Root<FAQs> rt = cq.from(FAQs.class);
        cq.select(getEntityManager().getCriteriaBuilder().count(rt));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }

    @Override
    public List<FAQs> emplFindAll() {
        try {
            Query q = getEntityManager().createNamedQuery("FAQs.emplfindAll");
            return q.getResultList();
        } catch (NoResultException noReEx) {
        }
        return null;
    }
}
