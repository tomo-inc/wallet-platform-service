package com.tomo.core.service;

import com.tomo.core.pojo.dto.EarningMaxApyDTO;
import com.tomo.core.service.provider.ProjectEarningProvider;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class EarningCoreService {

  private final Map<String, ProjectEarningProvider<?>> earningProviders;

  public EarningCoreService(List<ProjectEarningProvider<?>> providers) {
    this.earningProviders =
        providers.stream()
            .collect(Collectors.toMap(ProjectEarningProvider::getProjectId, provider -> provider));
    log.info("Initialized SwapCore with {} project token providers", earningProviders.size());
  }

  public EarningMaxApyDTO getEarningMaxApy(String projectId) {
    ProjectEarningProvider<?> provider = earningProviders.get(projectId);
    return provider.getEarningMaxApy();
  }

}
