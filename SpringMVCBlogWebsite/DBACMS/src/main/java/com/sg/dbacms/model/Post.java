/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.dbacms.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

/**
 *
 * @author Alex
 */
public class Post {
    
    private int postId;
    
    @NotEmpty(message = "Please enter a headline for this post.")
    @Length(max = 100, message = "The headline cannot exceed 100 characters.")
    private String headline;
    
    @NotEmpty(message = "Please enter content for this post.")
    @Length(max = 20000, message = "The content cannot exceed 20,000 characters.")
    //body == full HTML document
    private String content;
    
    private ApprovalStatus approvalStatus;
    
    private int numLikes;
    
    private LocalDateTime postingDate;
    
    private LocalDateTime expirationDate;
    
    @Length(max = 256, message = "Image link cannot exceed 256 characters.")
    private String imgLink;
    
    private User user;
    
    private List<Category> categories = new ArrayList<>();

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public String getHeadline() {
        return headline;
    }

    public void setHeadline(String headline) {
        this.headline = headline;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public ApprovalStatus getApprovalStatus() {
        return approvalStatus;
    }

    public void setApprovalStatus(ApprovalStatus approvalStatus) {
        this.approvalStatus = approvalStatus;
    }

    public int getNumLikes() {
        return numLikes;
    }

    public void setNumLikes(int numLikes) {
        this.numLikes = numLikes;
    }

    public LocalDateTime getPostingDate() {
        return postingDate;
    }

    public void setPostingDate(LocalDateTime date) {
        this.postingDate = date;
    }

    public LocalDateTime getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDateTime expirationDate) {
        this.expirationDate = expirationDate;
    }
    
    public String getImgLink() {
        return imgLink;
    }

    public void setImgLink(String imgLink) {
        this.imgLink = imgLink;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 73 * hash + this.postId;
        hash = 73 * hash + Objects.hashCode(this.headline);
        hash = 73 * hash + Objects.hashCode(this.content);
        hash = 73 * hash + Objects.hashCode(this.approvalStatus);
        hash = 73 * hash + this.numLikes;
//        hash = 73 * hash + Objects.hashCode(this.postingDate);
//        hash = 73 * hash + Objects.hashCode(this.expirationDate);
        hash = 73 * hash + Objects.hashCode(this.imgLink);
        hash = 73 * hash + Objects.hashCode(this.user);
//        hash = 73 * hash + Objects.hashCode(this.categories);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Post other = (Post) obj;
        if (this.postId != other.postId) {
            return false;
        }
        if (this.numLikes != other.numLikes) {
            return false;
        }
        if (!Objects.equals(this.headline, other.headline)) {
            return false;
        }
        if (!Objects.equals(this.content, other.content)) {
            return false;
        }
        if (!Objects.equals(this.imgLink, other.imgLink)) {
            return false;
        }
        if (!Objects.equals(this.approvalStatus, other.approvalStatus)) {
            return false;
        }
//        if (!Objects.equals(this.postingDate, other.postingDate)) {
//            return false;
//        }
//        if (!Objects.equals(this.expirationDate, other.expirationDate)) {
//            return false;
//        }
        if (!Objects.equals(this.user, other.user)) {
            return false;
        }
//        if (!Objects.equals(this.categories, other.categories)) {
//            return false;
//        }
        return true;
    }

    

    
}
