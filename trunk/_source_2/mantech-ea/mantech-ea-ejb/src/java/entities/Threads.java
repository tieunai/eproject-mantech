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

/**
 *
 * @author HP
 */
@Entity
@Table(name = "Threads", catalog = "MantechHelpdesk", schema = "dbo")
@NamedQueries({
    @NamedQuery(name = "Threads.findAll", query = "SELECT t FROM Threads t"),
    @NamedQuery(name = "Threads.findByThreadID", query = "SELECT t FROM Threads t WHERE t.threadID = :threadID"),
    @NamedQuery(name = "Threads.findByThreadName", query = "SELECT t FROM Threads t WHERE t.threadName = :threadName"),
    @NamedQuery(name = "Threads.findByCreateTime", query = "SELECT t FROM Threads t WHERE t.createTime = :createTime"),
    @NamedQuery(name = "Threads.findByCreateIP", query = "SELECT t FROM Threads t WHERE t.createIP = :createIP"),
    @NamedQuery(name = "Threads.findByEditTime", query = "SELECT t FROM Threads t WHERE t.editTime = :editTime"),
    @NamedQuery(name = "Threads.findByEditIP", query = "SELECT t FROM Threads t WHERE t.editIP = :editIP"),
    @NamedQuery(name = "Threads.findByIsEnable", query = "SELECT t FROM Threads t WHERE t.isEnable = :isEnable")})
public class Threads implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "ThreadID", nullable = false)
    private Integer threadID;
    @Basic(optional = false)
    @Column(name = "ThreadName", nullable = false, length = 500)
    private String threadName;
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
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "threadID")
    private List<Complaints> complaintsList;
    @JoinColumn(name = "DepartmentID", referencedColumnName = "DepartmentID", nullable = false)
    @ManyToOne(optional = false)
    private Departments departmentID;

    public Threads() {
    }

    public Threads(Integer threadID) {
        this.threadID = threadID;
    }

    public Threads(Integer threadID, String threadName, Date createTime, String createIP, boolean isEnable) {
        this.threadID = threadID;
        this.threadName = threadName;
        this.createTime = createTime;
        this.createIP = createIP;
        this.isEnable = isEnable;
    }

    public Integer getThreadID() {
        return threadID;
    }

    public void setThreadID(Integer threadID) {
        this.threadID = threadID;
    }

    public String getThreadName() {
        return threadName;
    }

    public void setThreadName(String threadName) {
        this.threadName = threadName;
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

    public Departments getDepartmentID() {
        return departmentID;
    }

    public void setDepartmentID(Departments departmentID) {
        this.departmentID = departmentID;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (threadID != null ? threadID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Threads)) {
            return false;
        }
        Threads other = (Threads) object;
        if ((this.threadID == null && other.threadID != null) || (this.threadID != null && !this.threadID.equals(other.threadID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Threads[threadID=" + threadID + "]";
    }

}
