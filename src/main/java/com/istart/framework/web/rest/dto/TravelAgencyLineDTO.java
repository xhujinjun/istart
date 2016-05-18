package com.istart.framework.web.rest.dto;

import java.time.ZonedDateTime;
import java.io.Serializable;
import java.util.Objects;


/**
 * A DTO for the TravelAgencyLine entity.
 */
public class TravelAgencyLineDTO implements Serializable {

    private Long id;

    private Long travelAgencyId;


    private String line_number;


    private String lineName;


    private String spotIntroduce;


    private ZonedDateTime lineDatetime;


    private String lineCity;


    private String dataCreator;


    private String dataUpdater;


    private ZonedDateTime dataCreateDatetime;


    private ZonedDateTime dataUpdateDatetime;


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

        TravelAgencyLineDTO travelAgencyLineDTO = (TravelAgencyLineDTO) o;

        if ( ! Objects.equals(id, travelAgencyLineDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "TravelAgencyLineDTO{" +
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
