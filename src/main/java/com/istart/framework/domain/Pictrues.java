package com.istart.framework.domain;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

/**
 * A Pictrues.
 */
@Entity
@Table(name = "products_pictrues")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "pictrues")
public class Pictrues implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	/**
	 * 产品编号
	 */
	@Column(name = "pno")
	private String pno;

	/**
	 * 图片路径
	 */
	@Column(name = "pic_path")
	private String picPath;

	@ManyToOne
	@JoinColumn(name = "pno", referencedColumnName = "pno", insertable = false, updatable = false)
	private Products prod;

	public Products getProd() {
		return prod;
	}

	public void setProd(Products prod) {
		this.prod = prod;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPno() {
		return pno;
	}

	public void setPno(String pno) {
		this.pno = pno;
	}

	public String getPicPath() {
		return picPath;
	}

	public void setPicPath(String picPath) {
		this.picPath = picPath;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		Pictrues pictrues = (Pictrues) o;
		if (pictrues.id == null || id == null) {
			return false;
		}
		return Objects.equals(id, pictrues.id);
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(id);
	}

	@Override
	public String toString() {
		return "Pictrues{" + "id=" + id + ", pno='" + pno + "'" + ", picPath='" + picPath + "'" + '}';
	}
}
