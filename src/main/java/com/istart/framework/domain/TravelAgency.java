package com.istart.framework.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Generated;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A TravelAgency.
 */
@Entity
@Table(name = "travel_agency")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "travelagency")
@DynamicInsert
@DynamicUpdate
public class TravelAgency implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "agency_number")
    private String agencyCode;

    @Column(name = "agency_name")
    private String agencyName;

    @Column(name = "agency_introduce")
    private String agencyIntroduce;

    @Column(name = "agency_addr")
    private String addr;

    @Column(name = "build_date")
    private LocalDate buildDate;

    @Column(name = "contact_phone")
    private String contactPhone;

    @Column(name = "data_creator")
    private String dataCreator;

    @Column(name = "data_updater")
    private String dataUpdater;

    @Column(name = "data_create_datetime",insertable=false,updatable=false)
    private ZonedDateTime dataCreateDatetime;

    @Column(name = "data_update_datetime")
    @Generated(org.hibernate.annotations.GenerationTime.ALWAYS)
    private ZonedDateTime dataUpdateDatetime;

    @Column(name = "data_status")
    private Integer dataStatus;
    
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

    public String getDataCreator() {
		return dataCreator;
	}

	public void setDataCreator(String dataCreator) {
		this.dataCreator = dataCreator;
	}

	public String getDataUpdater() {
		return dataUpdater;
	}

	public void setDataUpdater(String dataUpdater) {
		this.dataUpdater = dataUpdater;
	}

	public ZonedDateTime getDataCreateDatetime() {
		return dataCreateDatetime;
	}

	public void setDataCreateDatetime(ZonedDateTime dataCreateDatetime) {
		this.dataCreateDatetime = dataCreateDatetime;
	}

	public ZonedDateTime getDataUpdateDatetime() {
		return dataUpdateDatetime;
	}

	public void setDataUpdateDatetime(ZonedDateTime dataUpdateDatetime) {
		this.dataUpdateDatetime = dataUpdateDatetime;
	}

	public Integer getDataStatus() {
		return dataStatus;
	}

	public void setDataStatus(Integer dataStatus) {
		this.dataStatus = dataStatus;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TravelAgency travelAgency = (TravelAgency) o;
        if(travelAgency.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, travelAgency.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "TravelAgency{" +
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
