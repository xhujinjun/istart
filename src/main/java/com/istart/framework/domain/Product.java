package com.istart.framework.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.elasticsearch.annotations.Document;

/**
 * A Product.
 */
@Entity
@Table(name = "product")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "product")
@DynamicInsert
@DynamicUpdate
public class Product implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "pno")
	private String pno;

	@Column(name = "title")
	private String title;

	@Column(name = "price", precision = 10, scale = 2)
	private BigDecimal price;

	@Column(name = "pricedesc")
	private String pricedesc;

	@Column(name = "preferential")
	private String preferential;

	@Column(name = "costdesc")
	private String costdesc;

	@Column(name = "trip")
	private String trip;

	@Column(name = "start")
	private String start;

	@Column(name = "route")
	private String route;

	@Column(name = "prodesc")
	private String prodesc;

	@Column(name = "rate")
	private String rate;

	@Column(name = "state")
	private String state;

	@Column(name = "type")
	private String type;

	@Column(name = "startdate")
	private LocalDate startdate;

	@Column(name = "pics")
	private String pics;

	@Column(name = "days")
	private Integer days;

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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public String getPricedesc() {
		return pricedesc;
	}

	public void setPricedesc(String pricedesc) {
		this.pricedesc = pricedesc;
	}

	public String getPreferential() {
		return preferential;
	}

	public void setPreferential(String preferential) {
		this.preferential = preferential;
	}

	public String getCostdesc() {
		return costdesc;
	}

	public void setCostdesc(String costdesc) {
		this.costdesc = costdesc;
	}

	public String getTrip() {
		return trip;
	}

	public void setTrip(String trip) {
		this.trip = trip;
	}

	public String getStart() {
		return start;
	}

	public void setStart(String start) {
		this.start = start;
	}

	public String getRoute() {
		return route;
	}

	public void setRoute(String route) {
		this.route = route;
	}

	public String getProdesc() {
		return prodesc;
	}

	public void setProdesc(String prodesc) {
		this.prodesc = prodesc;
	}

	public String getRate() {
		return rate;
	}

	public void setRate(String rate) {
		this.rate = rate;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public LocalDate getStartdate() {
		return startdate;
	}

	public void setStartdate(LocalDate startdate) {
		this.startdate = startdate;
	}

	public String getPics() {
		return pics;
	}

	public void setPics(String pics) {
		this.pics = pics;
	}

	public Integer getDays() {
		return days;
	}

	public void setDays(Integer days) {
		this.days = days;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		Product product = (Product) o;
		if (product.id == null || id == null) {
			return false;
		}
		return Objects.equals(id, product.id);
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(id);
	}

	@Override
	public String toString() {
		return "Product{" + "id=" + id + ", pno='" + pno + "'" + ", title='" + title + "'" + ", price='" + price + "'"
				+ ", pricedesc='" + pricedesc + "'" + ", preferential='" + preferential + "'" + ", costdesc='"
				+ costdesc + "'" + ", trip='" + trip + "'" + ", start='" + start + "'" + ", route='" + route + "'"
				+ ", prodesc='" + prodesc + "'" + ", rate='" + rate + "'" + ", state='" + state + "'" + ", type='"
				+ type + "'" + ", startdate='" + startdate + "'" + ", pics='" + pics + "'" + ", days='" + days + "'"
				+ '}';
	}
}
