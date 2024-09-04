package com.kroger.merchandising.magicdatareader.entity.compositekey;

import lombok.Data;

import java.io.Serializable;

@Data
public class DataItemCompositeKey implements Serializable {
    private String location;
    private String upc;
}
