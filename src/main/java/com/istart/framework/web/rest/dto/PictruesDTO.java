package com.istart.framework.web.rest.dto;

import java.io.Serializable;
import java.util.Objects;


/**
 * A DTO for the Pictrues entity.
 */
public class PictruesDTO implements Serializable {

    private Long id;

    private String pno;

    private String picPath;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getPno() {
        return pno;
    }

    public void setPno(String pno) {
        this.pno = pno;
    }
    public String getPicPath() {
        return picPath;
    }

    public void setPicPath(String picPath) {
        this.picPath = picPath;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PictruesDTO pictruesDTO = (PictruesDTO) o;

        if ( ! Objects.equals(id, pictruesDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "PictruesDTO{" +
            "id=" + id +
            ", pno='" + pno + "'" +
            ", picPath='" + picPath + "'" +
            '}';
    }
}
