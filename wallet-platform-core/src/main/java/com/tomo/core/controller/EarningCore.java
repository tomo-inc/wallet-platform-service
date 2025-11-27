package com.tomo.core.controller;

import com.tomo.core.pojo.dto.EarningMaxApyDTO;
import com.tomo.core.service.EarningCoreService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/** Earning Controller */
@Slf4j
@RestController
@RequestMapping("/api/core")
public class EarningCore {

  @Autowired private EarningCoreService earningCoreService;

  @Operation(
      summary = "Get maximum APY across all protocols",
      description =
          "Returns the highest APY available across all protocols, including protocol and chain information")
  @GetMapping("/earning/max-apy")
  public EarningMaxApyDTO getEarningsMaxApy(@RequestHeader(required = false) String projectId) {
    return earningCoreService.getEarningsMaxApy(projectId);
  }
}
