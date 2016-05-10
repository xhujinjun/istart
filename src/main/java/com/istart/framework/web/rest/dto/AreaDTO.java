package com.istart.framework.web.rest.dto;

import java.time.ZonedDateTime;
import java.io.Serializable;
import java.util.Objects;

import com.istart.framework.domain.enumeration.DataStatus;

/**
 * A DTO for the Area entity.
 */
public class AreaDTO implements Serializable {

    private Long id;

    private String areaid;


    private String name;


    private String parentid;


    private String parentname;


    private String addr;


    private Integer level;


    private Boolean isleaf;


    private ZonedDateTime createDatetime;


    private ZonedDateTime modifiyDatetime;


    private DataStatus status;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getAreaid() {
        return areaid;
    }

    public void setAreaid(String areaid) {
        this.areaid = areaid;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getParentid() {
        return parentid;
    }

    public void setParentid(String parentid) {
        this.parentid = parentid;
    }
    public String getParentname() {
        return parentname;
    }

    public void setParentname(String parentname) {
        this.parentname = parentname;
    }
    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }
    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }
    public Boolean getIsleaf() {
        return isleaf;
    }

    public void setIsleaf(Boolean isleaf) {
        this.isleaf = isleaf;
    }
    public ZonedDateTime getCreateDatetime() {
        return createDatetime;
    }

    public void setCreateDatetime(ZonedDateTime createDatetime) {
        this.createDatetime = createDatetime;
    }
    public ZonedDateTime getModifiyDatetime() {
        return modifiyDatetime;
    }

    public void setModifiyDatetime(ZonedDateTime modifiyDatetime) {
        this.modifiyDatetime = modifiyDatetime;
    }
    public DataStatus getStatus() {
        return status;
    }

    public void setStatus(DataStatus status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AreaDTO areaDTO = (AreaDTO) o;

        if ( ! Objects.equals(id, areaDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "AreaDTO{" +
            "id=" + id +
            ", areaid='" + areaid + "'" +
            ", name='" + name + "'" +
            ", parentid='" + parentid + "'" +
            ", parentname='" + parentname + "'" +
            ", addr='" + addr + "'" +
            ", level='" + level + "'" +
            ", isleaf='" + isleaf + "'" +
            ", createDatetime='" + createDatetime + "'" +
            ", modifiyDatetime='" + modifiyDatetime + "'" +
            ", status='" + status + "'" +
            '}';
    }
}
