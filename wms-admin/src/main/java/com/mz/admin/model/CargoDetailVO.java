package com.mz.admin.model;

import com.mz.admin.entity.CargoInfo;
import com.mz.admin.entity.CustomerInfo;
import com.mz.admin.entity.OperateRecord;

/**
 * 货物-货架信息
 *
 * @author tongzhou
 * @date 2018-03-23 11:31
 **/
public class CargoDetailVO extends CargoInfo {

    OperateRecord operateRecord;

    CustomerInfo customerInfo;

    public OperateRecord getOperateRecord() {
        return operateRecord;
    }

    public void setOperateRecord(OperateRecord operateRecord) {
        this.operateRecord = operateRecord;
    }

    public CustomerInfo getCustomerInfo() {
        return customerInfo;
    }

    public void setCustomerInfo(CustomerInfo customerInfo) {
        this.customerInfo = customerInfo;
    }
}
