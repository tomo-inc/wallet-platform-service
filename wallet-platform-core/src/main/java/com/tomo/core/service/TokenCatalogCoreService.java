package com.tomo.core.service;

import com.tomo.core.pojo.dto.TokenInfoDTO;
import com.tomo.core.service.provider.ProjectTokenCatalogProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
public class TokenCatalogCoreService {

    private final Map<String, ProjectTokenCatalogProvider<?>> projectTokenCatalogProvider;

    public TokenCatalogCoreService(List<ProjectTokenCatalogProvider<?>> providers) {
        this.projectTokenCatalogProvider = providers.stream()
                .collect(Collectors.toMap(
                        ProjectTokenCatalogProvider::getProjectId,
                        provider -> provider
                ));
        log.info("Initialized TokenCatalogCoreService with {} project token catalog providers",
                projectTokenCatalogProvider.size());
    }

    public TokenInfoDTO getTokenInfo(String projectId, String chainId, String tokenContractAddress) {

        TokenInfoDTO tokenInfo = getTokenInfoForCore(chainId, tokenContractAddress);


        // 2. Add project-specific tokens if projectId provided
        if (projectId != null && projectTokenCatalogProvider.containsKey(projectId)) {
            ProjectTokenCatalogProvider<?> provider = projectTokenCatalogProvider.get(projectId);
            TokenInfoDTO fullTokenInfo = provider.getTokenInfo(chainId, tokenContractAddress, tokenInfo);
            log.info("extend token info with project {}", projectId);
            return fullTokenInfo;
        }
        return tokenInfo;
    }

    private TokenInfoDTO getTokenInfoForCore(String chainId, String tokenContractAddress) {

        if (chainId.equalsIgnoreCase("1") && tokenContractAddress.equalsIgnoreCase("0xdAC17F958D2ee523a2206206994597C13D831ec7")) {
            TokenInfoDTO tokenInfo = new TokenInfoDTO();
            tokenInfo.setName("Tether USD");
            tokenInfo.setDisplayName("Tether USD");
            tokenInfo.setLogo("usdt.png");
            tokenInfo.setSymbol("USDT");
            tokenInfo.setDecimals(6);
            tokenInfo.setIsNative(false);
            tokenInfo.setChainId(Long.parseLong(chainId));
            tokenInfo.setAddress(tokenContractAddress);
            return tokenInfo;
        }

        TokenInfoDTO tokenInfo = new TokenInfoDTO();
        tokenInfo.setName("ETH");
        tokenInfo.setSymbol("ETH");
        tokenInfo.setDecimals(18);
        tokenInfo.setIsNative(true);
        tokenInfo.setChainId(Long.parseLong(chainId));
        tokenInfo.setAddress("");
        return tokenInfo;
    }
}
