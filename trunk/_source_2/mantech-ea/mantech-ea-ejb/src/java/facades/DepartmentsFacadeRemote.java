/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package facades;

import entities.Departments;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author HP
 */
@Remote
public interface DepartmentsFacadeRemote {
    void create(Departments entity);

    void edit(Departments entity);

    void remove(Departments entity);

    Departments find(Object id);

    List<Departments> findAll();

    List<Departments> findRange(int[] range);

    int count();
}
