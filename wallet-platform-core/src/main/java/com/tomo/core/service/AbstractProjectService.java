package com.tomo.core.service;

import com.tomo.core.context.ProjectContext;

import com.tomo.core.service.provider.base.ProjectIdProvider;
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
public abstract class AbstractProjectService<C extends ProjectContext> implements ProjectIdProvider {

    /**
     * Project context instance (lazy initialized)
     */
    private C projectContext;

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

