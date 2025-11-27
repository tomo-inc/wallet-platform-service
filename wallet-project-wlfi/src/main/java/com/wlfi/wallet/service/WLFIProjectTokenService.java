package com.wlfi.wallet.service;

import com.tomo.core.pojo.dto.SwapTokenDTO;
import com.tomo.core.service.provider.ProjectTokenProvider;
import com.wlfi.wallet.context.WLFIContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

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
public class WLFIProjectTokenService implements ProjectTokenProvider<WLFIContext> {

    private final WLFIContext context;

    public WLFIProjectTokenService(WLFIContext context) {
        this.context = context;
        log.info("init, projectId: {}", context.getProjectId());
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
