package com.tomo.core.controller;

import com.tomo.core.pojo.dto.history.TxHistoryDTO;
import com.tomo.core.service.TxHistoryCoreService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/core/")
public class TxHistoryCoreController {

    private final TxHistoryCoreService txHistoryCoreService;

    public TxHistoryCoreController(TxHistoryCoreService txHistoryCoreService) {
        this.txHistoryCoreService = txHistoryCoreService;
    }

    @GetMapping("tx/history/list")
    public List<TxHistoryDTO> txHistoryList(
            @RequestHeader(required = true) String projectId,
            @RequestParam(required = true)  String walletId,
            @RequestParam(required = false) String chainId,
            @RequestParam(required = false) String tokenAddress,
            @RequestParam(required = false) Integer txType,
            @RequestParam(required = false) Long startTime,
            @RequestParam(required = false) Long endTime,
            @RequestParam(required = false) Integer pageNum,
            @RequestParam(required = false) Integer pageSize,
            @RequestParam(required = false) Boolean filterRisk
            ) {
        return txHistoryCoreService.txHistoryList(projectId, walletId, chainId, tokenAddress, txType, startTime, endTime, pageNum, pageSize, filterRisk);
    }

    @GetMapping("tx/history/detail")
    public TxHistoryDTO detail(
            @RequestHeader(required = true) String projectId,
            @RequestParam(required = false) String walletId,
            @RequestParam(required = false) String chainId,
            @RequestParam(required = false) Long orderId,
            @RequestParam(required = false) String txHash
            ) {
        return txHistoryCoreService.detail(projectId, walletId, chainId, orderId, txHash);
    }

}
