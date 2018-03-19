package com.mz.admin.entity;

import com.mz.common.entity.IEntity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author tongzhou
 * @description 货物信息
 * @date 2018/3/8 11:30
 */
public class CargoInfo implements IEntity {
    private Long id;

    private String cnNo;

    private String intlNo;

    private String orderNo;

    private String customerName;

    private String customerMobile;

    private String customerNo;

    private String expressCompanyCode;

    private String expressCompany;

    private String shelfNo;

    private String destination;

    private BigDecimal weight;

    private BigDecimal length;

    private BigDecimal wide;

    private BigDecimal height;

    private BigDecimal volumeWeight;

    private BigDecimal cargoCharge;

    private BigDecimal expressCharge;

    private Date gmtCreate;

    private Date gmtModify;

    private Byte status;

    private Byte isDeleted;

    public String getCustomerMobile() {
        return customerMobile;
    }

    public void setCustomerMobile(String customerMobile) {
        this.customerMobile = customerMobile;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCnNo() {
        return cnNo;
    }

    public void setCnNo(String cnNo) {
        this.cnNo = cnNo == null ? null : cnNo.trim();
    }

    public String getIntlNo() {
        return intlNo;
    }

    public void setIntlNo(String intlNo) {
        this.intlNo = intlNo == null ? null : intlNo.trim();
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo == null ? null : orderNo.trim();
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName == null ? null : customerName.trim();
    }

    public String getCustomerNo() {
        return customerNo;
    }

    public void setCustomerNo(String customerNo) {
        this.customerNo = customerNo == null ? null : customerNo.trim();
    }

    public String getExpressCompanyCode() {
        return expressCompanyCode;
    }

    public void setExpressCompanyCode(String expressCompanyCode) {
        this.expressCompanyCode = expressCompanyCode == null ? null : expressCompanyCode.trim();
    }

    public String getExpressCompany() {
        return expressCompany;
    }

    public void setExpressCompany(String expressCompany) {
        this.expressCompany = expressCompany == null ? null : expressCompany.trim();
    }

    public String getShelfNo() {
        return shelfNo;
    }

    public void setShelfNo(String shelfNo) {
        this.shelfNo = shelfNo == null ? null : shelfNo.trim();
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination == null ? null : destination.trim();
    }

    public BigDecimal getWeight() {
        return weight;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    public BigDecimal getLength() {
        return length;
    }

    public void setLength(BigDecimal length) {
        this.length = length;
    }

    public BigDecimal getWide() {
        return wide;
    }

    public void setWide(BigDecimal wide) {
        this.wide = wide;
    }

    public BigDecimal getHeight() {
        return height;
    }

    public void setHeight(BigDecimal height) {
        this.height = height;
    }

    public BigDecimal getVolumeWeight() {
        return volumeWeight;
    }

    public void setVolumeWeight(BigDecimal volumeWeight) {
        this.volumeWeight = volumeWeight;
    }

    public BigDecimal getCargoCharge() {
        return cargoCharge;
    }

    public void setCargoCharge(BigDecimal cargoCharge) {
        this.cargoCharge = cargoCharge;
    }

    public BigDecimal getExpressCharge() {
        return expressCharge;
    }

    public void setExpressCharge(BigDecimal expressCharge) {
        this.expressCharge = expressCharge;
    }

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Date getGmtModify() {
        return gmtModify;
    }

    public void setGmtModify(Date gmtModify) {
        this.gmtModify = gmtModify;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public Byte getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Byte isDeleted) {
        this.isDeleted = isDeleted;
    }
}