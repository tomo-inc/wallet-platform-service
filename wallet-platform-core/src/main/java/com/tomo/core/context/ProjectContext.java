package com.tomo.core.context;

import com.tomo.core.enums.ChainEnum;

import java.util.Map;
import java.util.Set;

/**
 * Project Context Interface
 * Defines project-specific metadata and configuration
 */
public interface ProjectContext {
    
    /**
     * Get unique project identifier
     * 
     * @return project ID (e.g., "WLFI", "MYDOGE")
     */
    String getProjectId();
    
    /**
     * Get supported blockchain chains
     * 
     * @return set of supported chain enums
     */
    Set<ChainEnum> getSupportedChains();
    
    /**
     * Get feature flags for this project
     * 
     * @return map of feature name to enabled status
     */
    Map<String, Boolean> getFeatureFlags();
    
    /**
     * Get project-specific configuration
     * 
     * @return configuration map
     */
    Map<String, Object> getProjectConfig();
}

