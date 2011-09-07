/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package facades;

import entities.Roles;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author HP
 */
@Remote
public interface RolesFacadeRemote {
    void create(Roles entity);

    void edit(Roles entity);

    void remove(Roles entity);

    Roles find(Object id);

    List<Roles> findAll();

    List<Roles> findRange(int[] range);

    int count();
}
