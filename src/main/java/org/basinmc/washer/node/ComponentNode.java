package org.basinmc.washer.node;

import org.basinmc.washer.component.ComponentDefinition;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.function.Predicate;

/**
 * A node representing a component usable for injection. Should be the endpoint for dependency resolution.
 *
 * @param <C> The type of component.
 */
public class ComponentNode<C> implements TransitNode {
    private final ComponentDefinition<C> component;

    public ComponentNode(@Nonnull ComponentDefinition<C> component) {
        this.component = component;
    }

    @Override
    public boolean connects(@Nonnull Node node) {
        return false;
    }

    @Nullable
    @Override
    public <T extends Node, M extends Node> T resolve(@Nonnull Node executor, @Nonnull Predicate<M> matcher) {
        return null;
    }

    @Override
    public <T extends Node> boolean inScope(@Nonnull Node executor, @Nonnull T node) {
        return false;
    }

    @Nonnull
    public ComponentDefinition<C> getComponent() {
        return component;
    }
}
