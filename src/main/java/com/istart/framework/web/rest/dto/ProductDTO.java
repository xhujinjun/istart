package com.istart.framework.web.rest.dto;

import java.time.LocalDate;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;


/**
 * A DTO for the Product entity.
 */
public class ProductDTO implements Serializable {

    private Long id;

    private String pno;

    private String title;

    private BigDecimal price;

    private String pricedesc;

    private String preferential;

    private String costdesc;

    private String trip;

    private String start;

    private String route;

    private String prodesc;

    private String rate;

    private String state;

    private String type;

    private LocalDate startdate;

    private String pics;

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

        ProductDTO productDTO = (ProductDTO) o;

        if ( ! Objects.equals(id, productDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ProductDTO{" +
            "id=" + id +
            ", pno='" + pno + "'" +
            ", title='" + title + "'" +
            ", price='" + price + "'" +
            ", pricedesc='" + pricedesc + "'" +
            ", preferential='" + preferential + "'" +
            ", costdesc='" + costdesc + "'" +
            ", trip='" + trip + "'" +
            ", start='" + start + "'" +
            ", route='" + route + "'" +
            ", prodesc='" + prodesc + "'" +
            ", rate='" + rate + "'" +
            ", state='" + state + "'" +
            ", type='" + type + "'" +
            ", startdate='" + startdate + "'" +
            ", pics='" + pics + "'" +
            ", days='" + days + "'" +
            '}';
    }
}
