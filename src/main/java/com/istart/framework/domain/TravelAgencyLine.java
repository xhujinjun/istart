package com.istart.framework.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A TravelAgencyLine.
 */
@Entity
@Table(name = "travel_agency_line")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "travelagencyline")
public class TravelAgencyLine implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "travel_agency_id")
    private Long travelAgencyId;

    @Column(name = "line_number")
    private String line_number;

    @Column(name = "line_name")
    private String lineName;

    @Column(name = "spot_introduce")
    private String spotIntroduce;

    @Column(name = "line_datetime")
    private ZonedDateTime lineDatetime;

    @Column(name = "line_city")
    private String lineCity;

    @Column(name = "data_creator")
    private String dataCreator;

    @Column(name = "data_updater")
    private String dataUpdater;

    @Column(name = "data_create_datetime")
    private ZonedDateTime dataCreateDatetime;

    @Column(name = "data_update_datetime")
    private ZonedDateTime dataUpdateDatetime;

    @Column(name = "data_status")
    private Integer dataStatus;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTravelAgencyId() {
        return travelAgencyId;
    }

    public void setTravelAgencyId(Long travelAgencyId) {
        this.travelAgencyId = travelAgencyId;
    }

    public String getLine_number() {
        return line_number;
    }

    public void setLine_number(String line_number) {
        this.line_number = line_number;
    }

    public String getLineName() {
        return lineName;
    }

    public void setLineName(String lineName) {
        this.lineName = lineName;
    }

    public String getSpotIntroduce() {
        return spotIntroduce;
    }

    public void setSpotIntroduce(String spotIntroduce) {
        this.spotIntroduce = spotIntroduce;
    }

    public ZonedDateTime getLineDatetime() {
        return lineDatetime;
    }

    public void setLineDatetime(ZonedDateTime lineDatetime) {
        this.lineDatetime = lineDatetime;
    }

    public String getLineCity() {
        return lineCity;
    }

    public void setLineCity(String lineCity) {
        this.lineCity = lineCity;
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
        TravelAgencyLine travelAgencyLine = (TravelAgencyLine) o;
        if(travelAgencyLine.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, travelAgencyLine.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "TravelAgencyLine{" +
            "id=" + id +
            ", travelAgencyId='" + travelAgencyId + "'" +
            ", line_number='" + line_number + "'" +
            ", lineName='" + lineName + "'" +
            ", spotIntroduce='" + spotIntroduce + "'" +
            ", lineDatetime='" + lineDatetime + "'" +
            ", lineCity='" + lineCity + "'" +
            ", dataCreator='" + dataCreator + "'" +
            ", dataUpdater='" + dataUpdater + "'" +
            ", dataCreateDatetime='" + dataCreateDatetime + "'" +
            ", dataUpdateDatetime='" + dataUpdateDatetime + "'" +
            ", dataStatus='" + dataStatus + "'" +
            '}';
    }
}
