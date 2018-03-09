package com.mz.admin.entity;

import com.mz.common.entity.IEntity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 仓管费配置
 */
public class CargoCharge implements IEntity{
    private Long id;

    private Byte expireTime;

    private BigDecimal cargoFee;

    private Date gmtCreate;

    private Date gmtModify;

    private Byte isDeleted;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Byte getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Byte expireTime) {
        this.expireTime = expireTime;
    }

    public BigDecimal getCargoFee() {
        return cargoFee;
    }

    public void setCargoFee(BigDecimal cargoFee) {
        this.cargoFee = cargoFee;
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

    public Byte getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Byte isDeleted) {
        this.isDeleted = isDeleted;
    }
}