package com.tomo.core.service.provider;

import com.tomo.core.context.ProjectContext;
import com.tomo.core.pojo.dto.EarningProtocolDTO;
import com.tomo.core.pojo.dto.EarningProtocolRecordDTO;

import java.util.List;

public interface ProjectEarningsProvider<C extends ProjectContext> {
  default String getProjectId() {
    return null;
  }

  String getProjectName();

  List<EarningProtocolDTO> getEarningsProtocols();

  List<EarningProtocolRecordDTO> selectEarningProtocolRecords();
}
