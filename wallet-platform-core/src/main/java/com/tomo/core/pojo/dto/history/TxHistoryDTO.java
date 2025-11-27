package com.tomo.core.pojo.dto.history;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
public class TxHistoryDTO implements Serializable {
    private Long orderId;
    private Long chainIndex;
    private String txHash;
    private Integer txType;
    private Long txTime;
    private List<AddressAmount> from;
    private List<AddressAmount> to;
    private TxHistoryTokenDTO tokenInfo;
    private String amount;
    private String usdAmount;
    private Integer txStatus;
    private TxHistoryTxFeeDTO txFee;
    private String note;
    private Boolean gasLess;

    private Long nonce;

    private Long refundChainIndex;
    private String refundTxHash;
    private Long originalChainIndex;
    private String originalTxHash;

    private List<CancelingTxInfo> cancelingTxList;
    private GasFeeDTO gasFee;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class AddressAmount implements Serializable {
        private Long chainIndex;
        private String txHash;
        private String address;
        private TxHistoryTokenDTO tokenInfo;
        private Long userId;
        private String userName;
        private String nickname;
        private String avatar;
        private String amount;
        private String usdAmount;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class CancelingTxInfo implements Serializable {
        private String txHash;
        private Integer txStatus;
        private Long txTime;
        private TxHistoryTxFeeDTO txFee;
        private GasFeeDTO gasFee;
    }
}
