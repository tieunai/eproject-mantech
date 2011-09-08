/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author HP
 */
@Entity
@Table(name = "Roles_Users", catalog = "MantechHelpdesk", schema = "dbo")
public class Users_Roles implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "RoleID", nullable = false)
    private Integer roleID;
    @Basic(optional = false)
    @Column(name = "UserID", nullable = false)
    private Integer userID;

    public Users_Roles() {
    }

    public Integer getRoleID() {
        return roleID;
    }

    public void setRoleID(Integer roleID) {
        this.roleID = roleID;
    }

    public Integer getUserID() {
        return userID;
    }

    public void setUserID(Integer userID) {
        this.userID = userID;
    }

    public Users_Roles(int roleID, int userID){
        this.roleID = roleID;
        this.userID = userID;
    }

    @Override
    public String toString() {
        return "entities.Users_Roles[RoleId=" + roleID + ", UserID=" + userID + "]";
    }
}
