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
import javax.persistence.Lob;
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
@Table(name = "Complaints", catalog = "MantechHelpdesk", schema = "dbo")
@NamedQueries({
    @NamedQuery(name = "Complaints.findAll", query = "SELECT c FROM Complaints c ORDER BY c.createTime DESC"),
    @NamedQuery(name = "Complaints.findByComplaintID", query = "SELECT c FROM Complaints c WHERE c.complaintID = :complaintID"),
    @NamedQuery(name = "Complaints.findByBetweenTime", query = "SELECT c FROM Complaints c WHERE c.threadID = :threadID AND c.createTime BETWEEN :start AND :end"),
    @NamedQuery(name = "Complaints.findByBetweenTime2", query = "SELECT c FROM Complaints c WHERE c.createTime BETWEEN :start AND :end"),
    @NamedQuery(name = "Complaints.findByThreadID", query = "SELECT c FROM Complaints c WHERE c.threadID = :threadID"),
    @NamedQuery(name = "Complaints.findByUserID", query = "SELECT c FROM Complaints c WHERE c.userID = :userID ORDER BY c.createTime DESC"),
    @NamedQuery(name = "Complaints.findByUserRef", query = "SELECT c FROM Complaints c WHERE c.userRef = :userRef ORDER BY c.createTime DESC"),
    @NamedQuery(name = "Complaints.findByTitle", query = "SELECT c FROM Complaints c WHERE c.title = :title ORDER BY c.createTime DESC"),
    @NamedQuery(name = "Complaints.findByCreateTime", query = "SELECT c FROM Complaints c WHERE c.createTime = :createTime ORDER BY c.createTime DESC"),
    @NamedQuery(name = "Complaints.findByCreateIP", query = "SELECT c FROM Complaints c WHERE c.createIP = :createIP ORDER BY c.createTime DESC"),
    @NamedQuery(name = "Complaints.findByEditIP", query = "SELECT c FROM Complaints c WHERE c.editIP = :editIP ORDER BY c.createTime DESC"),
    @NamedQuery(name = "Complaints.findByIsEnable", query = "SELECT c FROM Complaints c WHERE c.isEnable = :isEnable ORDER BY c.createTime DESC"),
    @NamedQuery(name = "Complaints.findByIsFinished", query = "SELECT c FROM Complaints c WHERE c.isFinished = :isFinished ORDER BY c.createTime DESC"),
    @NamedQuery(name = "Complaints.findByFinishedTime", query = "SELECT c FROM Complaints c WHERE c.finishedTime = :finishedTime ORDER BY c.createTime DESC"),
    @NamedQuery(name = "Complaints.findByHasReplied", query = "SELECT c FROM Complaints c WHERE c.hasReplied = :hasReplied ORDER BY c.createTime DESC"),
    @NamedQuery(name = "Complaints.findByRepliedTime", query = "SELECT c FROM Complaints c WHERE c.repliedTime = :repliedTime ORDER BY c.createTime DESC"),
    @NamedQuery(name = "Complaints.findByPriority", query = "SELECT c FROM Complaints c WHERE c.priority = :priority ORDER BY c.createTime DESC"),
    @NamedQuery(name = "Complaints.emplFindByUserID", query = "SELECT c FROM Complaints c WHERE c.userID = :userID AND c.isEnable = 1 ORDER BY c.createTime DESC"),
    @NamedQuery(name = "Complaints.emplFindByUserRef", query = "SELECT c FROM Complaints c WHERE c.userRef = :userRef AND c.isEnable = 1 ORDER BY c.createTime DESC"),
    @NamedQuery(name = "Complaints.emplFindByPriority", query = "SELECT c FROM Complaints c WHERE c.priority = :priority AND c.isEnable = 1 ORDER BY c.createTime DESC")})
