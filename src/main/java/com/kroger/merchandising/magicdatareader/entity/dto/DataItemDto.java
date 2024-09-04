package com.kroger.merchandising.magicdatareader.entity.dto;

import lombok.Data;

@Data
public class DataItemDto {
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
}
