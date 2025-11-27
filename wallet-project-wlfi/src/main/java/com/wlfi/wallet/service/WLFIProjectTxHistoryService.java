package com.wlfi.wallet.service;

import com.google.common.collect.Lists;
import com.tomo.core.pojo.dto.history.TxHistoryDTO;
import com.tomo.core.service.TxHistoryCoreService;
import com.tomo.core.service.provider.ProjectTxHistoryProvider;
import com.wlfi.wallet.context.WLFIContext;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class WLFIProjectTxHistoryService  implements ProjectTxHistoryProvider<WLFIContext> {


    private final WLFIContext context;
    private final TxHistoryCoreService txHistoryCoreService;

    public WLFIProjectTxHistoryService(WLFIContext context, TxHistoryCoreService txHistoryCoreService) {
        this.context = context;
        this.txHistoryCoreService = txHistoryCoreService;
        log.info("init, projectId: {}", context.getProjectId());
    }

    @PostConstruct
    public void register() {
        txHistoryCoreService.register(this);
    }

    @Override
    public String getProjectId() {
        return context.getProjectId();
    }

    @Override
    public List<String> getSupportedChainIds() {
        return Lists.newArrayList(context.supportedChains());
    }

    @Override
    public List<Integer> getSupportedTxTypes() {
        return List.of(1,2,3);
    }

    /**
     * 1. Add the project-related information in txHistoryDTOList, such as user details.
     * 2. Adapt to the project's special display requirements, such as showing contract interactions as transfers or receipts.
     * 3. Handle pagination and sorting if needed.
     *
     */
    @Override
    public List<TxHistoryDTO> getProjectTxHistoryList(List<TxHistoryDTO> txHistoryDTOList) {

        return txHistoryDTOList;
    }

    /**
     * Get project-specific transaction history detail
     * 1. Add the project-related information in txHistoryDTO, such as user details.
     * 2. Adapt to the project's special display requirements for detail view.
     */
    @Override
    public TxHistoryDTO getProjectTxHistoryDetail(TxHistoryDTO txHistoryDTO) {

        return txHistoryDTO;
    }

}
