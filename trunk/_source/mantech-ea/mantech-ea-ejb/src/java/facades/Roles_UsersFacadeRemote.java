/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package facades;

import entities.Users_Roles;
import javax.ejb.Remote;

/**
 *
 * @author HP
 */
@Remote
public interface Roles_UsersFacadeRemote {

    void create(Users_Roles rolesUsers);
    
}
