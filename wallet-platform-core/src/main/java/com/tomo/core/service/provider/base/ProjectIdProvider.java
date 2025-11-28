package com.tomo.core.service.provider.base;


public interface ProjectIdProvider {
    default String getProjectId() {
        return null;
    }


}

