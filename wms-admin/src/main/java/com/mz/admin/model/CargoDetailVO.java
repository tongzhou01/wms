package com.mz.admin.model;

import com.mz.admin.entity.CargoInfo;
import com.mz.admin.entity.CustomerInfo;
import com.mz.admin.entity.OperateRecord;

import java.util.List;

/**
 * 货物-货架信息
 *
 * @author tongzhou
 * @date 2018-03-23 11:31
 **/
public class CargoDetailVO extends CargoInfo {

    List<OperateRecord> operateRecords;

    CustomerInfo customerInfo;

    public List<OperateRecord> getOperateRecords() {
        return operateRecords;
    }

    public void setOperateRecords(List<OperateRecord> operateRecords) {
        this.operateRecords = operateRecords;
    }

    public CustomerInfo getCustomerInfo() {
        return customerInfo;
    }

    public void setCustomerInfo(CustomerInfo customerInfo) {
        this.customerInfo = customerInfo;
    }
}
