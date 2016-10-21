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

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;

/**
 * Represents an interface or abstract class that component classes can be bound to.
 *
 * @param <C> The interface type
 */
public interface ComponentBase<C> {

    /**
     * Get the interface/abstract class associated with this component supertype.
     *
     * @return a type
     */
    @Nonnull
    Class<C> getType();

    /**
     * Gets the bit field containing elements defined in {@link Constraints}, each of which
     * corresponding to a constraint this component object has.
     *
     * @return a bit field as a long
     */
    @Nonnegative
    long getConstraints();
}
