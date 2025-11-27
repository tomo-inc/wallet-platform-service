package com.wlfi.wallet.service;

import com.tomo.core.controller.ChainRegistryCore;
import com.tomo.core.controller.PortfolioCore;
import com.tomo.core.controller.TokenCatalogCore;
import com.tomo.core.pojo.dto.*;
import com.tomo.core.service.AbstractProjectService;
import com.tomo.core.service.provider.ProjectAssetProvider;
import com.tomo.core.service.provider.ProjectEarningsProvider;
import com.wlfi.wallet.context.WLFIContext;
import jakarta.annotation.PostConstruct;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

/**
 * WLFI Project Service Implementation
 * Extends core portfolio with WLFI-specific features:
 * - Staked tokens
 * - Vesting schedules
 * - Governance power
 * - Reward calculations
 */
@Slf4j
@Service
public class WLFIProjectEarningService implements ProjectEarningsProvider<WLFIContext> {

    @PostConstruct
    public void init() {
        log.info("WLFIProjectAssetService initialized, projectId: {}", getProjectId());
    }

    @Override
    public String getProjectId() {
        return "WLFI";
    }

    @Override
    public String getProjectName() {
        return "WLFI Project";
    }

    @Override
    public List<EarningProtocolDTO> getEarningsProtocols() {

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
