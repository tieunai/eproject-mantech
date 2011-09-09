/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 *
 * @author HP
 */
@Entity
@Table(name = "Departments", catalog = "MantechHelpdesk", schema = "dbo", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"DepartmentName"})})
@NamedQueries({
    @NamedQuery(name = "Departments.findAll", query = "SELECT d FROM Departments d"),
    @NamedQuery(name = "Departments.findByDepartmentID", query = "SELECT d FROM Departments d WHERE d.departmentID = :departmentID"),
    @NamedQuery(name = "Departments.findByDepartmentName", query = "SELECT d FROM Departments d WHERE d.departmentName = :departmentName"),
    @NamedQuery(name = "Departments.findByIsEnable", query = "SELECT d FROM Departments d WHERE d.isEnable = :isEnable"),

    @NamedQuery(name = "Departments.emplFindAll", query = "SELECT d FROM Departments d WHERE d.isEnable = 1")})
public class Departments implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "DepartmentID", nullable = false)
    private Integer departmentID;
    @Basic(optional = false)
    @Column(name = "DepartmentName", nullable = false, length = 200)
    private String departmentName;
    @Basic(optional = false)
    @Column(name = "IsEnable", nullable = false)
    private boolean isEnable;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "departmentID")
    private List<Users> usersList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "departmentID")
    private List<Threads> threadsList;

    public Departments() {
    }

    public Departments(Integer departmentID) {
        this.departmentID = departmentID;
    }

    public Departments(Integer departmentID, String departmentName, boolean isEnable) {
        this.departmentID = departmentID;
        this.departmentName = departmentName;
        this.isEnable = isEnable;
    }

    public Integer getDepartmentID() {
        return departmentID;
    }

    public void setDepartmentID(Integer departmentID) {
        this.departmentID = departmentID;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public boolean getIsEnable() {
        return isEnable;
    }

    public void setIsEnable(boolean isEnable) {
        this.isEnable = isEnable;
    }

    public List<Users> getUsersList() {
        return usersList;
    }

    public void setUsersList(List<Users> usersList) {
        this.usersList = usersList;
    }

    public List<Threads> getThreadsList() {
        return threadsList;
    }

    public void setThreadsList(List<Threads> threadsList) {
        this.threadsList = threadsList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (departmentID != null ? departmentID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Departments)) {
            return false;
        }
        Departments other = (Departments) object;
        if ((this.departmentID == null && other.departmentID != null) || (this.departmentID != null && !this.departmentID.equals(other.departmentID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Departments[departmentID=" + departmentID + "]";
    }

}
