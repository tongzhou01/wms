package com.mz.admin.entity;

import com.mz.common.entity.IEntity;

import java.util.Date;

public class OperateRecord implements IEntity {
    private Long id;

    private Long cargoId;

    private Long operator;

    private Date outboundTime;

    private Date type;

    private Long outboundTypeId;

    private Byte isDeleted;

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

    public Date getOutboundTime() {
        return outboundTime;
    }

    public void setOutboundTime(Date outboundTime) {
        this.outboundTime = outboundTime;
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