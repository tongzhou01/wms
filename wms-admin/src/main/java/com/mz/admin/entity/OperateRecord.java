package com.mz.admin.entity;

import com.mz.common.entity.IEntity;

import java.util.Date;

public class OperateRecord implements IEntity {
    private Long id;

    private Long cargoId;

    private Long operator;

    private Date scanTime;

    private Date type;

    private Long outboundTypeId;

    private Byte isDeleted;

    private String shelfNo;

    public String getShelfNo() {
        return shelfNo;
    }

    public void setShelfNo(String shelfNo) {
        this.shelfNo = shelfNo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCargoId() {
        return cargoId;
    }

    public void setCargoId(Long cargoId) {
        this.cargoId = cargoId;
    }

    public Long getOperator() {
        return operator;
    }

    public void setOperator(Long operator) {
        this.operator = operator;
    }

    public Date getScanTime() {
        return scanTime;
    }

    public void setScanTime(Date scanTime) {
        this.scanTime = scanTime;
    }

    public Date getType() {
        return type;
    }

    public void setType(Date type) {
        this.type = type;
    }

    public Long getOutboundTypeId() {
        return outboundTypeId;
    }

    public void setOutboundTypeId(Long outboundTypeId) {
        this.outboundTypeId = outboundTypeId;
    }

    public Byte getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Byte isDeleted) {
        this.isDeleted = isDeleted;
    }
}