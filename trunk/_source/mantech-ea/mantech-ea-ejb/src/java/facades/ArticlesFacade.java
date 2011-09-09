/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package facades;

import entities.Articles;
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
public class ArticlesFacade implements ArticlesFacadeRemote {
    @PersistenceContext(unitName = "mantech-ea-ejbPU")
    private EntityManager em;

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")

    private EntityManager getEntityManager(){
        return em;
    }

    @Override
    public void create(Articles entity) {
        getEntityManager().persist(entity);
    }

    @Override
    public void edit(Articles entity) {
        getEntityManager().merge(entity);
    }

    @Override
    public void remove(Articles entity) {
        getEntityManager().remove(getEntityManager().merge(entity));
    }

    @Override
    public Articles find(Object id) {
        return getEntityManager().find(Articles.class, id);
    }

    @Override
    public List<Articles> findAll() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(Articles.class));
        return getEntityManager().createQuery(cq).getResultList();
    }

    @Override
    public List<Articles> findRange(int[] range) {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(Articles.class));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        q.setMaxResults(range[1] - range[0]);
        q.setFirstResult(range[0]);
        return q.getResultList();
    }

    @Override
    public int count() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        javax.persistence.criteria.Root<Articles> rt = cq.from(Articles.class);
        cq.select(getEntityManager().getCriteriaBuilder().count(rt));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }

    @Override
    public List<Articles> findByEnable(Boolean isEnable) {
        try {
            Query q = getEntityManager().createNamedQuery("Articles.findByIsEnable");
            q.setParameter("isEnable",isEnable );
            return q.getResultList();
        } catch (NoResultException noReEx) {
        }
        return null;
    }

    @Override
    public List<Articles> emplFindAll() {
        try {
            Query q = getEntityManager().createNamedQuery("Articles.emplFindAll");
            return q.getResultList();
        } catch (NoResultException noReEx) {
        }
        return null;
    }
}