public class Complaints implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "ComplaintID", nullable = false)
    private Integer complaintID;
    @Basic(optional = false)
    @Column(name = "Title", nullable = false, length = 500)
    private String title;
    @Basic(optional = false)
    @Lob
    @Column(name = "ComplaintContent", nullable = false)
    private String complaintContent;
    @Basic(optional = false)
    @Column(name = "CreateTime", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;
    @Basic(optional = false)
    @Column(name = "CreateIP", nullable = false, length = 20)
    private String createIP;
    @Column(name = "EditIP", length = 20)
    private String editIP;
    @Basic(optional = false)
    @Column(name = "IsEnable", nullable = false)
    private boolean isEnable;
    @Basic(optional = false)
    @Column(name = "IsFinished", nullable = false)
    private boolean isFinished;
    @Column(name = "FinishedTime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date finishedTime;
    @Basic(optional = false)
    @Column(name = "HasReplied", nullable = false)
    private boolean hasReplied;
    @Column(name = "RepliedTime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date repliedTime;
    @JoinColumn(name = "UserRef", referencedColumnName = "UserID")
    @ManyToOne
    private Users userRef;
    @JoinColumn(name = "EditorID", referencedColumnName = "UserID")
    @ManyToOne
    private Users editorID;
    @JoinColumn(name = "UserID", referencedColumnName = "UserID", nullable = false)
    @ManyToOne(optional = false)
    private Users userID;
    @JoinColumn(name = "ThreadID", referencedColumnName = "ThreadID", nullable = false)
    @ManyToOne(optional = false)
    private Threads threadID;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "complaintID")
    private List<Answers> answersList;
    @Basic(optional = false)
    @Column(name = "Priority", nullable = false)
    private Integer priority;
    @Basic(optional = false)
    @Column(name = "IsComplaintFinished", nullable = false)
    private boolean isComplaintFinished;
    @Basic(optional = false)
    @Column(name = "IsRead", nullable = false)
    private boolean isRead;
    @Basic(optional = false)
    @Column(name = "IsAdminRead", nullable = false)
    private boolean isAdminRead;

    public Complaints() {
    }

    public Complaints(Integer complaintID) {
        this.complaintID = complaintID;
    }

    public Complaints(Integer complaintID, String title, String complaintContent, Date createTime, String createIP, boolean isEnable, boolean isFinished, boolean hasReplied) {
        this.complaintID = complaintID;
        this.title = title;
        this.complaintContent = complaintContent;
        this.createTime = createTime;
        this.createIP = createIP;
        this.isEnable = isEnable;
        this.isFinished = isFinished;
        this.hasReplied = hasReplied;
    }

    public boolean isIsAdminRead() {
        return isAdminRead;
    }

    public void setIsAdminRead(boolean isAdminRead) {
        this.isAdminRead = isAdminRead;
    }

    public boolean isIsRead() {
        return isRead;
    }

    public void setIsRead(boolean isRead) {
        this.isRead = isRead;
    }

    public boolean isIsComplaintFinished() {
        return isComplaintFinished;
    }

    public void setIsComplaintFinished(boolean isComplaintFinished) {
        this.isComplaintFinished = isComplaintFinished;
    }

    public Integer getComplaintID() {
        return complaintID;
    }

    public void setComplaintID(Integer complaintID) {
        this.complaintID = complaintID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getComplaintContent() {
        return complaintContent;
    }

    public void setComplaintContent(String complaintContent) {
        this.complaintContent = complaintContent;
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

    public boolean getIsFinished() {
        return isFinished;
    }

    public void setIsFinished(boolean isFinished) {
        this.isFinished = isFinished;
    }

    public Date getFinishedTime() {
        return finishedTime;
    }

    public void setFinishedTime(Date finishedTime) {
        this.finishedTime = finishedTime;
    }

    public boolean getHasReplied() {
        return hasReplied;
    }

    public void setHasReplied(boolean hasReplied) {
        this.hasReplied = hasReplied;
    }

    public Date getRepliedTime() {
        return repliedTime;
    }

    public void setRepliedTime(Date repliedTime) {
        this.repliedTime = repliedTime;
    }

    public Users getUserRef() {
        return userRef;
    }

    public void setUserRef(Users userRef) {
        this.userRef = userRef;
    }

    public Users getEditorID() {
        return editorID;
    }

    public void setEditorID(Users editorID) {
        this.editorID = editorID;
    }

    public Users getUserID() {
        return userID;
    }

    public void setUserID(Users userID) {
        this.userID = userID;
    }

    public Threads getThreadID() {
        return threadID;
    }

    public void setThreadID(Threads threadID) {
        this.threadID = threadID;
    }

    public List<Answers> getAnswersList() {
        return answersList;
    }

    public void setAnswersList(List<Answers> answersList) {
        this.answersList = answersList;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (complaintID != null ? complaintID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Complaints)) {
            return false;
        }
        Complaints other = (Complaints) object;
        if ((this.complaintID == null && other.complaintID != null) || (this.complaintID != null && !this.complaintID.equals(other.complaintID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Complaints[complaintID=" + complaintID + "]";
    }

}
