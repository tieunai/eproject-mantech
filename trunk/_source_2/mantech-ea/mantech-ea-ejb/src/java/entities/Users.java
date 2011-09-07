/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

/**
 *
 * @author HP
 */
@Entity
@Table(name = "Users", catalog = "MantechHelpdesk", schema = "dbo", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"Username"})})
@NamedQueries({
    @NamedQuery(name = "Users.findAll", query = "SELECT u FROM Users u"),
    @NamedQuery(name = "Users.findByUserID", query = "SELECT u FROM Users u WHERE u.userID = :userID"),
    @NamedQuery(name = "Users.findByUsername", query = "SELECT u FROM Users u WHERE u.username = :username"),
    @NamedQuery(name = "Users.findByPassword", query = "SELECT u FROM Users u WHERE u.password = :password"),
    @NamedQuery(name = "Users.findByFirstName", query = "SELECT u FROM Users u WHERE u.firstName = :firstName"),
    @NamedQuery(name = "Users.findByLastName", query = "SELECT u FROM Users u WHERE u.lastName = :lastName"),
    @NamedQuery(name = "Users.findByIsOnline", query = "SELECT u FROM Users u WHERE u.isOnline = :isOnline"),
    @NamedQuery(name = "Users.findByLastVisit", query = "SELECT u FROM Users u WHERE u.lastVisit = :lastVisit"),
    @NamedQuery(name = "Users.findByCreateTime", query = "SELECT u FROM Users u WHERE u.createTime = :createTime"),
    @NamedQuery(name = "Users.findByCreateIP", query = "SELECT u FROM Users u WHERE u.createIP = :createIP"),
    @NamedQuery(name = "Users.findByEditTime", query = "SELECT u FROM Users u WHERE u.editTime = :editTime"),
    @NamedQuery(name = "Users.findByEditIP", query = "SELECT u FROM Users u WHERE u.editIP = :editIP"),
    @NamedQuery(name = "Users.findByIsEnable", query = "SELECT u FROM Users u WHERE u.isEnable = :isEnable"),
    @NamedQuery(name = "Users.findByDepartmentID", query = "SELECT u FROM Users u WHERE u.departmentID = :departmentID")})
public class Users implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "UserID", nullable = false)
    private Integer userID;
    @Basic(optional = false)
    @Column(name = "Username", nullable = false, length = 50)
    private String username;
    @Basic(optional = false)
    @Column(name = "Password", nullable = false, length = 50)
    private String password;
    @Basic(optional = false)
    @Column(name = "FirstName", nullable = false, length = 50)
    private String firstName;
    @Basic(optional = false)
    @Column(name = "LastName", nullable = false, length = 50)
    private String lastName;
    @Basic(optional = false)
    @Column(name = "IsOnline", nullable = false)
    private boolean isOnline;
    @Basic(optional = false)
    @Column(name = "LastVisit", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastVisit;
    @Basic(optional = false)
    @Column(name = "CreateTime", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;
    @Basic(optional = false)
    @Column(name = "CreateIP", nullable = false, length = 20)
    private String createIP;
    @Column(name = "EditTime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date editTime;
    @Column(name = "EditIP", length = 20)
    private String editIP;
    @Basic(optional = false)
    @Column(name = "IsEnable", nullable = false)
    private boolean isEnable;
    @OneToMany(mappedBy = "userRef")
    private List<Complaints> complaintsList;
    @OneToMany(mappedBy = "editorID")
    private List<Complaints> complaintsList1;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userID")
    private List<Complaints> complaintsList2;
    @OneToMany(mappedBy = "editorID")
    private List<Users> usersList;
    @JoinColumn(name = "EditorID", referencedColumnName = "UserID")
    @ManyToOne
    private Users editorID;
    @JoinColumn(name = "RoleID", referencedColumnName = "RoleID", nullable = false)
    @ManyToOne(optional = false)
    private Roles roleID;
    @JoinColumn(name = "DepartmentID", referencedColumnName = "DepartmentID", nullable = false)
    @ManyToOne(optional = false)
    private Departments departmentID;
    @OneToMany(mappedBy = "editorID")
    private List<Answers> answersList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userID")
    private List<Articles> articlesList;

    public Users() {
    }

    public Users(Integer userID) {
        this.userID = userID;
    }

    public Users(Integer userID, String username, String password, String firstName, String lastName, boolean isOnline, Date lastVisit, Date createTime, String createIP, boolean isEnable) {
        this.userID = userID;
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.isOnline = isOnline;
        this.lastVisit = lastVisit;
        this.createTime = createTime;
        this.createIP = createIP;
        this.isEnable = isEnable;
    }

    public Integer getUserID() {
        return userID;
    }

    public void setUserID(Integer userID) {
        this.userID = userID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public boolean getIsOnline() {
        return isOnline;
    }

    public void setIsOnline(boolean isOnline) {
        this.isOnline = isOnline;
    }

    public Date getLastVisit() {
        return lastVisit;
    }

    public void setLastVisit(Date lastVisit) {
        this.lastVisit = lastVisit;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreateIP() {
        return createIP;
    }

    public void setCreateIP(String createIP) {
        this.createIP = createIP;
    }

    public Date getEditTime() {
        return editTime;
    }

    public void setEditTime(Date editTime) {
        this.editTime = editTime;
    }

    public String getEditIP() {
        return editIP;
    }

    public void setEditIP(String editIP) {
        this.editIP = editIP;
    }

    public boolean getIsEnable() {
        return isEnable;
    }

    public void setIsEnable(boolean isEnable) {
        this.isEnable = isEnable;
    }

    public List<Complaints> getComplaintsList() {
        return complaintsList;
    }

    public void setComplaintsList(List<Complaints> complaintsList) {
        this.complaintsList = complaintsList;
    }

    public List<Complaints> getComplaintsList1() {
        return complaintsList1;
    }

    public void setComplaintsList1(List<Complaints> complaintsList1) {
        this.complaintsList1 = complaintsList1;
    }

    public List<Complaints> getComplaintsList2() {
        return complaintsList2;
    }

    public void setComplaintsList2(List<Complaints> complaintsList2) {
        this.complaintsList2 = complaintsList2;
    }

    public List<Users> getUsersList() {
        return usersList;
    }

    public void setUsersList(List<Users> usersList) {
        this.usersList = usersList;
    }

    public Users getEditorID() {
        return editorID;
    }

    public void setEditorID(Users editorID) {
        this.editorID = editorID;
    }

    public Roles getRoleID() {
        return roleID;
    }

    public void setRoleID(Roles roleID) {
        this.roleID = roleID;
    }

    public Departments getDepartmentID() {
        return departmentID;
    }

    public void setDepartmentID(Departments departmentID) {
        this.departmentID = departmentID;
    }

    public List<Answers> getAnswersList() {
        return answersList;
    }

    public void setAnswersList(List<Answers> answersList) {
        this.answersList = answersList;
    }

    public List<Articles> getArticlesList() {
        return articlesList;
    }

    public void setArticlesList(List<Articles> articlesList) {
        this.articlesList = articlesList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (userID != null ? userID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Users)) {
            return false;
        }
        Users other = (Users) object;
        if ((this.userID == null && other.userID != null) || (this.userID != null && !this.userID.equals(other.userID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Users[userID=" + userID + "]";
    }

}
