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

import javax.annotation.Nonnull;

/**
 * Provides a wrapper around lazy component types in order to delay their construction beyond the
 * moment of injection.
 *
 * @param <C> the component type (or one of its super types).
 * @author <a href="mailto:johannesd@torchmind.com">Johannes Donath</a>
 */
public interface Lazy<C> {

    /**
     * Retrieves an existing component instance or enforces the initialization of such instance.
     *
     * @return a component instance.
     */
    @Nonnull
    C getInstance();

    /**
     * Checks whether the component instance in question has already been initialized (either by
     * calling {@link #getInstance()} within this instance or through another component which
     * required access to the component instance represented by this interface.
     */
    boolean isInitialized();
}
