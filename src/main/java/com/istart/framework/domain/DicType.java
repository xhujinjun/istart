package com.istart.framework.domain;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;
/**
 * 字典类型.
 */
@Entity
@Table(name = "dic_type")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "dictype")
public class DicType  implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name = "dic_type_code")
	private String dicTypeCode;
	
	@Column(name = "dic_type_name")
	private String dicTypeName;
	
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
        DicType dicType = (DicType) o;
        if(dicType.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, dicType.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

	@Override
	public String toString() {
		return "DicType [id=" + id + ", dicTypeCode=" + dicTypeCode + ", dicTypeName=" + dicTypeName + ", dataCreator="
				+ dataCreator + ", dataUpdater=" + dataUpdater + ", dataCreateDatetime=" + dataCreateDatetime
				+ ", dataUpdateDatetime=" + dataUpdateDatetime + ", dataStatus=" + dataStatus + "]";
	}
    
    
}
