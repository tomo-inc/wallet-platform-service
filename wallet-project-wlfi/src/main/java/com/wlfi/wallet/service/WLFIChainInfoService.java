package com.wlfi.wallet.service;

import com.tomo.core.pojo.dto.ChainInfoDTO;
import com.tomo.core.service.provider.ProjectChainInfoProvider;
import com.wlfi.wallet.context.WLFIContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * WLFI Chain Service
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class WLFIChainInfoService implements ProjectChainInfoProvider<WLFIContext> {

    private final WLFIContext wlfiContext;


    @Override
    public List<ChainInfoDTO> getChainInfo() {

        return buildChainInfo();
    }

    private List<ChainInfoDTO> buildChainInfo() {
        List<ChainInfoDTO> chainInfoList = new ArrayList<>();
        ChainInfoDTO chainInfo = ChainInfoDTO.builder()
                .chainName("Bitcoin")
                .oldChainName("BITCOIN")
                .chainId(0L)
                .orderStatusSupport(true)
                .type(1)
                .build();
        chainInfoList.add(chainInfo);
        return chainInfoList;
    }
}
