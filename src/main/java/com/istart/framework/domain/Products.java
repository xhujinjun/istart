package com.istart.framework.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 旅游产品
 */
@Entity
@Table(name = "products")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "products")
public class Products implements Serializable {

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
	 * 旅行社id
	 */
	@Column(name = "travel_agent_id")
	private Long travelAgentId;

	@ManyToOne
	@JoinColumn(name = "travel_agent_id", insertable = false, updatable = false)
	private TravelAgency travelAgency;
	/**
	 * 客服电话
	 */
	@Column(name = "phone")
	private String phone;
	/**
	 * 标题
	 */
	@Column(name = "title")
	private String title;
	/**
	 * 产品价格
	 */
	@Column(name = "price", precision = 10, scale = 2)
	private BigDecimal price;
	/**
	 * 起价说明
	 */
	@Column(name = "pricedesc")
	private String pricedesc;
	/**
	 * 优惠信息
	 */
	@Column(name = "preferential")
	private String preferential;
	/**
	 * 出发时间
	 */
	@Column(name = "startdate")
	private LocalDate startdate;
	/**
	 * 出发地
	 */
	@Column(name = "startadderss")
	private String startadderss;
	/**
	 * 目的地
	 */
	@Column(name = "endadderss")
	private String endadderss;
	/**
	 * 出游天数
	 */
	@Column(name = "days")
	private Integer days;
	/**
	 * 费用说明
	 */
	@Column(name = "costdesc")
	private String costdesc;
	/**
	 * 路线
	 */
	@Column(name = "route")
	private String route;
	/**
	 * 产品描述
	 */
	@Column(name = "detaildesc")
	private String detaildesc;
	/**
	 * 预定须知
	 */
	@Column(name = "book_notice")
	private String bookNotice;
	/**
	 * 具体行程
	 */
	@Column(name = "detail_trip")
	private String detailTrip;
	/**
	 * 好评率
	 */
	@Column(name = "rate")
	private String rate;
	/**
	 * 类型（大类）
	 */
	@Column(name = "tourism_types_id")
	private Long tourismTypesId;

	@ManyToOne
	@JoinColumn(name = "tourism_types_id", insertable = false, updatable = false)
	private Dic tourismType;
	/**
	 * 类型（小类）
	 */
	@Column(name = "detail_type_id")
	private Long detailTypeId;

	@ManyToOne
	@JoinColumn(name = "detail_type_id", insertable = false, updatable = false)
	private Dic detailType;
	/**
	 * 创建人
	 */
	@Column(name = "data_creator")
	private String dataCreator;
	/**
	 * 更新人
	 */
	@Column(name = "data_updater")
	private String dataUpdater;
	/**
	 * 创建时间
	 */
	@Column(name = "data_create_datetime")
	private LocalDate dataCreateDatetime;
	/**
	 * 更新时间
	 */
	@Column(name = "data_update_datetime")
	private LocalDate dataUpdateDatetime;
	/**
	 * 状态
	 */
	@Column(name = "data_status")
	private Integer dataStatus;

	/**
	 * 产品具体行程
	 */
	@OneToMany(mappedBy = "prod")
	@JsonIgnore
	@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
	private Set<Trip> trips = new HashSet<>();

	/**
	 * 相关图片
	 */
	@OneToMany(mappedBy = "prod")
	@JsonIgnore
	@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
	private Set<Pictrues> pictrues = new HashSet<>();

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

	public Long getTravelAgentId() {
		return travelAgentId;
	}

