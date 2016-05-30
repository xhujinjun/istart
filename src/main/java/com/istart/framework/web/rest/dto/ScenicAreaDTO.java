package com.istart.framework.web.rest.dto;

import java.time.ZonedDateTime;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import javax.persistence.Lob;


/**
 * A DTO for the ScenicArea entity.
 */
public class ScenicAreaDTO implements Serializable {

    private Long id;

    private String name;


    private String scenicStar;


    private BigDecimal score;


    private Integer interestNum;


    private BigDecimal ticket;


    private String ticketDesc;


    private ZonedDateTime openStartTime;


    private ZonedDateTime openEndTime;


    private String openDesc;


    private BigDecimal playTimeNum;


    private String contact;


    private String website;


    @Lob
    private String overview;


    private String imagePath;


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
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getScenicStar() {
        return scenicStar;
    }

    public void setScenicStar(String scenicStar) {
        this.scenicStar = scenicStar;
    }
    public BigDecimal getScore() {
        return score;
    }

    public void setScore(BigDecimal score) {
        this.score = score;
    }
    public Integer getInterestNum() {
        return interestNum;
    }

    public void setInterestNum(Integer interestNum) {
        this.interestNum = interestNum;
    }
    public BigDecimal getTicket() {
        return ticket;
    }

    public void setTicket(BigDecimal ticket) {
        this.ticket = ticket;
    }
    public String getTicketDesc() {
        return ticketDesc;
    }

    public void setTicketDesc(String ticketDesc) {
        this.ticketDesc = ticketDesc;
    }
    public ZonedDateTime getOpenStartTime() {
        return openStartTime;
    }

    public void setOpenStartTime(ZonedDateTime openStartTime) {
        this.openStartTime = openStartTime;
    }
    public ZonedDateTime getOpenEndTime() {
        return openEndTime;
    }

    public void setOpenEndTime(ZonedDateTime openEndTime) {
        this.openEndTime = openEndTime;
    }
    public String getOpenDesc() {
        return openDesc;
    }

    public void setOpenDesc(String openDesc) {
        this.openDesc = openDesc;
    }
    public BigDecimal getPlayTimeNum() {
        return playTimeNum;
    }

    public void setPlayTimeNum(BigDecimal playTimeNum) {
        this.playTimeNum = playTimeNum;
    }
    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }
    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }
    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }
    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
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

        ScenicAreaDTO scenicAreaDTO = (ScenicAreaDTO) o;

        if ( ! Objects.equals(id, scenicAreaDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ScenicAreaDTO{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", scenicStar='" + scenicStar + "'" +
            ", score='" + score + "'" +
            ", interestNum='" + interestNum + "'" +
            ", ticket='" + ticket + "'" +
            ", ticketDesc='" + ticketDesc + "'" +
            ", openStartTime='" + openStartTime + "'" +
            ", openEndTime='" + openEndTime + "'" +
            ", openDesc='" + openDesc + "'" +
            ", playTimeNum='" + playTimeNum + "'" +
            ", contact='" + contact + "'" +
            ", website='" + website + "'" +
            ", overview='" + overview + "'" +
            ", imagePath='" + imagePath + "'" +
            ", dataCreator='" + dataCreator + "'" +
            ", dataUpdater='" + dataUpdater + "'" +
            ", dataCreateDatetime='" + dataCreateDatetime + "'" +
            ", dataUpdateDatetime='" + dataUpdateDatetime + "'" +
            ", dataStatus='" + dataStatus + "'" +
            '}';
    }
}
