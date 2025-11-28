package com.tomo.core.service.provider;

import com.tomo.core.context.ProjectContext;
import com.tomo.core.pojo.dto.TokenInfoDTO;
import com.tomo.core.service.provider.base.ProjectIdProvider;

public interface ProjectTokenCatalogProvider<C extends ProjectContext> extends ProjectIdProvider {
    TokenInfoDTO getTokenInfo(String chainId, String tokenContractAddress, TokenInfoDTO tokenInfo);
}

