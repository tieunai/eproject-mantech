/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package facades;

import entities.Departments;
import entities.Threads;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author HP
 */
@Remote
public interface ThreadsFacadeRemote {
    void create(Threads entity);

    void edit(Threads entity);

    void remove(Threads entity);

    Threads find(Object id);

    List<Threads> findAll();

    List<Threads> findRange(int[] range);

    int count();

    List<Threads> findByDepartment(Departments department);
}
