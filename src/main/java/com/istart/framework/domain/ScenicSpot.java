package com.istart.framework.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A ScenicSpot.
 */
@Entity
@Table(name = "scenic_spot")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "scenicspot")
@DynamicInsert
@DynamicUpdate
public class ScenicSpot implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "scenic_areas_id")
    private Integer scenicAreasId;

    @ManyToOne
    @JoinColumn(name = "scenic_areas_id",insertable = false,updatable = false)
    private ScenicArea scenicArea;
    @Column(name = "name")
    private String name;

    @Lob
    @Column(name = "introduce")
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
        ScenicSpot scenicSpot = (ScenicSpot) o;
        if(scenicSpot.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, scenicSpot.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ScenicSpot{" +
            "id=" + id +
            ", scenicAreasId='" + scenicAreasId + "'" +
            ", name='" + name + "'" +
            ", introduce='" + introduce + "'" +
            '}';
    }
}
