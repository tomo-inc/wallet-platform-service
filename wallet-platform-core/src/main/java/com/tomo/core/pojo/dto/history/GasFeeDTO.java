package com.tomo.core.pojo.dto.history;

import lombok.Data;

@Data
public class GasFeeDTO {

    private EvmGas evm;

    @Data
    public static class EvmGas {
        private String gas;

        private String gasPrice;

        private String maxPriorityFeePerGas;

        private String maxFeePerGas;
    }
}
