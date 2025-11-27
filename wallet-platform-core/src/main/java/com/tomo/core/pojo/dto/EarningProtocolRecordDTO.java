package com.tomo.core.pojo.dto;

import java.math.BigDecimal;
import java.sql.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EarningProtocolRecordDTO {

  /** Primary key ID, auto-increment */
  private Long id;

  /** Chain Index */
  private Long chainIndex;

  /** Protocol identifier */
  private String protocol;

  /** Protocol name */
  private String protocolName;

  /** Protocol icon */
  private String protocolIcon;

  /** Annual percentage yield */
  private BigDecimal apy;

  /** Creation time */
  private Timestamp createdAt;

  /** Update time */
  private Timestamp updatedAt;
}
