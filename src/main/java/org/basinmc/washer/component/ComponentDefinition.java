/*
 * Copyright 2016 Johannes Donath <johannesd@torchmind.com>
 * and other copyright owners as documented in the project's IP log.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * 	http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.basinmc.washer.component;

import org.basinmc.washer.component.constraint.ComponentConstraint;

import java.util.Deque;
import java.util.Optional;

import javax.annotation.Nonnull;
import javax.inject.Provider;

/**
 * Provides all necessary information around a discovered component definition such as type,
 * implementation type, order or provider.
 *
 * @author <a href="mailto:johannesd@torchmind.com">Johannes Donath</a>
 */
public interface ComponentDefinition<C> {

    /**
     * Retrieves a list of constraints which will be evaluated for this definition.
     *
     * @return a queue of constraints.
     */
    @Nonnull
    Deque<ComponentConstraint> getConstraints();

    /**
     * Retrieves the core component type which this definition will return instances of.
     */
    @Nonnull
    Class<C> getType();

    /**
     * Retrieves an implementation type (e.g. an implementation or extension of, the base type as
     * returned by {@link #getType()}.
     *
     * @return an implementation type or, if no specific implementation was bound, an empty
     * optional.
     */
    @Nonnull
    Optional<Class<? extends C>> getImplementationType();

    /**
     * Retrieves the numerical order of priority this definition has where {@link Integer#MAX_VALUE}
     * is the highest and {@link Integer#MIN_VALUE} is the lowest priority.
     *
     * <strong>Note:</strong> This priority is considered for eager initialization only. It does not
     * allow to shadow other declarations.
     */
    int getOrder();

    /**
     * Retrieves the provider type which produces an instance of this definition when called.
     *
     * @return a provider implementation or, if no provider was declared for this definition, an
     * empty optional.
     */
    @Nonnull
    Optional<Class<? extends Provider<C>>> getProviderType();

    /**
     * Retrieves the qualifier bound to this definition.
     */
    @Nonnull
    String getQualifier();

    /**
     * Checks whether the definition matches its constraints.
     *
     * @return true if matches, false otherwise.
     */
    boolean matchesConstraints();
}
