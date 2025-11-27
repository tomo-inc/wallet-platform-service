package com.wlfi.wallet.config;

import com.wlfi.wallet.context.WLFIContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * WLFI Configuration
 */
@Configuration
public class WLFIConfig {

    @Bean
    public WLFIContext wlfiContext() {
        return new WLFIContext();
    }
}
