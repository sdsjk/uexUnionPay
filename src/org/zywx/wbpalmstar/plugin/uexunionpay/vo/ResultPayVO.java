package org.zywx.wbpalmstar.plugin.uexunionpay.vo;

import java.io.Serializable;

public class ResultPayVO implements Serializable{
    private static final long serialVersionUID = -4172598485455171288L;
    private String payResult;

    public String getPayResult() {
        return payResult;
    }

    public void setPayResult(String payResult) {
        this.payResult = payResult;
    }
}
