package com.tomo.core.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EarningProtocolDTO {
    private String code;
    private String name;
    private String displayName;
    private Long chainId;
    private Long chainIndex;
}
