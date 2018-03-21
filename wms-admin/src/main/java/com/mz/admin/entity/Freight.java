package com.mz.admin.entity;

import com.mz.common.entity.IEntity;

import java.math.BigDecimal;
import java.util.Date;

public class Freight implements IEntity{
    private Integer id;

    private Integer destination;

    private Integer packageType;

    private Integer productType;

    private BigDecimal initPrice;

    private BigDecimal initWeight;

    private BigDecimal steppingPrice;

    private BigDecimal steppingWeight;

    private String postcode;

    private Date gmtCreate;

    private Integer createUserId;

    private BigDecimal fuelCharge;

    private String remark;

    private Integer isDeleted;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getDestination() {
        return destination;
    }

    public void setDestination(Integer destination) {
        this.destination = destination;
    }

    public Integer getPackageType() {
        return packageType;
    }

    public void setPackageType(Integer packageType) {
        this.packageType = packageType;
    }

    public Integer getProductType() {
        return productType;
    }

    public void setProductType(Integer productType) {
        this.productType = productType;
    }

    public BigDecimal getInitPrice() {
        return initPrice;
    }

    public void setInitPrice(BigDecimal initPrice) {
        this.initPrice = initPrice;
    }

    public BigDecimal getInitWeight() {
        return initWeight;
    }

    public void setInitWeight(BigDecimal initWeight) {
        this.initWeight = initWeight;
    }

    public BigDecimal getSteppingPrice() {
        return steppingPrice;
    }

    public void setSteppingPrice(BigDecimal steppingPrice) {
        this.steppingPrice = steppingPrice;
    }

    public BigDecimal getSteppingWeight() {
        return steppingWeight;
    }

    public void setSteppingWeight(BigDecimal steppingWeight) {
        this.steppingWeight = steppingWeight;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode == null ? null : postcode.trim();
    }

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Integer getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Integer createUserId) {
        this.createUserId = createUserId;
    }

    public BigDecimal getFuelCharge() {
        return fuelCharge;
    }

    public void setFuelCharge(BigDecimal fuelCharge) {
        this.fuelCharge = fuelCharge;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
    }
}