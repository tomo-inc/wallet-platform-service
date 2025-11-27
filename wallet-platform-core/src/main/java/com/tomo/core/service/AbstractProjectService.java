package com.tomo.core.service;

import com.tomo.core.context.ProjectContext;
import com.tomo.core.controller.ChainRegistryCore;
import com.tomo.core.controller.PortfolioCore;
import com.tomo.core.controller.TokenCatalogCore;

import lombok.extern.slf4j.Slf4j;


/**
 * Abstract Project Service Base Class
 * Provides common infrastructure for project-specific business logic
 * <p>
 * This class:
 * 1. Implements ProjectAssetProvider and ProjectTokenProvider interfaces
 * 2. Injects core platform services for project use
 * 3. Provides template method for portfolio extension
 * 4. Allows projects to provide their specific swap tokens
 *
 * @param <C> Project context type that extends ProjectContext
 */
@Slf4j
public abstract class AbstractProjectService<C extends ProjectContext> {

    /**
     * Core platform service dependencies
     * These are injected by constructor and available to all project implementations
     */
    protected final ChainRegistryCore chainRegistry;
    protected final TokenCatalogCore tokenCatalog;
    protected final PortfolioCore portfolioCore;

    /**
     * Project context instance (lazy initialized)
     */
    private C projectContext;

    /**
     * Constructor with core service dependencies
     * Subclasses should call super() to initialize core services
     */
    protected AbstractProjectService(ChainRegistryCore chainRegistry,
                                     TokenCatalogCore tokenCatalog,
                                     PortfolioCore portfolioCore) {
        this.chainRegistry = chainRegistry;
        this.tokenCatalog = tokenCatalog;
        this.portfolioCore = portfolioCore;
    }

    /**
     * Create project context instance
     * Subclasses must implement this to provide their specific context
     *
     * @return project context instance
     */
    protected abstract C createProjectContext();

    /**
     * Get project context (lazy initialization)
     *
     * @return project context
     */
    protected C getProjectContext() {
        if (projectContext == null) {
            projectContext = createProjectContext();
        }
        return projectContext;
    }

    public String getProjectId() {
        return getProjectContext().getProjectId();
    }
}

