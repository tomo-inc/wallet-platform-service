package com.wlfi.wallet.service;

import com.tomo.core.controller.ChainRegistryCore;
import com.tomo.core.controller.PortfolioCore;
import com.tomo.core.controller.TokenCatalogCore;
import com.tomo.core.enums.ChainEnum;
import com.tomo.core.pojo.dto.SwapTokenDTO;
import com.tomo.core.service.AbstractProjectService;
import com.wlfi.wallet.context.WLFIContext;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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
public class WLFIProjectService extends AbstractProjectService<WLFIContext> {

    /**
     * Constructor with core service dependencies
     */
    public WLFIProjectService(ChainRegistryCore chainRegistry,
                              TokenCatalogCore tokenCatalog,
                              PortfolioCore portfolioCore) {
        super(chainRegistry, tokenCatalog, portfolioCore);
    }

    @PostConstruct
    public void init() {
        log.info("WLFI Project Service initialized, projectId: {}", getProjectId());
    }

    /**
     * Create WLFI project context
     * No need to override getProjectId() - it's automatically retrieved from context
     */
    @Override
    protected WLFIContext createProjectContext() {
        return new WLFIContext();
    }

    /**
     * Provide WLFI-specific swap tokens
     */
    @Override
    public List<SwapTokenDTO> getProjectTokens(String chainId) {
        log.debug("Getting WLFI project tokens for chainId: {}", chainId);

        List<SwapTokenDTO> wlfiTokens = new ArrayList<>();

        // WLFI token on Ethereum
        if (chainId == null || "100".equals(chainId)) {
            SwapTokenDTO wlfi = new SwapTokenDTO("WLFI", "WLFI Token", "100");
            wlfi.setAddress("0x1234567890abcdef1234567890abcdef12345678");
            wlfi.setDecimals(18);
            wlfi.setPriceUsd(new BigDecimal("2.50"));
            wlfi.setIsProjectToken(true);
            wlfi.setProjectId(getProjectId());
            wlfi.setLogoUrl("https://example.com/logos/wlfi.png");
            wlfiTokens.add(wlfi);
        }

        // WLFI token on Polygon
        if (chainId == null || "137".equals(chainId)) {
            SwapTokenDTO wlfiPoly = new SwapTokenDTO("WLFI", "WLFI Token", "137");
            wlfiPoly.setAddress("0xabcdef1234567890abcdef1234567890abcdef12");
            wlfiPoly.setDecimals(18);
            wlfiPoly.setPriceUsd(new BigDecimal("2.50"));
            wlfiPoly.setIsProjectToken(true);
            wlfiPoly.setProjectId(getProjectId());
            wlfiPoly.setLogoUrl("https://example.com/logos/wlfi.png");
            wlfiTokens.add(wlfiPoly);
        }

        log.info("Returning {} WLFI tokens", wlfiTokens.size());
        return wlfiTokens;
    }
}
