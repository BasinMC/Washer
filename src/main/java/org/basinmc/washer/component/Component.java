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
package org.basinmc.washer.component;

import javax.annotation.Nonnull;

/**
 * Represents an internally-represented component. This should be implemented independent of
 * the context family in which they reside.
 *
 * @param <C> The component type
 */
public interface Component<C> {

    /**
     * Retrieve the base interface/abstract class type that this component corresponds to.
     *
     * @return a {@link ComponentBase}
     */
    @Nonnull
    ComponentBase<? super C> getBaseType();

    /**
     * Attempts to construct a new instance of this component by injecting other components
     * <strong>already available</strong> in the context family.
     *
     * @return a new instance of the component type, if construction was successful.
     * @throws IllegalStateException if not all required components are available.
     */
    @Nonnull
    C newInstance() throws IllegalStateException;

    /**
     * Determines if this component implementation has a specific qualifier associated with it.
     *
     * @return true if there is a specific qualifier, false if it uses the default qualifier.
     */
    boolean isQualified();

    /**
     * Get the component's qualifier. The default qualifier for its context will be returned if
     * none is specified, so it is best to check {@link #isQualified()} before retrieval.
     *
     * @return the qualifier as a String
     */
    @Nonnull
    String getQualifier();
}
