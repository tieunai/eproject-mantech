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
import javax.persistence.Lob;
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
@Table(name = "FAQs", catalog = "MantechHelpdesk", schema = "dbo")
@NamedQueries({
    @NamedQuery(name = "FAQs.findAll", query = "SELECT f FROM FAQs f"),
    @NamedQuery(name = "FAQs.findByFaqid", query = "SELECT f FROM FAQs f WHERE f.faqid = :faqid"),
    @NamedQuery(name = "FAQs.findByTitle", query = "SELECT f FROM FAQs f WHERE f.title = :title"),
    @NamedQuery(name = "FAQs.findByCreateIP", query = "SELECT f FROM FAQs f WHERE f.createIP = :createIP"),
    @NamedQuery(name = "FAQs.findByCreateTime", query = "SELECT f FROM FAQs f WHERE f.createTime = :createTime"),
    @NamedQuery(name = "FAQs.findByIsEnable", query = "SELECT f FROM FAQs f WHERE f.isEnable = :isEnable")})
public class FAQs implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "FAQID", nullable = false)
    private Integer faqid;
    @Basic(optional = false)
    @Column(name = "Title", nullable = false, length = 500)
    private String title;
    @Basic(optional = false)
    @Lob
    @Column(name = "Content", nullable = false)
    private String content;
    @Lob
    @Column(name = "Feedback")
    private String feedback;
    @Basic(optional = false)
    @Column(name = "CreateIP", nullable = false, length = 20)
    private String createIP;
    @Basic(optional = false)
    @Column(name = "CreateTime", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;
    @Basic(optional = false)
    @Column(name = "IsEnable", nullable = false)
    private boolean isEnable;

    public FAQs() {
    }

    public FAQs(Integer faqid) {
        this.faqid = faqid;
    }

    public FAQs(Integer faqid, String title, String content, String createIP, Date createTime, boolean isEnable) {
        this.faqid = faqid;
        this.title = title;
        this.content = content;
        this.createIP = createIP;
        this.createTime = createTime;
        this.isEnable = isEnable;
    }

    public Integer getFaqid() {
        return faqid;
    }

    public void setFaqid(Integer faqid) {
        this.faqid = faqid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public String getCreateIP() {
        return createIP;
    }

    public void setCreateIP(String createIP) {
        this.createIP = createIP;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public boolean getIsEnable() {
        return isEnable;
    }

    public void setIsEnable(boolean isEnable) {
        this.isEnable = isEnable;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (faqid != null ? faqid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FAQs)) {
            return false;
        }
        FAQs other = (FAQs) object;
        if ((this.faqid == null && other.faqid != null) || (this.faqid != null && !this.faqid.equals(other.faqid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.FAQs[faqid=" + faqid + "]";
    }

}
