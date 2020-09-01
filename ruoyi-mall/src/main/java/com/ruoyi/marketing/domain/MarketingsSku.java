package com.ruoyi.marketing.domain;

import com.ruoyi.goods.domain.PmsSku;
import lombok.Data;

@Data
public class MarketingsSku  extends PmsSku {
    
    private String marketingPrice;
    private String type;
    private String marketingType;
    private String marketingName;

}
