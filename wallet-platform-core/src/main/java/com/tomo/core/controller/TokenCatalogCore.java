package com.tomo.core.controller;

import com.tomo.core.pojo.dto.TokenInfoDTO;
import com.tomo.core.service.TokenCatalogCoreService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Token Catalog Core
 * Manages token metadata catalog
 */
@Slf4j
@RestController
@RequestMapping("/api/core/token")
public class TokenCatalogCore {


    private final TokenCatalogCoreService tokenCatalogCoreService;

    @Autowired
    public TokenCatalogCore(TokenCatalogCoreService tokenCatalogCoreService) {
        this.tokenCatalogCoreService = tokenCatalogCoreService;
    }

    /**
     * Get specified token info
     * Aggregates core platform token and project-specific token
     *
     * @param chainId              optional chain filter
     * @param tokenContractAddress token Contract Address
     * @return token info
     * @header projectId optional project ID to include project tokens
     */
    @GetMapping("/token-info")
    public TokenInfoDTO getTokenInfo(@RequestHeader() String projectId, @RequestParam() String chainId, @RequestParam() String tokenContractAddress) {
        log.info("Getting token info for projectId: {}, chainId: {}, tokenContractAddress:{}", projectId, chainId, tokenContractAddress);
        return tokenCatalogCoreService.getTokenInfo(projectId, chainId, tokenContractAddress);
    }
}
