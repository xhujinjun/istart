package com.istart.framework.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

import com.istart.framework.domain.enumeration.DataStatus;

/**
 * A Area.
 */
@Entity
@Table(name = "area")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "area")
public class Area implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "areaid")
    private String areaid;

    @Column(name = "name")
    private String name;

    @Column(name = "parentid")
    private String parentid;

    @Column(name = "parentname")
    private String parentname;

    @Column(name = "addr")
    private String addr;

    @Column(name = "level")
    private Integer level;

    @Column(name = "isleaf")
    private Boolean isleaf;

    @Column(name = "create_datetime")
    private ZonedDateTime createDatetime;

    @Column(name = "modifiy_datetime")
    private ZonedDateTime modifiyDatetime;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "status")
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

    public Boolean isIsleaf() {
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
        Area area = (Area) o;
        if(area.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, area.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Area{" +
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
