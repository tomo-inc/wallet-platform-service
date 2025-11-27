package com.tomo.core.pojo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Earning Maximum APY")
public class EarningMaxApyDTO {

  @Schema(description = "Maximum APY value", example = "3.5000")
  private String maxApy;

  @Schema(description = "Protocol with maximum APY", example = "lista")
  private String protocol;

  @Schema(description = "Chain name of the protocol", example = "BSC")
  private String chainName;

  @Schema(description = "Chain index", example = "5600")
  private String chainIndex;

  @Schema(description = "Protocol icon URL")
  private String protocolIcon;

  @Schema(description = "Protocol logo URL for night mode")
  private String protocolLogoForNightMode;

  @Schema(description = "Chain icon URL")
  private String chainIcon;
}
