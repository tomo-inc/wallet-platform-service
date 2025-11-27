package com.wlfi.wallet.context;

import com.tomo.core.context.ProjectContext;
import com.tomo.core.enums.ChainEnum;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

/**
 * WLFI Project Context Implementation
 * Defines WLFI project configuration and supported chains
 */
public class WLFIContext implements ProjectContext {


    private static final Set<ChainEnum> WLFI_SUPPORTED_CHAINS = Set.of(
        ChainEnum.BTC,
        ChainEnum.ETH,
        ChainEnum.BSC,
        ChainEnum.SOL,
        ChainEnum.BASE,
        ChainEnum.ARBITRUM,
        ChainEnum.OPTIMISM,
        ChainEnum.POLYGON,
        ChainEnum.DOGE
    );

    @Override
    public String getProjectId() {
        return "WLFI";
    }

    @Override
    public Set<ChainEnum> getSupportedChains() {
        return WLFI_SUPPORTED_CHAINS;
    }

    @Override
    public Map<String, Boolean> getFeatureFlags() {
        return Collections.emptyMap();
    }

    @Override
    public Map<String, Object> getProjectConfig() {
        return Collections.emptyMap();
    }
}
