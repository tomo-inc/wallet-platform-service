package com.wlfi.wallet.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tomo.core.enums.ChainEnum;

import com.wlfi.wallet.context.WLFIContext;
import com.wlfi.wallet.dto.ChainExtDTO;
import com.wlfi.wallet.vo.ChainInfoVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import java.io.IOException;
import java.util.*;
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

    private Map<Long, ChainExtDTO> chainExtMap;

    // MEV related constants
    private static final String MEV_BSC_CLUB48_NAME = "Club48";
    private static final String MEV_BSC_CLUB48_SYMBOL = "CLUB48";
    private static final String MEV_BSC_CLUB48_ICON = "https://static.tomo.inc/mev/club48.png";
    private static final String MEV_RANDOM = "RANDOM";

    // Chains that support gas fee
    private static final Set<String> GAS_FEE_SUPPORT_SET = new HashSet<>(Arrays.asList(
        "ARBITRUM", "ETH", "BSC", "BASE", "SOL"
    ));

    // Chains that do not support broadcast
    private static final Set<String> BROADCAST_NOT_SUPPORT_SET = new HashSet<>(Arrays.asList(
        "BTC", "DOGE"
    ));

    @PostConstruct
    public void init() {
        loadChainExtData();
    }

    /**
     * Load chain extension data from JSON file
     */
    private void loadChainExtData() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            ClassPathResource resource = new ClassPathResource("json/chains.json");
            List<ChainExtDTO> chainExtList = mapper.readValue(
                resource.getInputStream(),
                new TypeReference<List<ChainExtDTO>>() {}
            );
            chainExtMap = chainExtList.stream()
                .collect(Collectors.toMap(ChainExtDTO::getChainIndex, Function.identity(), (k1, k2) -> k2));
            log.info("Loaded {} chain configurations from chains.json", chainExtMap.size());
        } catch (IOException e) {
            log.error("Failed to load chains.json", e);
            throw new RuntimeException("Failed to load chain configuration", e);
        }
    }

    /**
     * Query chain information
     */
    public List<ChainInfoVO> queryChainInfo() {
        // Get supported chains from WLFIContext
        Set<ChainEnum> supportedChains = wlfiContext.getSupportedChains();

        // Build chain info list
        List<ChainInfoVO> chainInfoList = supportedChains.stream()
            .filter(chainEnum -> !chainEnum.equals(ChainEnum.UNKNOWN))
            .map(this::buildChainInfo)
            .collect(Collectors.toList());

        // Enrich with extension data from JSON
        enrichChainInfo(chainInfoList);

        return chainInfoList;
    }


    /**
     * Build chain information
     */
    private ChainInfoVO buildChainInfo(ChainEnum chainEnum) {
        ChainInfoVO chainInfo = ChainInfoVO.builder()
            .chainName(chainEnum.getChainName())
            .oldChainName(chainEnum.getOldChainName())
            .chainId(chainEnum.getChainId())
            .chainIndex(chainEnum.getChainIndex())
            .support(GAS_FEE_SUPPORT_SET.contains(chainEnum.name()) || chainEnum.getIsEVM())
            .orderStatusSupport(true)
            .supportBalance(chainEnum.getIsEVM() ||
                          StringUtils.equalsAnyIgnoreCase(chainEnum.name(), "SOL"))
            .supportBroadcast(!BROADCAST_NOT_SUPPORT_SET.contains(chainEnum.name()))
            .type(chainEnum.getType())
            .build();

        // Add MEV info for BSC
        if (StringUtils.equalsAnyIgnoreCase(chainEnum.name(), ChainEnum.BSC.name())) {
            ChainInfoVO.MevInfo mevInfo = ChainInfoVO.MevInfo.builder()
                .mevName(MEV_BSC_CLUB48_NAME)
                .mevSymbol(MEV_BSC_CLUB48_SYMBOL)
                .mevIcon(MEV_BSC_CLUB48_ICON)
                .build();
            chainInfo.setMevInfoList(Collections.singletonList(mevInfo));
        }

        return chainInfo;
    }


    /**
     * Enrich chain info list with extension data
     */
    private void enrichChainInfo(List<ChainInfoVO> chainInfoList) {
        for (ChainInfoVO chainInfo : chainInfoList) {
            ChainExtDTO chainExt = chainExtMap.get(chainInfo.getChainIndex());
            if (chainExt == null) {
                log.warn("Chain extension data not found for chainIndex: {}", chainInfo.getChainIndex());
                continue;
            }

            chainInfo.setChainType(chainExt.getChainType());
            chainInfo.setExplorer(chainExt.getExplorer());
            chainInfo.setIconUrl(chainExt.getIconUrl());

            ChainEnum chainEnum = ChainEnum.getByChainIndex(chainInfo.getChainIndex());
            if (chainEnum != null) {
                chainInfo.setSupportHistory(chainEnum.getLevel() == 1 || chainEnum.getLevel() == 2);
            }

            if (chainExt.getNativeToken() != null) {
                chainInfo.setNativeToken(ChainInfoVO.NativeToken.builder()
                    .name(chainExt.getNativeToken().getName())
                    .symbol(chainExt.getNativeToken().getSymbol())
                    .decimals(chainExt.getNativeToken().getDecimals())
                    .build());
            }
        }
    }


}
