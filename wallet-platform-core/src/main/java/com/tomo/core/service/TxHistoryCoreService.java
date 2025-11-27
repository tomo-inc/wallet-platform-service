package com.tomo.core.service;

import com.tomo.core.pojo.dto.history.TxHistoryDTO;
import com.tomo.core.pojo.dto.history.TxHistoryTokenDTO;
import com.tomo.core.pojo.dto.history.TxHistoryTxFeeDTO;
import com.tomo.core.service.provider.ProjectTxHistoryProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
public class TxHistoryCoreService {


    private final Map<String, ProjectTxHistoryProvider<?>> projectTxHistoryProviders;

    public TxHistoryCoreService(List<ProjectTxHistoryProvider<?>> providers) {
        this.projectTxHistoryProviders = providers.stream().collect(Collectors.toMap(ProjectTxHistoryProvider::getProjectId, provider -> provider));
        log.info("Initialized TxHistoryCore with {} project txHistory providers", projectTxHistoryProviders.size());
    }

    public List<TxHistoryDTO> txHistoryList(String projectId, String walletId, String chainId, String tokenAddress, Integer txType, Long startTime, Long endTime, Integer pageNum, Integer pageSize,
                                            Boolean filterRisk) {

        ProjectTxHistoryProvider<?> provider = projectTxHistoryProviders.get(projectId);
        List<String> supportedChainIds = provider.getSupportedChainIds();
        List<Integer> supportedTxTypes = provider.getSupportedTxTypes();

        checkParam(supportedChainIds, supportedTxTypes, chainId, txType);
        List<TxHistoryDTO> coreHistories = getCoreHistories(walletId, chainId, tokenAddress, txType, startTime, endTime, pageNum, pageSize, filterRisk);

        return provider.getProjectTxHistoryList(coreHistories);

    }

    public TxHistoryDTO detail(String projectId, String walletId, String chainId, Long orderId, String txHash) {

        ProjectTxHistoryProvider<?> provider = projectTxHistoryProviders.get(projectId);
        List<String> supportedChainIds = provider.getSupportedChainIds();

        checkParam(supportedChainIds, null, chainId, null);
        TxHistoryDTO coreHistory = getCoreHistory(walletId, chainId, orderId, txHash);

        return provider.getProjectTxHistoryDetail(coreHistory);

    }

    /**
     * Check if chainId and txType are supported
     */
    private void checkParam(List<String> supportedChainIds, List<Integer> supportedTxTypes, String chainId, Integer txType) {
        return;
    }

    /**
     * query tx history list from db or cache
     */
    private List<TxHistoryDTO> getCoreHistories(String chainId, String id, String tokenAddress, Integer txType, Long startTime, Long endTime, Integer pageNum, Integer pageSize, Boolean filterRisk) {

        List<TxHistoryDTO> list = new ArrayList<>();
        TxHistoryDTO tx1 = new TxHistoryDTO();
        tx1.setOrderId(1L);
        tx1.setChainIndex(1001L);
        tx1.setTxHash("0xmocktxhash1");
        tx1.setTxType(1);
        tx1.setTxTime(System.currentTimeMillis());
        tx1.setFrom(Arrays.asList(TxHistoryDTO.AddressAmount.builder().chainIndex(1001L).address("0xfromaddress1").amount("1.0").build()));
        tx1.setTo(Arrays.asList(TxHistoryDTO.AddressAmount.builder().chainIndex(1001L).address("0xtoaddress1").amount("1.0").build()));
        tx1.setTokenInfo(new TxHistoryTokenDTO("0xtokenaddr1", "MOCK1", "https://example.com/logo/mock1.png", 0));
        tx1.setAmount("1.0");
        tx1.setUsdAmount("1.23");
        tx1.setTxStatus(1);
        tx1.setTxFee(TxHistoryTxFeeDTO.builder().symbol("ETH").amount("0.00021").usdAmount("0.26").build());
        tx1.setNote("Mock transaction 1");

        TxHistoryDTO tx2 = new TxHistoryDTO();
        tx2.setOrderId(2L);
        tx2.setChainIndex(1002L);
        tx2.setTxHash("0xmocktxhash2");
        tx2.setTxType(2);
        tx2.setTxTime(System.currentTimeMillis() - 60_000);
        tx2.setFrom(Arrays.asList(TxHistoryDTO.AddressAmount.builder().chainIndex(1002L).address("0xfromaddress2").amount("2.5").build()));
        tx2.setTo(Arrays.asList(TxHistoryDTO.AddressAmount.builder().chainIndex(1002L).address("0xtoaddress2").amount("2.5").build()));
        tx2.setTokenInfo(new TxHistoryTokenDTO("0xtokenaddr2", "MOCK2", "https://example.com/logo/mock2.png", 1));
        tx2.setAmount("2.5");
        tx2.setUsdAmount("3.07");
        tx2.setTxStatus(1);
        tx2.setTxFee(TxHistoryTxFeeDTO.builder().symbol("ETH").amount("0.00042").usdAmount("0.52").build());
        tx2.setNote("Mock transaction 2");

        list.add(tx1);
        list.add(tx2);
        return list;
    }

    /**
     * query tx history detail from db or cache
     */
    private TxHistoryDTO getCoreHistory(String walletId, String chainId, Long orderId, String txHash) {

        // Mock implementation - should query from db or cache
        TxHistoryDTO tx = new TxHistoryDTO();
        tx.setOrderId(orderId != null ? orderId : 1L);
        tx.setChainIndex(chainId != null ? Long.parseLong(chainId) : 1001L);
        tx.setTxHash(txHash != null ? txHash : "0xmocktxhash1");
        tx.setTxType(1);
        tx.setTxTime(System.currentTimeMillis());
        tx.setFrom(Arrays.asList(TxHistoryDTO.AddressAmount.builder().chainIndex(chainId != null ? Long.parseLong(chainId) : 1001L).address("0xfromaddress1").amount("1.0").build()));
        tx.setTo(Arrays.asList(TxHistoryDTO.AddressAmount.builder().chainIndex(chainId != null ? Long.parseLong(chainId) : 1001L).address("0xtoaddress1").amount("1.0").build()));
        tx.setTokenInfo(new TxHistoryTokenDTO("0xtokenaddr1", "MOCK1", "https://example.com/logo/mock1.png", 0));
        tx.setAmount("1.0");
        tx.setUsdAmount("1.23");
        tx.setTxStatus(1);
        tx.setTxFee(TxHistoryTxFeeDTO.builder().symbol("ETH").amount("0.00021").usdAmount("0.26").build());
        tx.setNote("Mock transaction detail");

        return tx;
    }

}
