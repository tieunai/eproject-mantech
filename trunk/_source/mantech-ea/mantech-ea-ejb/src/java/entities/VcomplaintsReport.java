/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author HP
 */
@Entity
@Table(name = "v_complaintsReport", catalog = "MantechHelpdesk", schema = "dbo")
@NamedQueries({
    @NamedQuery(name = "VcomplaintsReport.findAll", query = "SELECT v FROM VcomplaintsReport v"),
    @NamedQuery(name = "VcomplaintsReport.findByTitle", query = "SELECT v FROM VcomplaintsReport v WHERE v.title = :title"),
    @NamedQuery(name = "VcomplaintsReport.findByThreadName", query = "SELECT v FROM VcomplaintsReport v WHERE v.threadName = :threadName"),
    @NamedQuery(name = "VcomplaintsReport.findByDepartmentName", query = "SELECT v FROM VcomplaintsReport v WHERE v.departmentName = :departmentName"),
    @NamedQuery(name = "VcomplaintsReport.findByCreateTime", query = "SELECT v FROM VcomplaintsReport v WHERE v.createTime = :createTime"),
    @NamedQuery(name = "VcomplaintsReport.findByFinishedTime", query = "SELECT v FROM VcomplaintsReport v WHERE v.finishedTime = :finishedTime"),
    @NamedQuery(name = "VcomplaintsReport.findByRepliedTime", query = "SELECT v FROM VcomplaintsReport v WHERE v.repliedTime = :repliedTime"),
    @NamedQuery(name = "VcomplaintsReport.findByIsFinished", query = "SELECT v FROM VcomplaintsReport v WHERE v.isFinished = :isFinished"),
    @NamedQuery(name = "VcomplaintsReport.findByUserRef", query = "SELECT v FROM VcomplaintsReport v WHERE v.userRef = :userRef"),
    @NamedQuery(name = "VcomplaintsReport.findByUserID", query = "SELECT v FROM VcomplaintsReport v WHERE v.userID = :userID"),
    @NamedQuery(name = "VcomplaintsReport.findByDepartmentID", query = "SELECT v FROM VcomplaintsReport v WHERE v.departmentID = :departmentID"),
    @NamedQuery(name = "VcomplaintsReport.findByBetweenTime", query = "SELECT v FROM VcomplaintsReport v WHERE v.departmentID = :departmentID AND v.createTime BETWEEN :start AND :end"),
    @NamedQuery(name = "VcomplaintsReport.findByThreadID", query = "SELECT v FROM VcomplaintsReport v WHERE v.threadID = :threadID")})
public class VcomplaintsReport implements Serializable {
    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @Column(name = "Title", nullable = false, length = 256)
    private String title;
    @Basic(optional = false)
    @Column(name = "ThreadName", nullable = false, length = 256)
    private String threadName;
    @Basic(optional = false)
    @Column(name = "DepartmentName", nullable = false, length = 200)
    private String departmentName;
    @Basic(optional = false)
    @Column(name = "CreateTime", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;
    @Column(name = "FinishedTime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date finishedTime;
    @Column(name = "RepliedTime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date repliedTime;
    @Basic(optional = false)
    @Column(name = "IsFinished", nullable = false)
    private boolean isFinished;
    @Column(name = "UserRef")
    private Integer userRef;
    @Basic(optional = false)
    @Column(name = "UserID", nullable = false)
    private int userID;
    @Basic(optional = false)
    @Column(name = "DepartmentID", nullable = false)
    @Id
    private int departmentID;
    @Basic(optional = false)
    @Column(name = "ThreadID", nullable = false)
    private int threadID;

    @Basic(optional = false)
    @Column(name = "ComplaintID", nullable = false)
    private int complaintID;

    public VcomplaintsReport() {
    }

    public int getComplaintID() {
        return complaintID;
    }

    public void setComplaintID(int complaintID) {
        this.complaintID = complaintID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getThreadName() {
        return threadName;
    }

    public void setThreadName(String threadName) {
        this.threadName = threadName;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getFinishedTime() {
        return finishedTime;
    }

    public void setFinishedTime(Date finishedTime) {
        this.finishedTime = finishedTime;
    }

    public Date getRepliedTime() {
        return repliedTime;
    }

    public void setRepliedTime(Date repliedTime) {
        this.repliedTime = repliedTime;
    }

    public boolean getIsFinished() {
        return isFinished;
    }

    public void setIsFinished(boolean isFinished) {
        this.isFinished = isFinished;
    }

    public Integer getUserRef() {
        return userRef;
    }

    public void setUserRef(Integer userRef) {
        this.userRef = userRef;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getDepartmentID() {
        return departmentID;
    }

    public void setDepartmentID(int departmentID) {
        this.departmentID = departmentID;
    }

    public int getThreadID() {
        return threadID;
    }

    public void setThreadID(int threadID) {
        this.threadID = threadID;
    }

}
