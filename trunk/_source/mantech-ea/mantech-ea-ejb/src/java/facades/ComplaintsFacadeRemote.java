/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package facades;

import entities.Complaints;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author HP
 */
@Remote
public interface ComplaintsFacadeRemote {
    void create(Complaints entity);

    void edit(Complaints entity);

    void remove(Complaints entity);

    Complaints find(Object id);

    List<Complaints> findAll();

    List<Complaints> findRange(int[] range);

    int count();

    List<Complaints> findByPriority(int priority);

    List<Complaints> findByUserID(int userID);
}
