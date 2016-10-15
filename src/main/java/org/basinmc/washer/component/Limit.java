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

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;

/**
 * Limits the number of available instances of a component based on another
 * component.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Limit {
    /**
     * The component to which this limit is relative. If the targeted component
     * itself is chosen, then the limit provided by {@link #limit()} will be
     * absolute.
     * @return A class object of the component's type.
     */
    @Nonnull Class<?> value();

    /**
     * The number of times per instance of the dependency component which
     * this component can be initialized. Defaults to 1.
     * @return A non-negative integer
     */
    @Nonnegative int limit() default 1;

    /**
     * Determines whether the given limit applies per each thread separately or
     * on a global application level.
     * @return True if the limit is per each thread, otherwise false.
     */
    boolean threadLocal() default false;
}
