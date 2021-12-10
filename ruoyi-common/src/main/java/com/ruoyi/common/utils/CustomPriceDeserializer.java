package com.ruoyi.common.utils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.math.BigDecimal;

/**
 * Created by 商城
 * 将价格json 转化成BigDecimal
 */
public class CustomPriceDeserializer extends JsonDeserializer<BigDecimal> {

    @Override
    public BigDecimal deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {

        BigDecimal d = new BigDecimal(jsonParser.getText().toString());

        return d.setScale(2, BigDecimal.ROUND_DOWN);
    }
}
