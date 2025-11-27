package com.tomo.core.service.provider;

import com.tomo.core.context.ProjectContext;
import com.tomo.core.pojo.dto.EarningProtocolDTO;
import com.tomo.core.pojo.dto.EarningProtocolRecordDTO;

import java.util.List;

public interface ProjectEarningsProvider<C extends ProjectContext> {
    String getProjectId();
    String getProjectName();
    List<EarningProtocolDTO> getEarningsProtocols();

    List<EarningProtocolRecordDTO> selectEarningProtocolRecords();
}

