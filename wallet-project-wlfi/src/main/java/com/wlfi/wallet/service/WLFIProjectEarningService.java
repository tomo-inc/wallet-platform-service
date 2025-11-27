package com.wlfi.wallet.service;

import com.tomo.core.pojo.dto.*;
import com.tomo.core.service.provider.ProjectEarningProvider;
import com.tomo.core.util.EarningUtil;
import com.wlfi.wallet.context.WLFIContext;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * WLFI Project Service Implementation
 */
@Slf4j
@Service
public class WLFIProjectEarningService implements ProjectEarningProvider<WLFIContext> {

  @Autowired private WLFIContext context;

  @Override
  public String getProjectId() {
    return context.getProjectId();
  }

  @Override
  public EarningMaxApyDTO getEarningMaxApy() {
    List<EarningProtocolDTO> earningProtocols = getEarningProtocols();
    try {
      // Get APY data for all chains
      Map<String, String> chainApyMap = getChainApyMap();
      if (chainApyMap.isEmpty()) {
        return EarningMaxApyDTO.builder().maxApy("0.0000").build();
      }

      // Find the protocol with maximum APY
      Map.Entry<String, String> maxEntry =
          chainApyMap.entrySet().stream()
              .max(
                  (e1, e2) -> {
                    try {
                      BigDecimal apy1 = new BigDecimal(e1.getValue());
                      BigDecimal apy2 = new BigDecimal(e2.getValue());
                      return apy1.compareTo(apy2);
                    } catch (NumberFormatException ex) {
                      log.warn("Invalid APY format: {} or {}", e1.getValue(), e2.getValue());
                      return 0;
                    }
                  })
              .orElse(null);

      // Get protocol information
      String protocolCode = maxEntry.getKey();
      EarningProtocolDTO protocol =
          earningProtocols.stream()
              .filter(p -> p.getCode().equals(protocolCode))
              .findFirst()
              .orElse(null);
      if (protocol == null) {
        return EarningMaxApyDTO.builder().maxApy("0.0000").build();
      }

      // Build response
      return EarningMaxApyDTO.builder()
          .maxApy(EarningUtil.formatApy(new BigDecimal(maxEntry.getValue())))
          .protocol(context.getProjectId())
          // .chainName(chainEnum != null ? chainEnum.getChainName() : "Unknown")
          .chainIndex(String.valueOf(protocol.getChainIndex()))
          .build();

    } catch (Exception e) {
      log.error("Failed to get maximum APY", e);
      return EarningMaxApyDTO.builder().maxApy("0.0000").build();
    }
  }

  /**
   * Get APY data for all chains, prioritize on-chain data, if not available get the highest APY
   * from the latest database records
   *
   * @return Map<String, String> key is protocol code, value is APY value
   */
  private Map<String, String> getChainApyMap() {
    Map<String, String> apyMap = new HashMap<>();

    try {
      List<EarningProtocolRecordDTO> latestRecords = selectEarningProtocolRecords();
      for (EarningProtocolRecordDTO record : latestRecords) {
        if (record.getApy() != null && record.getProtocol() != null) {
          apyMap.put(record.getProtocol(), record.getApy().toPlainString());
        }
      }
      log.info("Successfully retrieved APY data from database, protocols: {}", apyMap.size());
    } catch (Exception e) {
      log.error("Failed to retrieve APY data from database", e);
    }

    return apyMap;
  }

  @Override
  public List<EarningProtocolDTO> getEarningProtocols() {

        EarningProtocolDTO lista =
                EarningProtocolDTO.builder()
                        .code("lista")
                        .name("Lista DAO")
                        .displayName("Lista Protocol")
                        .chainIndex(5600L)
                        .build();
        EarningProtocolDTO dolomite =
                EarningProtocolDTO.builder()
                        .code("dolomite")
                        .name("Dolomite")
                        .displayName("Dolomite Protocol")
                        .chainIndex(100L)
                        .build();
        EarningProtocolDTO justlend =
                EarningProtocolDTO.builder()
                        .code("justlend")
                        .name("JustLend")
                        .displayName("JustLend Protocol")
                        .chainIndex(1948400L)
                        .build();

        return List.of(lista, dolomite, justlend);
    }

    @Override
    public List<EarningProtocolRecordDTO> selectEarningProtocolRecords() {
        // MOCK DATA
        return List.of(
                EarningProtocolRecordDTO.builder().protocol("lista").apy(new BigDecimal("0.00001")).chainIndex(5600L).build(),
                EarningProtocolRecordDTO.builder().protocol("dolomite").apy(new BigDecimal("0.0010")).chainIndex(100L).build(),
                EarningProtocolRecordDTO.builder().protocol("justlend").apy(new BigDecimal("0.0000")).chainIndex(1948400L).build(),
                EarningProtocolRecordDTO.builder().protocol("lista").apy(new BigDecimal("0.0200")).chainIndex(5600L).build(),
                EarningProtocolRecordDTO.builder().protocol("dolomite").apy(new BigDecimal("0.0000")).chainIndex(100L).build(),
                EarningProtocolRecordDTO.builder().protocol("justlend").apy(new BigDecimal("0.0010")).chainIndex(1948400L).build(),
                EarningProtocolRecordDTO.builder().protocol("lista").apy(new BigDecimal("0.0300")).chainIndex(5600L).build(),
                EarningProtocolRecordDTO.builder().protocol("dolomite").apy(new BigDecimal("0.0020")).chainIndex(100L).build(),
                EarningProtocolRecordDTO.builder().protocol("justlend").apy(new BigDecimal("0.0020")).chainIndex(1948400L).build()
        );

    }
}
