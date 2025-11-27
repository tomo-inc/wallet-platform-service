package com.tomo.core.controller;

import com.tomo.core.pojo.dto.ChainInfoDTO;
import com.tomo.core.service.ChainInfoCoreService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/core")
public class ChainInfoCore {

    private final ChainInfoCoreService chainInfoCoreService;


    public ChainInfoCore(ChainInfoCoreService chainInfoCoreService) {
        this.chainInfoCoreService = chainInfoCoreService;
    }


    @GetMapping("/chain/info")
    public List<ChainInfoDTO> getChainInfo(@RequestHeader() String projectId) {
        return chainInfoCoreService.getChainInfo(projectId);
    }
}
