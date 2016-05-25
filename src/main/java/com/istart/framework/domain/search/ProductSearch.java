package com.istart.framework.domain.search;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * 封装查询条件
 * 
 * @author Administrator
 *
 */
public class ProductSearch extends PageSearch {

	// 查询条件：天数，起始日期，出发地，价格
	private Integer days;
	private LocalDate startdate;
	private BigDecimal price;
	private String start;

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

	public String getStart() {
		return start;
	}

	public void setStart(String start) {
		this.start = start;
	}

	@Override
	public String toString() {
		return "PageSearch [page=" + this.page + ", size=" + this.size + ", direction=" + this.direction
				+ "]ProductSearch [days=" + days + ", startdate=" + startdate + ", price=" + price + ", start=" + start
				+ "]";
	}

}
