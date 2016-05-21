package com.istart.framework.web.rest.dto;

import java.time.ZonedDateTime;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;


/**
 * A DTO for the Dic entity.
 */
public class DicDTO implements Serializable {

    private Long id;

    private Long dicTypeId;


    private String dicCode;


    private String dicName;


    private String dicNameDefinition;


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
    public String getDicCode() {
        return dicCode;
    }

    public void setDicCode(String dicCode) {
        this.dicCode = dicCode;
    }
    public String getDicName() {
        return dicName;
    }

    public void setDicName(String dicName) {
        this.dicName = dicName;
    }
    public String getDicNameDefinition() {
        return dicNameDefinition;
    }

    public void setDicNameDefinition(String dicNameDefinition) {
        this.dicNameDefinition = dicNameDefinition;
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

    public Long getDicTypeId() {
        return dicTypeId;
    }

    public void setDicTypeId(Long dicTypeId) {
        this.dicTypeId = dicTypeId;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        DicDTO dicDTO = (DicDTO) o;

        if ( ! Objects.equals(id, dicDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "DicDTO{" +
            "id=" + id +
            ", dicTypeId='" + dicTypeId + "'" +
            ", dicCode='" + dicCode + "'" +
            ", dicName='" + dicName + "'" +
            ", dicNameDefinition='" + dicNameDefinition + "'" +
            ", dataCreator='" + dataCreator + "'" +
            ", dataUpdater='" + dataUpdater + "'" +
            ", dataCreateDatetime='" + dataCreateDatetime + "'" +
            ", dataUpdateDatetime='" + dataUpdateDatetime + "'" +
            ", dataStatus='" + dataStatus + "'" +
            '}';
    }
}
