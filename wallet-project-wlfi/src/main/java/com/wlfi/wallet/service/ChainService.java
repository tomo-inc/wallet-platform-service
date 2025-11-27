package com.wlfi.wallet.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wlfi.wallet.context.WLFIContext;
import com.wlfi.wallet.dto.ChainExtDTO;
import com.wlfi.wallet.vo.ChainInfoVO;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * WLFI Chain Service
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class ChainService {

    private final WLFIContext wlfiContext;




    /**
     * Query chain information
     */
    public List<ChainInfoVO> queryChainInfo() {
        // Get supported chains from WLFIContext
        Set<String> supportedChains = wlfiContext.supportedChains();


        // Build chain info list
        List<ChainInfoVO> chainInfoList = supportedChains.stream()
            .map(this::buildChainInfo)
            .collect(Collectors.toList());


        return chainInfoList;
    }


    /**
     * Build chain information
     */
    private ChainInfoVO buildChainInfo(String chain) {
        ChainInfoVO chainInfo = ChainInfoVO.builder()
            .chainName("Bitcoin")
            .oldChainName("BITCOIN")
            .chainId(0L)
            .chainIndex(0L)
            .orderStatusSupport(true)
            .type(1)
            .build();

        return chainInfo;
    }





}
