package com.tomo.core.service.provider;

import com.tomo.core.context.ProjectContext;
import com.tomo.core.pojo.dto.history.TxHistoryDTO;

import java.util.List;

public interface ProjectTxHistoryProvider<C extends ProjectContext> {

    List<String> getSupportedChainIds();

    List<Integer> getSupportedTxTypes();

    List<TxHistoryDTO> getProjectTxHistoryList(List<TxHistoryDTO> chainId);

    TxHistoryDTO getProjectTxHistoryDetail(TxHistoryDTO txHistoryDTO);

    default String getProjectId() {
        return null;
    }
}

