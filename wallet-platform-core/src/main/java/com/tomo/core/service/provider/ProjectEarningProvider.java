package com.tomo.core.service.provider;

import com.tomo.core.context.ProjectContext;
import com.tomo.core.pojo.dto.EarningMaxApyDTO;
import com.tomo.core.pojo.dto.EarningProtocolDTO;
import com.tomo.core.pojo.dto.EarningProtocolRecordDTO;

import java.util.List;

public interface ProjectEarningProvider<C extends ProjectContext> {
  default String getProjectId() {
    return null;
  }

  EarningMaxApyDTO getEarningMaxApy();

  List<EarningProtocolDTO> getEarningProtocols();

  List<EarningProtocolRecordDTO> selectEarningProtocolRecords();
}
