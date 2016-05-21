package com.istart.framework.web.rest.dto;

import java.time.ZonedDateTime;
import java.io.Serializable;
import java.util.Objects;


/**
 * A DTO for the DicType entity.
 */
public class DicTypeDTO implements Serializable {

    private Long id;

    private String dicTypeCode;


    private String dicTypeName;


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
    public String getDicTypeCode() {
        return dicTypeCode;
    }

    public void setDicTypeCode(String dicTypeCode) {
        this.dicTypeCode = dicTypeCode;
    }
    public String getDicTypeName() {
        return dicTypeName;
    }

    public void setDicTypeName(String dicTypeName) {
        this.dicTypeName = dicTypeName;
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

        DicTypeDTO dicTypeDTO = (DicTypeDTO) o;

        if ( ! Objects.equals(id, dicTypeDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "DicTypeDTO{" +
            "id=" + id +
            ", dicTypeCode='" + dicTypeCode + "'" +
            ", dicTypeName='" + dicTypeName + "'" +
            ", dataCreator='" + dataCreator + "'" +
            ", dataUpdater='" + dataUpdater + "'" +
            ", dataCreateDatetime='" + dataCreateDatetime + "'" +
            ", dataUpdateDatetime='" + dataUpdateDatetime + "'" +
            ", dataStatus='" + dataStatus + "'" +
            '}';
    }
}
