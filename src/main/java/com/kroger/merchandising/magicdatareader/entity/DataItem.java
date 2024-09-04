package com.kroger.merchandising.magicdatareader.entity;

import com.kroger.merchandising.magicdatareader.entity.compositekey.DataItemCompositeKey;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Entity
@Data
@Builder
@AllArgsConstructor
@Table(name = "magic_data_item")
@IdClass(DataItemCompositeKey.class)
public class DataItem implements Serializable {
    @Id
    @NotBlank
    @Size(max = 5)
    @Column(length = 5, name = "location_no", nullable = false)
    private String location;
    @Id
    @NotBlank
    @Column(length = 5, name = "upc")
    @Size(max = 13)
    private String upc;
    @Size(max = 3)
    @Column(length = 3, name = "quantitie1")
    private String quantitie1;
    @Size(max = 7)
    @Column(name = "permanent_price")
    private String permanentPrice;
    @Size(max = 3)
    @Column(name = "quantitie2")
    private String quantitie2;
    @Size(max = 7)
    @Column(name = "temporary_price")
    private String temporaryPrice;
    @Size(max = 10)
    @Column(name = "effective_date_from")
    private String effectiveDateFrom;
    @Size(max = 10)
    @Column(name = "effective_date_to")
    private String effectiveDateTo;
    @Size(max = 1)
    @Column(name = "timing_flag")
    private String timingFlag;
    @Size(max = 1)
    @Column(name = "duration_flag")
    private String durationFlag;
    @Size(max = 10)
    @Column(name = "sku")
    private String sku;
    @Size(max = 10)
    @Column(name = "magic_coupon")
    private String magicCoupon;
    @Size(max = 13)
    @Column(name = "coupon_upc")
    private String couponUpc;

    public DataItem() {

    }

    public String dataAsTextLine() {
        return location+"|"+upc+"|"+quantitie1+"|"+permanentPrice+"|"+quantitie2+"|"+temporaryPrice+"|"+effectiveDateFrom+"|"+effectiveDateTo+"|"+timingFlag+"|"+durationFlag+"|"+sku+"|"+magicCoupon+"|"+couponUpc+"\r";
    }
}
