package com.wlfi.wallet.service;

import com.tomo.core.pojo.dto.*;
import com.tomo.core.service.provider.ProjectTokenCatalogProvider;
import com.wlfi.wallet.context.WLFIContext;
import com.wlfi.wallet.service.base.WLFIProjectBaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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
public class WLFIProjectTokenCatalogService extends WLFIProjectBaseService<WLFIContext> implements ProjectTokenCatalogProvider<WLFIContext> {

    public WLFIProjectTokenCatalogService() {
        WLFIContext context = this.getProjectContext();
        log.info("init WLFIProjectTokenCatalogService, projectId: {}", context.getProjectId());

    }

    @Override
    public TokenInfoDTO getTokenInfo(String chainId, String tokenContractAddress, TokenInfoDTO tokenInfo) {
        if (chainId.equalsIgnoreCase("1") && tokenContractAddress.equalsIgnoreCase("0xdAC17F958D2ee523a2206206994597C13D831ec7")) {
            tokenInfo.setTokenPrice("1.00");
            Map<String, Object> extraInfo = new HashMap<>();
            extraInfo.put("safeTag", "safe");
            extraInfo.put("coinType", "stable-coin");
            tokenInfo.setExtInfo(extraInfo);
            tokenInfo.setPriceChangeH24("0.0001");
            tokenInfo.setVolumeH24("123456789");
            return tokenInfo;
        }

        tokenInfo.setTokenPrice("3500.01");
        Map<String, Object> extraInfo = new HashMap<>();
        extraInfo.put("safeTag", "safe");
        tokenInfo.setExtInfo(extraInfo);
        return tokenInfo;
    }


}
