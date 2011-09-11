/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package facades;

import entities.Departments;
import entities.Roles;
import entities.Users;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author HP
 */
@Remote
public interface UsersFacadeRemote {
    void create(Users entity);

    void edit(Users entity);

    void remove(Users entity);

    Users find(Object id);

    List<Users> findAll();

    List<Users> findRange(int[] range);

    int count();

    Users find(String userName);

    List<Users> findByDepartment(Departments department);

    List<Users> findByRole(Roles role);

    Users findByUsername(String userName);
}
