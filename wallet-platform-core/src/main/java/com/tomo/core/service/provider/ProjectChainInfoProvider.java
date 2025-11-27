package com.tomo.core.service.provider;

import com.tomo.core.context.ProjectContext;
import com.tomo.core.pojo.dto.ChainInfoDTO;

import java.util.List;

public interface ProjectChainInfoProvider <C extends ProjectContext> {

    default String getProjectId() {
        return null;
    }

    List<ChainInfoDTO> getChainInfo();
}
