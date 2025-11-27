package com.wlfi.wallet.common.config;

import com.wlfi.wallet.context.WLFIContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Set;

@Configuration
public class WLFIProjectConfiguration {

    @Bean
    public WLFIContext wlfiContext() {
        Set<String> supportedChains = Set.of("ethereum", "polygon", "bsc");
        return new WLFIContext(supportedChains);
    }
}
