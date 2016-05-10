package com.istart.framework.web.rest.dto;

import java.io.Serializable;
import java.util.Objects;


/**
 * A DTO for the Sysres entity.
 */
public class SysresDTO implements Serializable {

    private Long id;

    private String name;


    private Integer type;


    private String url;


    private String resdesc;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
    public String getResdesc() {
        return resdesc;
    }

    public void setResdesc(String resdesc) {
        this.resdesc = resdesc;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SysresDTO sysresDTO = (SysresDTO) o;

        if ( ! Objects.equals(id, sysresDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "SysresDTO{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", type='" + type + "'" +
            ", url='" + url + "'" +
            ", resdesc='" + resdesc + "'" +
            '}';
    }
}
