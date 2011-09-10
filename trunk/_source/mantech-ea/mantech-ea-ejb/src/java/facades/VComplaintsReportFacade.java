/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package facades;

import entities.Departments;
import entities.VcomplaintsReport;
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
public class VComplaintsReportFacade implements VComplaintsReportFacadeRemote {
    
    @PersistenceContext(unitName = "mantech-ea-ejbPU")
    private EntityManager em;

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")

    private EntityManager getEntityManager(){
        return em;
    }


    @Override
    public VcomplaintsReport find(Object id) {
        return getEntityManager().find(VcomplaintsReport.class, id);
    }

    @Override
    public List<VcomplaintsReport> findAll(Departments department) {
        try {
            Query q = getEntityManager().createNamedQuery("VcomplaintsReport.findByDepartmentID");
            q.setParameter("departmentID", department.getDepartmentID());
            return q.getResultList();
        } catch (NoResultException noReEx) {
        }
        return null;
    }

    @Override
    public List<VcomplaintsReport> findAll(Departments department, Date start, Date end) {
        try {
            Query q = getEntityManager().createNamedQuery("VcomplaintsReport.findByDepartmentID");
            q.setParameter("departmentID", department.getDepartmentID());
            q.setParameter("start", start, TemporalType.DATE);
            q.setParameter("end", end, TemporalType.DATE);
            return q.getResultList();
        } catch (NoResultException noReEx) {
        }
        return null;
    }

    @Override
    public int count() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        javax.persistence.criteria.Root<VcomplaintsReport> rt = cq.from(VcomplaintsReport.class);
        cq.select(getEntityManager().getCriteriaBuilder().count(rt));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }
 
}
