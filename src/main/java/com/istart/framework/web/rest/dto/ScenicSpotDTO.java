package com.istart.framework.web.rest.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;


/**
 * A DTO for the ScenicSpot entity.
 */
public class ScenicSpotDTO implements Serializable {

    private Long id;

    private Integer scenicAreasId;


    private String name;


    @Lob
    private String introduce;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public Integer getScenicAreasId() {
        return scenicAreasId;
    }

    public void setScenicAreasId(Integer scenicAreasId) {
        this.scenicAreasId = scenicAreasId;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ScenicSpotDTO scenicSpotDTO = (ScenicSpotDTO) o;

        if ( ! Objects.equals(id, scenicSpotDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ScenicSpotDTO{" +
            "id=" + id +
            ", scenicAreasId='" + scenicAreasId + "'" +
            ", name='" + name + "'" +
            ", introduce='" + introduce + "'" +
            '}';
    }
}
