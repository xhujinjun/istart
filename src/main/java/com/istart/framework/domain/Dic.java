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
@Table(name = "dic")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "dic")
public class Dic implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name = "dic_type_id")
	private Long dicTypeId;
	
	@Column(name = "dic_code")
	private String dicCode;
	
	@Column(name = "dic_name")
	private String dicName;
	
	@Column(name = "dic_name_definition")
	private String dicNameDefinition;
	
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

	public Long getDicTypeId() {
		return dicTypeId;
	}

	public void setDicTypeId(Long dicTypeId) {
		this.dicTypeId = dicTypeId;
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
	
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Dic dic = (Dic) o;
        if(dic.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, dic.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

	@Override
	public String toString() {
		return "Dic [id=" + id + ", dicTypeId=" + dicTypeId + ", dicCode=" + dicCode + ", dicName=" + dicName
				+ ", dicNameDefinition=" + dicNameDefinition + ", dataCreator=" + dataCreator + ", dataUpdater="
				+ dataUpdater + ", dataCreateDatetime=" + dataCreateDatetime + ", dataUpdateDatetime="
				+ dataUpdateDatetime + ", dataStatus=" + dataStatus + "]";
	}
    
}
