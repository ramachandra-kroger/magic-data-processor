package com.kroger.merchandising.magicdatareader.domain;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DataItem implements Serializable {
    @NotBlank
    @Size(min = 5, max = 5)
    private String locationNumber;
    @NotBlank
    @Size(min = 13, max = 13)
    private String upc;
    private String quantitie1;
    @Size(max = 7)
    private String permanentPrice;
    private String quantitie2;
    @Size(max = 7)
    private String temporaryPrice;
    private String effectiveDateFrom;
    private String effectiveDateTo;
    private String timingFlag;
    private String durationFlag;
    @Size(max = 8)
    @NotBlank
    private String sku;
    private String magicCoupon;
    private String couponUpc;

    public String dataAsTextLine() {
        return locationNumber+"|"+upc+"|"+quantitie1+"|"+permanentPrice+"|"+quantitie2+"|"+temporaryPrice+"|"+effectiveDateFrom+"|"+effectiveDateTo+"|"+timingFlag+"|"+durationFlag+"|"+sku+"|"+magicCoupon+"|"+couponUpc+"\r";
    }
}
