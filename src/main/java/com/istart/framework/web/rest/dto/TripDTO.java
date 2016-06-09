package com.istart.framework.web.rest.dto;

import java.io.Serializable;
import java.util.Objects;


/**
 * A DTO for the Trip entity.
 */
public class TripDTO implements Serializable {

    private Long id;

    private String pno;

    private String day;

    private String discripe;


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
    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }
    public String getDiscripe() {
        return discripe;
    }

    public void setDiscripe(String discripe) {
        this.discripe = discripe;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TripDTO tripDTO = (TripDTO) o;

        if ( ! Objects.equals(id, tripDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "TripDTO{" +
            "id=" + id +
            ", pno='" + pno + "'" +
            ", day='" + day + "'" +
            ", discripe='" + discripe + "'" +
            '}';
    }
}
