package com.tomo.core.service.provider;

import com.tomo.core.context.ProjectContext;
import com.tomo.core.pojo.dto.TokenInfoDTO;

public interface ProjectTokenCatalogProvider<C extends ProjectContext> {
    default String getProjectId() {
        return null;
    }

    TokenInfoDTO getTokenInfo(String chainId, String tokenContractAddress, TokenInfoDTO tokenInfo);
}

