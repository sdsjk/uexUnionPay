package org.zywx.wbpalmstar.plugin.uexunionpay.vo;

import java.io.Serializable;

public class PayDataVO implements Serializable{
    private static final long serialVersionUID = -3655890593502980873L;
    private String orderInfo;
    private String mode;

    public String getOrderInfo() {
        return orderInfo;
    }

    public void setOrderInfo(String orderInfo) {
        this.orderInfo = orderInfo;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }
}
