package com.tomo.core.service;

import com.tomo.core.pojo.dto.EarningMaxApyDTO;
import com.tomo.core.pojo.dto.EarningProtocolDTO;
import com.tomo.core.pojo.dto.EarningProtocolRecordDTO;
import com.tomo.core.service.provider.ProjectEarningsProvider;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.tomo.core.util.EarningUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class EarningCoreService {

  private final Map<String, ProjectEarningsProvider<?>> earningProviders;

  public EarningCoreService(List<ProjectEarningsProvider<?>> providers) {
    this.earningProviders =
        providers.stream()
            .collect(Collectors.toMap(ProjectEarningsProvider::getProjectId, provider -> provider));
    log.info("Initialized SwapCore with {} project token providers", earningProviders.size());
  }

  public EarningMaxApyDTO getEarningsMaxApy(String projectId) {

    ProjectEarningsProvider<?> provider = earningProviders.get(projectId);
    List<EarningProtocolDTO> earningsProtocols = provider.getEarningsProtocols();
    try {
      // Get APY data for all chains
      Map<String, String> chainApyMap = getChainApyMap(provider);
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
          earningsProtocols.stream()
              .filter(p -> p.getCode().equals(protocolCode))
              .findFirst()
              .orElse(null);
      if (protocol == null) {
        return EarningMaxApyDTO.builder().maxApy("0.0000").build();
      }

      // Build response
      return EarningMaxApyDTO.builder()
          .maxApy(EarningUtil.formatApy(new BigDecimal(maxEntry.getValue())))
          .protocol(provider.getProjectName())
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
  private Map<String, String> getChainApyMap(ProjectEarningsProvider<?> provider) {
    Map<String, String> apyMap = new HashMap<>();

    try {
      List<EarningProtocolRecordDTO> latestRecords = provider.selectEarningProtocolRecords();
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
}
