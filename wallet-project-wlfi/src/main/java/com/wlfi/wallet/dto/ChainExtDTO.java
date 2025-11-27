package com.wlfi.wallet.dto;

import lombok.Data;

/**
 * Chain extension information DTO (corresponds to chains.json)
 */
@Data
public class ChainExtDTO {
    private String chainName;
    private Long chainIndex;
    private String chainType;
    private String iconUrl;
    private String explorer;
    private String aiFeedNetwork;
    private NativeTokenDTO nativeToken;

    @Data
    public static class NativeTokenDTO {
        private String symbol;
        private String name;
        private Integer decimals;
    }
}
