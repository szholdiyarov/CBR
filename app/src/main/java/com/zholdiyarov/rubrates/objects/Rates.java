package com.zholdiyarov.rubrates.objects;

/**
 * Created by szholdiyarov on 2/17/16.
 */
public class Rates {
    private String charCode;
    private String rate;
    private String nominal;

    public Rates(String charCode, String rate, String nominal) {
        this.charCode = charCode;
        this.rate = rate;
        this.nominal = nominal;
    }

    public String getCharCode() {
        return charCode;
    }

    public String getRate() {
        return rate;
    }

    public String getNominal() {
        return nominal;
    }


}
