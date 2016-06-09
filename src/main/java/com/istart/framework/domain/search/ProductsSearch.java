package com.istart.framework.domain.search;

import java.math.BigDecimal;
import java.time.LocalDate;

public class ProductsSearch {
	// 查询条件：天数，起始日期，出发地，价格
	private Integer days;
	private LocalDate startdate;
	private BigDecimal price;
	private String startadderss;

	public Integer getDays() {
		return days;
	}

	public void setDays(Integer days) {
		this.days = days;
	}

	public LocalDate getStartdate() {
		return startdate;
	}

	public void setStartdate(LocalDate startdate) {
		this.startdate = startdate;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public String getStartadderss() {
		return startadderss;
	}

	public void setStartadderss(String startadderss) {
		this.startadderss = startadderss;
	}

	@Override
	public String toString() {
		return "ProductsSearch [days=" + days + ", startdate=" + startdate + ", price=" + price + ", startadderss="
				+ startadderss + "]";
	}

}
