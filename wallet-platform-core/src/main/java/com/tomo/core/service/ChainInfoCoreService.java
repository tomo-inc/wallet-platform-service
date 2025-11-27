package com.tomo.core.service;

import com.tomo.core.pojo.dto.ChainInfoDTO;
import com.tomo.core.service.provider.ProjectChainInfoProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ChainInfoCoreService {


    private final Map<String, ProjectChainInfoProvider<?>> projectChainInfoProvider;

    public ChainInfoCoreService(List<ProjectChainInfoProvider<?>> providers) {
        this.projectChainInfoProvider = providers.stream()
                .collect(Collectors.toMap(
                        ProjectChainInfoProvider::getProjectId,
                        provider -> provider
                ));

    }

    public List<ChainInfoDTO> getChainInfo(String projectId) {

        List<ChainInfoDTO> chainInfoList = new ArrayList<>();

        if (projectId != null && projectChainInfoProvider.containsKey(projectId)) {
            ProjectChainInfoProvider<?> provider = projectChainInfoProvider.get(projectId);
            List<ChainInfoDTO> chainInfoDTOList = provider.getChainInfo();
            log.info("extend token info with project {}", projectId);
            return chainInfoDTOList;
        }
        return chainInfoList;
    }


}
