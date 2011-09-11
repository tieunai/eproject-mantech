/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import entities.Complaints;
import entities.Threads;
import entities.Users;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TemporalType;

/**
 *
 * @author HP
 */
@Stateless
public class ComplaintsFacade implements ComplaintsFacadeRemote {

    @PersistenceContext(unitName = "mantech-ea-ejbPU")
    private EntityManager em;

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    private EntityManager getEntityManager() {
        return em;
    }

    @Override
    public void create(Complaints entity) {
        getEntityManager().persist(entity);
    }

    @Override
    public void edit(Complaints entity) {
        getEntityManager().merge(entity);
    }

    @Override
    public void remove(Complaints entity) {
        getEntityManager().remove(getEntityManager().merge(entity));
    }

    @Override
    public Complaints find(Object id) {
        Query q = getEntityManager().createNamedQuery("Complaints.findByComplaintID");
        q.setParameter("complaintID", id);
        return (Complaints) q.getSingleResult();
    }

    @Override
    public List<Complaints> findAll() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(Complaints.class));
        return getEntityManager().createQuery(cq).getResultList();
    }

    @Override
    public List<Complaints> findRange(int[] range) {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(Complaints.class));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        q.setMaxResults(range[1] - range[0]);
        q.setFirstResult(range[0]);
        return q.getResultList();
    }

    @Override
    public int count() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        javax.persistence.criteria.Root<Complaints> rt = cq.from(Complaints.class);
        cq.select(getEntityManager().getCriteriaBuilder().count(rt));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }

    @Override
    public List<Complaints> findByPriority(int priority) {
        try {
            Query q = getEntityManager().createNamedQuery("Complaints.findByPriority");
            q.setParameter("priority", priority);
            return q.getResultList();
        } catch (NoResultException noReEx) {
        }
        return null;
    }

    @Override
    public List<Complaints> findByUserID(Users user) {
        try {
            Query q = getEntityManager().createNamedQuery("Complaints.findByUserID");
            q.setParameter("userID", user);
            return q.getResultList();
        } catch (NoResultException noReEx) {
        }
        return null;
    }

    @Override
    public List<Complaints> findByUserRef(Users userRef) {
        try {
            Query q = getEntityManager().createNamedQuery("Complaints.findByUserRef");
            q.setParameter("userRef", userRef);
            return q.getResultList();
        } catch (NoResultException noReEx) {
        }
        return null;
    }

    @Override
    public List<Complaints> emplFindByPriority(int priority) {
        try {
            Query q = getEntityManager().createNamedQuery("Complaints.emplFindByPriority");
            q.setParameter("priority", priority);
            return q.getResultList();
        } catch (NoResultException noReEx) {
        }
        return null;
    }

    @Override
    public List<Complaints> emplFindByUserID(Users user) {
        try {
            Query q = getEntityManager().createNamedQuery("Complaints.emplFindByUserID");
            q.setParameter("userID", user);
            return q.getResultList();
        } catch (NoResultException noReEx) {
        }
        return null;
    }

    @Override
    public List<Complaints> emplFindByUserRef(Users userRef) {
        try {
            Query q = getEntityManager().createNamedQuery("Complaints.emplFindByUserRef");
            q.setParameter("userRef", userRef);
            return q.getResultList();
        } catch (NoResultException noReEx) {
        }
        return null;
    }

    @Override
    public List<Complaints> findByThread(Threads thread) {
        try {
            Query q = getEntityManager().createNamedQuery("Complaints.findByThreadID");
            q.setParameter("threadID", thread);
            return q.getResultList();
        } catch (NoResultException noReEx) {
        }
        return null;
    }

    @Override
    public List<Complaints> findBetweenTime(int threadID, String start, String end) {
        try {
            Query q = getEntityManager().createNamedQuery("Complaints.findByBetweenTime");
            q.setParameter("threadID", threadID);
            q.setParameter("start", start);
            q.setParameter("end", end);
            return q.getResultList();
        } catch (NoResultException noReEx) {
        }
        return null;
    }


    @Override
    public List<Complaints> findBetweenTime(String start, String end) {
        try {
            Query q = getEntityManager().createNamedQuery("Complaints.findByBetweenTime2");
            q.setParameter("start", start);
            q.setParameter("end", end);
            return q.getResultList();
        } catch (NoResultException noReEx) {
        }
        return null;
    }
}
