package com.tomo.core.service.provider;

import com.tomo.core.context.ProjectContext;
import com.tomo.core.pojo.dto.SwapTokenDTO;
import com.tomo.core.pojo.dto.TokenInfoDTO;

import java.util.List;

public interface ProjectTokenCatalogProvider<C extends ProjectContext> {
    String getProjectId();

    TokenInfoDTO getTokenInfo(String chainId, String tokenContractAddress, TokenInfoDTO tokenInfo);
}

