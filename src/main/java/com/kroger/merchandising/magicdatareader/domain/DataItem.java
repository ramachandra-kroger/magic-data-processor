package com.kroger.merchandising.magicdatareader.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DataItem {
    private String locationNumber;
    private String upc;
    private String quantitie1;
    private String permanentPrice;
    private String quantitie2;
    private String temporaryPrice;
    private String effectiveDateFrom;
    private String effectiveDateTo;
    private String timingFlag;
    private String durationFlag;
    private String sku;
    private String magicCoupon;
    private String couponUpc;

    public String dataAsTextLine() {
        return locationNumber+"|"+upc+"|"+quantitie1+"|"+permanentPrice+"|"+quantitie2+"|"+temporaryPrice+"|"+effectiveDateFrom+"|"+effectiveDateTo+"|"+timingFlag+"|"+durationFlag+"|"+sku+"|"+magicCoupon+"|"+couponUpc+"\r";
    }
}
