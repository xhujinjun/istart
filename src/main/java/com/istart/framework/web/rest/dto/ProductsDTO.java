package com.istart.framework.web.rest.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

import com.istart.framework.domain.Dic;

/**
 * A DTO for the Products entity.
 */
public class ProductsDTO implements Serializable {

	private Long id;

	private String pno;

	private Long travelAgentId;

	private String phone;

	private String title;

	private BigDecimal price;

	private String pricedesc;

	private String preferential;

	private LocalDate startdate;

	private String startadderss;

	private String endadderss;

	private Integer days;

	private String costdesc;

	private String route;

	private String detaildesc;

	private String bookNotice;

	private String rate;

	private String detailTrip;

	private Long tourismTypesId;

	private Long detailTypeId;

	private String dataCreator;

	private String dataUpdater;

	private LocalDate dataCreateDatetime;

	private LocalDate dataUpdateDatetime;

	private Integer dataStatus;

	private Dic tourismType;

	public Dic getTourismType() {
		return tourismType;
	}

	public void setTourismType(Dic tourismType) {
		this.tourismType = tourismType;
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

	public String getDetailTrip() {
		return detailTrip;
	}

	public void setDetailTrip(String detailTrip) {
		this.detailTrip = detailTrip;
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

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		ProductsDTO productsDTO = (ProductsDTO) o;

		if (!Objects.equals(id, productsDTO.id))
			return false;

		return true;
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(id);
	}

	@Override
	public String toString() {
		return "ProductsDTO{" + "id=" + id + ", pno='" + pno + "'" + ", travelAgentId='" + travelAgentId + "'"
				+ ", phone='" + phone + "'" + ", title='" + title + "'" + ", price='" + price + "'" + ", pricedesc='"
				+ pricedesc + "'" + ", preferential='" + preferential + "'" + ", startdate='" + startdate + "'"
				+ ", startadderss='" + startadderss + "'" + ", endadderss='" + endadderss + "'" + ", days='" + days
				+ "'" + ", costdesc='" + costdesc + "'" + ", route='" + route + "'" + ", detaildesc='" + detaildesc
				+ "'" + ", bookNotice='" + bookNotice + "'" + ", rate='" + rate + "'" + ", tourismTypesId='"
				+ tourismTypesId + "'" + ", detailTypeId='" + detailTypeId + "'" + ", dataCreator='" + dataCreator + "'"
				+ ", dataUpdater='" + dataUpdater + "'" + ", dataCreateDatetime='" + dataCreateDatetime + "'"
				+ ", dataUpdateDatetime='" + dataUpdateDatetime + "'" + ", dataStatus='" + dataStatus + "'" + '}';
	}
}
