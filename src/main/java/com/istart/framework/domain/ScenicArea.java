package com.istart.framework.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Generated;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A ScenicArea.
 */
@Entity
@Table(name = "scenic_area")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "scenicarea")
@DynamicInsert
@DynamicUpdate
public class ScenicArea implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "scenic_star")
    private String scenicStar;

    @Column(name = "score", precision=10, scale=2)
    private BigDecimal score;

    @Column(name = "interest_num")
    private Integer interestNum;

    @Column(name = "ticket", precision=10, scale=2)
    private BigDecimal ticket;

    @Column(name = "ticket_desc")
    private String ticketDesc;

    @Column(name = "open_start_time")
    private ZonedDateTime openStartTime;

    @Column(name = "open_end_time")
    private ZonedDateTime openEndTime;

    @Column(name = "open_desc")
    private String openDesc;

    @Column(name = "play_time_num", precision=10, scale=2)
    private BigDecimal playTimeNum;

    @Column(name = "contact")
    private String contact;

    @Column(name = "website")
    private String website;

    @Lob
    @Column(name = "overview")
    private String overview;

    @Column(name = "image_path")
    private String imagePath;

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

    @OneToMany(mappedBy = "scenicArea")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<ScenicSpot> scenicSpots = new HashSet<>();

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

    public Set<ScenicSpot> getScenicSpots() {
        return scenicSpots;
    }

    public void setScenicSpots(Set<ScenicSpot> scenicSpots) {
        this.scenicSpots = scenicSpots;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ScenicArea scenicArea = (ScenicArea) o;
        if(scenicArea.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, scenicArea.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ScenicArea{" +
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
            ", imagePath='" + imagePath + "'" +
            ", dataCreator='" + dataCreator + "'" +
            ", dataUpdater='" + dataUpdater + "'" +
            ", dataCreateDatetime='" + dataCreateDatetime + "'" +
            ", dataUpdateDatetime='" + dataUpdateDatetime + "'" +
            ", dataStatus='" + dataStatus + "'" +
            '}';
    }
}
