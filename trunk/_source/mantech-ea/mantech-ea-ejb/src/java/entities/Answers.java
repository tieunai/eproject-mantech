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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
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
@Table(name = "Answers", catalog = "MantechHelpdesk", schema = "dbo")
@NamedQueries({
    @NamedQuery(name = "Answers.findAll", query = "SELECT a FROM Answers a ORDER BY a.createTime DESC"),
    @NamedQuery(name = "Answers.findByAnswerID", query = "SELECT a FROM Answers a WHERE a.answerID = :answerID"),
    @NamedQuery(name = "Answers.findByComplaintID", query = "SELECT a FROM Answers a WHERE a.complaintID = :complaintID"),
    @NamedQuery(name = "Answers.findByCreateTime", query = "SELECT a FROM Answers a WHERE a.createTime = :createTime"),
    @NamedQuery(name = "Answers.findByCreateIP", query = "SELECT a FROM Answers a WHERE a.createIP = :createIP"),
    @NamedQuery(name = "Answers.findByEditTime", query = "SELECT a FROM Answers a WHERE a.editTime = :editTime"),
    @NamedQuery(name = "Answers.findByEditIP", query = "SELECT a FROM Answers a WHERE a.editIP = :editIP"),
    @NamedQuery(name = "Answers.findByIsEnable", query = "SELECT a FROM Answers a WHERE a.isEnable = :isEnable")})
public class Answers implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "AnswerID", nullable = false)
    private Integer answerID;
    @Basic(optional = false)
    @Lob
    @Column(name = "AnswerContent", nullable = false)
    private String answerContent;
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
    @JoinColumn(name = "EditorID", referencedColumnName = "UserID")
    @ManyToOne
    private Users editorID;
    @JoinColumn(name = "ComplaintID", referencedColumnName = "ComplaintID", nullable = false)
    @ManyToOne(optional = false)
    private Complaints complaintID;

    public Answers() {
    }

    public Answers(Integer answerID) {
        this.answerID = answerID;
    }

    public Answers(Integer answerID, String answerContent, Date createTime, String createIP, boolean isEnable) {
        this.answerID = answerID;
        this.answerContent = answerContent;
        this.createTime = createTime;
        this.createIP = createIP;
        this.isEnable = isEnable;
    }

    public Integer getAnswerID() {
        return answerID;
    }

    public void setAnswerID(Integer answerID) {
        this.answerID = answerID;
    }

    public String getAnswerContent() {
        return answerContent;
    }

    public void setAnswerContent(String answerContent) {
        this.answerContent = answerContent;
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

    public Users getEditorID() {
        return editorID;
    }

    public void setEditorID(Users editorID) {
        this.editorID = editorID;
    }

    public Complaints getComplaintID() {
        return complaintID;
    }

    public void setComplaintID(Complaints complaintID) {
        this.complaintID = complaintID;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (answerID != null ? answerID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Answers)) {
            return false;
        }
        Answers other = (Answers) object;
        if ((this.answerID == null && other.answerID != null) || (this.answerID != null && !this.answerID.equals(other.answerID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Answers[answerID=" + answerID + "]";
    }

}
