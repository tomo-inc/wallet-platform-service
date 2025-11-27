package com.wlfi.wallet.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Chain information response object
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChainInfoVO {
    private String chainName;
    private String oldChainName;
    private Long chainId;
    private Long chainIndex;
    private Boolean support;
    private Boolean orderStatusSupport;
    private Boolean supportBalance;
    private Boolean supportBroadcast;
    private Boolean supportHistory;
    private Integer type;
    private String chainType;
    private String explorer;
    private String iconUrl;
    private NativeToken nativeToken;
    private List<MevInfo> mevInfoList;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class NativeToken {
        private String name;
        private String symbol;
        private Integer decimals;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MevInfo {
        private String mevName;
        private String mevSymbol;
        private String mevIcon;
    }
}
