package com.tomo.core.pojo.dto.history;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TxHistoryTokenDTO implements Serializable {
    private String tokenAddress;
    private String symbol;
    private String logo;
    private Integer riskLevel;
}