	public void setTravelAgentId(Long travelAgentId) {
		this.travelAgentId = travelAgentId;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
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

	public LocalDate getStartdate() {
		return startdate;
	}

	public void setStartdate(LocalDate startdate) {
		this.startdate = startdate;
	}

	public String getStartadderss() {
		return startadderss;
	}

	public void setStartadderss(String startadderss) {
		this.startadderss = startadderss;
	}

	public String getEndadderss() {
		return endadderss;
	}

	public void setEndadderss(String endadderss) {
		this.endadderss = endadderss;
	}

	public Integer getDays() {
		return days;
	}

	public void setDays(Integer days) {
		this.days = days;
	}

	public String getCostdesc() {
		return costdesc;
	}

	public void setCostdesc(String costdesc) {
		this.costdesc = costdesc;
	}

	public String getRoute() {
		return route;
	}

	public void setRoute(String route) {
		this.route = route;
	}

	public String getDetaildesc() {
		return detaildesc;
	}

	public void setDetaildesc(String detaildesc) {
		this.detaildesc = detaildesc;
	}

	public String getBookNotice() {
		return bookNotice;
	}

	public void setBookNotice(String bookNotice) {
		this.bookNotice = bookNotice;
	}

	public String getRate() {
		return rate;
	}

	public void setRate(String rate) {
		this.rate = rate;
	}

	public Long getTourismTypesId() {
		return tourismTypesId;
	}

	public void setTourismTypesId(Long tourismTypesId) {
		this.tourismTypesId = tourismTypesId;
	}

	public Long getDetailTypeId() {
		return detailTypeId;
	}

	public void setDetailTypeId(Long detailTypeId) {
		this.detailTypeId = detailTypeId;
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

	public LocalDate getDataCreateDatetime() {
		return dataCreateDatetime;
	}

	public void setDataCreateDatetime(LocalDate dataCreateDatetime) {
		this.dataCreateDatetime = dataCreateDatetime;
	}

	public LocalDate getDataUpdateDatetime() {
		return dataUpdateDatetime;
	}

	public void setDataUpdateDatetime(LocalDate dataUpdateDatetime) {
		this.dataUpdateDatetime = dataUpdateDatetime;
	}

	public Integer getDataStatus() {
		return dataStatus;
	}

	public void setDataStatus(Integer dataStatus) {
		this.dataStatus = dataStatus;
	}

	public Set<Trip> getTrips() {
		return trips;
	}

	public void setTrips(Set<Trip> trips) {
		this.trips = trips;
	}

	public Set<Pictrues> getPictrues() {
		return pictrues;
	}

	public void setPictrues(Set<Pictrues> pictrues) {
		this.pictrues = pictrues;
	}

	public TravelAgency getTravelAgency() {
		return travelAgency;
	}

	public void setTravelAgency(TravelAgency travelAgency) {
		this.travelAgency = travelAgency;
	}

	public Dic getTourismType() {
		return tourismType;
	}

	public void setTourismType(Dic tourismType) {
		this.tourismType = tourismType;
	}

	public Dic getDetailType() {
		return detailType;
	}

	public void setDetailType(Dic detailType) {
		this.detailType = detailType;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		Products products = (Products) o;
		if (products.id == null || id == null) {
			return false;
		}
		return Objects.equals(id, products.id);
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(id);
	}

	public String getDetailTrip() {
		return detailTrip;
	}

	public void setDetailTrip(String detailTrip) {
		this.detailTrip = detailTrip;
	}

	@Override
	public String toString() {
		return "Products [id=" + id + ", pno=" + pno + ", travelAgentId=" + travelAgentId + ", travelAgency="
				+ travelAgency + ", phone=" + phone + ", title=" + title + ", price=" + price + ", pricedesc="
				+ pricedesc + ", preferential=" + preferential + ", startdate=" + startdate + ", startadderss="
				+ startadderss + ", endadderss=" + endadderss + ", days=" + days + ", costdesc=" + costdesc + ", route="
				+ route + ", detaildesc=" + detaildesc + ", bookNotice=" + bookNotice + ", detailTrip=" + detailTrip
				+ ", rate=" + rate + ", tourismTypesId=" + tourismTypesId + ", tourismType=" + tourismType
				+ ", detailTypeId=" + detailTypeId + ", detailType=" + detailType + ", dataCreator=" + dataCreator
				+ ", dataUpdater=" + dataUpdater + ", dataCreateDatetime=" + dataCreateDatetime
				+ ", dataUpdateDatetime=" + dataUpdateDatetime + ", dataStatus=" + dataStatus + ", trips=" + trips
				+ ", pictrues=" + pictrues + "]";
	}

}
