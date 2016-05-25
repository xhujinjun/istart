package com.istart.framework.web.rest.dto;

import java.time.LocalDate;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


/**
 * A DTO for the Book entity.
 */
public class HotelDTO implements Serializable {
	    private Long id;
	    private String name;
	    private Long brandId;
	    private String level;
	    private Integer busiStatus;
	    private Integer score;
	    private String creator;
	    private String updater;
	    private Date createDatetime;
	    private Date updateDatetime;
	    private Integer status;
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
		public Long getBrandId() {
			return brandId;
		}
		public void setBrandId(Long brandId) {
			this.brandId = brandId;
		}
		public String getLevel() {
			return level;
		}
		public void setLevel(String level) {
			this.level = level;
		}
		public Integer getBusiStatus() {
			return busiStatus;
		}
		public void setBusiStatus(Integer busiStatus) {
			this.busiStatus = busiStatus;
		}
		public Integer getScore() {
			return score;
		}
		public void setScore(Integer score) {
			this.score = score;
		}
		public String getCreator() {
			return creator;
		}
		public void setCreator(String creator) {
			this.creator = creator;
		}
		public String getUpdater() {
			return updater;
		}
		public void setUpdater(String updater) {
			this.updater = updater;
		}
		public Date getCreateDatetime() {
			return createDatetime;
		}
		public void setCreateDatetime(Date createDatetime) {
			this.createDatetime = createDatetime;
		}
		public Date getUpdateDatetime() {
			return updateDatetime;
		}
		public void setUpdateDatetime(Date updateDatetime) {
			this.updateDatetime = updateDatetime;
		}
		public Integer getStatus() {
			return status;
		}
		public void setStatus(Integer status) {
			this.status = status;
		}


}
