/*
 * Copyright 2016 Hex <hex@hex.lc>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.basinmc.washer.node;

import org.basinmc.washer.Context;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.inject.Provider;
import java.util.Optional;
import java.util.function.Predicate;

abstract class TransitContext implements Context {
    private final Context parent;

    protected TransitContext(@Nullable Context parent) {
        this.parent = parent;
    }

    /**
     * Binds a supertype to a specific implementation using the default generated qualifier.
     *
     * @param base    a base type such as an abstract class or interface.
     * @param binding an implementation type to bind to.
     */
    @Override
    public <C> void bind(@Nonnull Class<? super C> base, @Nonnull Class<C> binding) {
        bind(base, getDefaultQualifier(base), binding);
    }

    /**
     * Binds a type to a specific provider implementation using the default generated qualifier.
     *
     * @param base            a base type.
     * @param providerBinding a provider implementation type to bind to.
     */
    @Override
    public <C> void bindProvider(@Nonnull Class<? super C> base, @Nonnull Class<Provider<C>> providerBinding) {
        bindProvider(base, getDefaultQualifier(base), providerBinding);
    }

    /**
     * Checks whether the context or one of its parents can produce an instance of a certain type
     * (either through a provider or by invoking its constructor).
     *
     * @param type a type.
     * @return true if an instance can be produced, false otherwise.
     */
    @Override
    public boolean canProduceInstance(@Nonnull Class<?> type) {
        return canProduceInstance(type, getDefaultQualifier(type));
    }

    /**
     * Checks whether the context can produce an instance of a certain type locally (e.g. a provider
     * is locally available or its type is considered part of this context while providing a viable
     * constructor).
     *
     * @param type a type.
     * @return true if an instance can be produced locally, false otherwise.
     */
    @Override
    public boolean canProduceInstanceLocally(@Nonnull Class<?> type) {
        return canProduceInstanceLocally(type, getDefaultQualifier(type));
    }

    /**
     * Clears the default binding (attributed to the generated qualifier).
     *
     * @param base a base type such as an abstract class or interface.
     */
    @Override
    public void clearBinding(@Nonnull Class<?> base) {
        clearBinding(base, getDefaultQualifier(base));
    }

    /**
     * Clears all bindings which match the specified filter predicate.
     *
     * @param base            a base type such as an abstract class or interface.
     * @param filterPredicate a filter predicate which accepts a bound implementation type.
     */
    @Override
    public <C> void clearBinding(@Nonnull Class<C> base, @Nonnull Predicate<Class<? extends C>> filterPredicate) {
        clearBinding(base, (qualifier, type) -> qualifier.equals(getDefaultQualifier(base)) && filterPredicate.test(type));
    }

    /**
     * Retrieves the parent context which will be used to resolve instances when values cannot be
     * located in the local context.
     *
     * @return a context or, if no parent was set, an empty optional.
     */
    @Nonnull
    @Override
    public Optional<Context> getParent() {
        return Optional.ofNullable(parent);
    }

    /**
     * Retrieves the provider (a factory for) the specified type and qualifier.
     *
     * @param type a type.
     * @return a provider implementation or, if no such provider could be located, an empty
     * optional.
     */
    @Nonnull
    @Override
    public <C> Optional<Provider<C>> getProvider(@Nonnull Class<C> type) {
        return getProvider(type, getDefaultQualifier(type));
    }

    /**
     * Checks whether this context or one of its parents contains an instance of a specific
     * component type.
     *
     * @param type a type.
     * @return true if an instance exists, false otherwise.
     * @see #canProduceInstance(Class) for verifying whether this context or one of its parents is
     * capable of constructing an instance.
     */
    @Override
    public boolean hasInstance(@Nonnull Class<?> type) {
        return hasInstance(type, getDefaultQualifier(type));
    }

    /**
     * Checks whether this context contains an instance for a specific component type.
     *
     * @param type a type.
     * @return true if an instance exists, false otherwise.
     * @see #canProduceInstanceLocally(Class) for verifying whether this context is capable of
     * constructing an instance.
     */
    @Override
    public boolean hasLocalInstance(@Nonnull Class<?> type) {
        return hasLocalInstance(type, getDefaultQualifier(type));
    }

    /**
     * Removes a specific component instance from the context.
     * <p>
     * <strong>Note:</strong> This will not account for non-singleton instances since they are not
     * actively stored in the context. These objects can be safely discarded by removing all strong
     * references to their instance.
     *
     * @param type an instance type.
     */
    @Override
    public void removeInstance(@Nonnull Class<?> type) {
        removeInstance(type, getDefaultQualifier(type));
    }

    /**
     * Stores an instance in the context using its core type and the default qualifier.
     *
     * @param instance an instance.
     */
    @Override
    public void setInstance(@Nonnull Object instance) {
        setInstance(getDefaultQualifier(instance.getClass()), instance); // TODO - this isn't how this works.
    }

    /**
     * Stores an instance using a specified base type and a generated default qualifier.
     *
     * @param base     a base type such as an abstract class or interface.
     * @param instance an instance.
     */
    @Override
    public <C> void setInstance(@Nonnull Class<? super C> base, @Nonnull Object instance) {
        setInstance(base, getDefaultQualifier(base), instance);
    }
}
