package com.wlfi.wallet.controller;

import com.tomo.core.pojo.dto.SwapTokenDTO;
import com.wlfi.wallet.service.WLFIProjectTokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * WLFI Project Controller
 * Exposes WLFI-specific endpoints under /api/project/wlfi/
 */
@Slf4j
@RestController
@RequestMapping("/api/project/wlfi")
@RequiredArgsConstructor
public class WLFIProjectController {

    private final WLFIProjectTokenService wlfiProjectService;

    /**
     * Get WLFI project information
     */
    @GetMapping("/info")
    public Map<String, Object> getProjectInfo() {
        log.info("Getting WLFI project info");

        Map<String, Object> info = new HashMap<>();
        info.put("projectId", wlfiProjectService.getProjectId());
        info.put("supportedChains", Set.of("ethereum", "polygon", "bsc"));

        return info;
    }

    /**
     * Get WLFI-specific swap tokens
     */
    @GetMapping("/swap/tokens")
    public List<SwapTokenDTO> getSwapTokens(@RequestParam(required = false) String chainId) {
        log.info("Getting WLFI swap tokens for chainId: {}", chainId);
        return wlfiProjectService.getProjectTokens(chainId);
    }
}
