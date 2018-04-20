/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.dbacms.model;

import java.util.Objects;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

/**
 *
 * @author blake
 */
public class ApprovalStatus {
    
    private int approvalStatusId;
    
    @NotEmpty(message = "Please provide a description for this status")
    @Length(max = 20, message = "Description cannot exceed 20 characters.")
    private String description;

    public ApprovalStatus() {
    }

    public ApprovalStatus(String description) {
        this.description = description;
    }

    public int getApprovalStatusId() {
        return approvalStatusId;
    }

    public void setApprovalStatusId(int approvalStatusId) {
        this.approvalStatusId = approvalStatusId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 37 * hash + this.approvalStatusId;
        hash = 37 * hash + Objects.hashCode(this.description);
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
        final ApprovalStatus other = (ApprovalStatus) obj;
        if (this.approvalStatusId != other.approvalStatusId) {
            return false;
        }
        if (!Objects.equals(this.description, other.description)) {
            return false;
        }
        return true;
    }
    
    
    
}
