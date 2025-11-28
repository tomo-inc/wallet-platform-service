package com.wlfi.wallet.service.base;

import com.tomo.core.service.AbstractProjectService;
import com.wlfi.wallet.context.WLFIContext;

import java.util.Set;

public class WLFIProjectBaseService<W> extends AbstractProjectService<WLFIContext> {
    @Override
    protected WLFIContext createProjectContext() {
        Set<String> supportedChains = Set.of("ethereum", "polygon", "bsc");
        return new WLFIContext(supportedChains);
    }
}
