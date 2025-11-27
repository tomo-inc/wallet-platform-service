package com.tomo.core.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import lombok.extern.slf4j.Slf4j;

/** earning utility class */
@Slf4j
public class EarningUtil {

  /** format the apy */
  public static String formatApy(BigDecimal apy) {
    if (apy == null) {
      return "0.0000";
    }
    return apy.setScale(4, RoundingMode.HALF_UP).toString();
  }
}
