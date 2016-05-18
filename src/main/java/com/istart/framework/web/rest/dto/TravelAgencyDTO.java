package com.istart.framework.web.rest.dto;

import java.time.LocalDate;
import java.io.Serializable;
import java.util.Objects;


/**
 * A DTO for the TravelAgency entity.
 */
public class TravelAgencyDTO implements Serializable {

    private Long id;

    private String agencyCode;


    private String agencyName;


    private String agencyIntroduce;


    private String addr;


    private LocalDate buildDate;


    private String contactPhone;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getAgencyCode() {
        return agencyCode;
    }

    public void setAgencyCode(String agencyCode) {
        this.agencyCode = agencyCode;
    }
    public String getAgencyName() {
        return agencyName;
    }

    public void setAgencyName(String agencyName) {
        this.agencyName = agencyName;
    }
    public String getAgencyIntroduce() {
        return agencyIntroduce;
    }

    public void setAgencyIntroduce(String agencyIntroduce) {
        this.agencyIntroduce = agencyIntroduce;
    }
    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }
    public LocalDate getBuildDate() {
        return buildDate;
    }

    public void setBuildDate(LocalDate buildDate) {
        this.buildDate = buildDate;
    }
    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TravelAgencyDTO travelAgencyDTO = (TravelAgencyDTO) o;

        if ( ! Objects.equals(id, travelAgencyDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "TravelAgencyDTO{" +
            "id=" + id +
            ", agencyCode='" + agencyCode + "'" +
            ", agencyName='" + agencyName + "'" +
            ", agencyIntroduce='" + agencyIntroduce + "'" +
            ", addr='" + addr + "'" +
            ", buildDate='" + buildDate + "'" +
            ", contactPhone='" + contactPhone + "'" +
            '}';
    }
}
