package com.tomo.core.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TokenInfoDTO {
    private Long chainId;

    private String address;

    private Boolean isNative;

    private String name;

    private String displayName;

    private String symbol;

    private String logo;

    private Integer decimals;

    private String tokenPrice;

    private String priceChangeH24;

    private String volumeH24;

    private Map<String, Object> extInfo;
}
