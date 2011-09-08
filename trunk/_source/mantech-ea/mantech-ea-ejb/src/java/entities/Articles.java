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
@Table(name = "Articles", catalog = "MantechHelpdesk", schema = "dbo")
@NamedQueries({
    @NamedQuery(name = "Articles.findAll", query = "SELECT a FROM Articles a"),
    @NamedQuery(name = "Articles.findByArticleID", query = "SELECT a FROM Articles a WHERE a.articleID = :articleID"),
    @NamedQuery(name = "Articles.findByTitle", query = "SELECT a FROM Articles a WHERE a.title = :title"),
    @NamedQuery(name = "Articles.findByCreateTime", query = "SELECT a FROM Articles a WHERE a.createTime = :createTime"),
    @NamedQuery(name = "Articles.findByCreateIP", query = "SELECT a FROM Articles a WHERE a.createIP = :createIP"),
    @NamedQuery(name = "Articles.findByEditTime", query = "SELECT a FROM Articles a WHERE a.editTime = :editTime"),
    @NamedQuery(name = "Articles.findByEditIP", query = "SELECT a FROM Articles a WHERE a.editIP = :editIP"),
    @NamedQuery(name = "Articles.findByIsEnable", query = "SELECT a FROM Articles a WHERE a.isEnable = :isEnable")})
public class Articles implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "ArticleID", nullable = false)
    private Integer articleID;
    @Basic(optional = false)
    @Column(name = "Title", nullable = false, length = 500)
    private String title;
    @Basic(optional = false)
    @Lob
    @Column(name = "Content", nullable = false)
    private String content;
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
    @JoinColumn(name = "UserID", referencedColumnName = "UserID", nullable = false)
    @ManyToOne(optional = false)
    private Users userID;
    @Basic(optional = false)
    @Column(name = "LikeCount", nullable = false)
    private Integer likeCount;
    @Basic(optional = false)
    @Column(name = "DislikeCount", nullable = false)
    private Integer dislikeCount;

    public Articles() {
    }

    public Articles(Integer articleID) {
        this.articleID = articleID;
    }

    public Articles(Integer articleID, String title, String content, Date createTime, String createIP, boolean isEnable) {
        this.articleID = articleID;
        this.title = title;
        this.content = content;
        this.createTime = createTime;
        this.createIP = createIP;
        this.isEnable = isEnable;
    }

    public Integer getArticleID() {
        return articleID;
    }

    public void setArticleID(Integer articleID) {
        this.articleID = articleID;
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

    public Users getUserID() {
        return userID;
    }

    public void setUserID(Users userID) {
        this.userID = userID;
    }

    public Integer getDislikeCount() {
        return dislikeCount;
    }

    public void setDislikeCount(Integer dislikeCount) {
        this.dislikeCount = dislikeCount;
    }

    public Integer getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(Integer likeCount) {
        this.likeCount = likeCount;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (articleID != null ? articleID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Articles)) {
            return false;
        }
        Articles other = (Articles) object;
        if ((this.articleID == null && other.articleID != null) || (this.articleID != null && !this.articleID.equals(other.articleID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Articles[articleID=" + articleID + "]";
    }

}
