/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import entities.Users_Roles;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author HP
 */
@Stateless
public class Roles_UsersFacade implements Roles_UsersFacadeRemote {

    @PersistenceContext(unitName = "mantech-ea-ejbPU")
    private EntityManager em;

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    private EntityManager getEntityManager() {
        return em;
    }

    @Override
    public void create(Users_Roles entity) {
        getEntityManager().persist(entity);
    }
}
