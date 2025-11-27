package com.wlfi.wallet.service;

import com.tomo.core.controller.ChainRegistryCore;
import com.tomo.core.controller.PortfolioCore;
import com.tomo.core.controller.TokenCatalogCore;
import com.tomo.core.enums.ChainEnum;
import com.tomo.core.pojo.dto.AddressDTO;
import com.tomo.core.pojo.dto.BasePortfolioDTO;
import com.tomo.core.pojo.dto.ProjectPortfolioExtension;
import com.tomo.core.service.AbstractProjectService;
import com.tomo.core.service.provider.ProjectAssetProvider;
import com.wlfi.wallet.context.WLFIContext;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

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
public class WLFIProjectAssetService extends AbstractProjectService<WLFIContext> implements ProjectAssetProvider<WLFIContext> {

    private static final Set<ChainEnum> WLFI_SUPPORTED_CHAINS = Set.of(
            ChainEnum.ETH,
            ChainEnum.POLYGON,
            ChainEnum.BSC
    );

    /**
     * Constructor with core service dependencies
     */
    public WLFIProjectAssetService(
            @Lazy ChainRegistryCore chainRegistry,
            @Lazy TokenCatalogCore tokenCatalog,
            @Lazy PortfolioCore portfolioCore) {
        super(chainRegistry, tokenCatalog, portfolioCore);
    }

    @PostConstruct
    public void init() {
        log.info("WLFIProjectAssetService initialized, projectId: {}", getProjectId());
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
     * Template method for portfolio extension
     * Provides common workflow: validation -> extension -> error handling
     */
    @Override
    public ProjectPortfolioExtension extendPortfolio(
            WLFIContext context,
            Long userId,
            List<AddressDTO> addresses,
            BasePortfolioDTO basePortfolioDTO) {

        return doExtendPortfolio(context, userId, addresses, basePortfolioDTO);
    }

    /**
     * Project-specific portfolio extension logic
     * Subclasses can override this method to add their custom asset logic
     *
     * @param context          project context with configuration
     * @param userId           platform user ID
     * @param addresses        user's addresses across chains
     * @param basePortfolioDTO base portfolio from core platform
     * @return project-specific portfolio extension
     */
    protected ProjectPortfolioExtension doExtendPortfolio(
            WLFIContext context,
            Long userId,
            List<AddressDTO> addresses,
            BasePortfolioDTO basePortfolioDTO) {
        // use injected core services
        // Example: query wallet's special tokens for current project
        return ProjectPortfolioExtension.empty();
    }
}
