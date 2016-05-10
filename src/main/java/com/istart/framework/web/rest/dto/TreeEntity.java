package com.istart.framework.web.rest.dto;

public class TreeEntity {  
    public Long parentId;  
    public Long orgId;  
    public String orgName;  
    public Long getParentId() {  
        return parentId;  
    }  
    public void setParentId(Long parentId) {  
        this.parentId = parentId;  
    }  
    public Long getOrgId() {  
        return orgId;  
    }  
    public void setOrgId(Long orgId) {  
        this.orgId = orgId;  
    }  
    public String getOrgName() {  
        return orgName;  
    }  
    public void setOrgName(String orgName) {  
        this.orgName = orgName;  
    }  
}  