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

/**
 * Provides a list of initialization strategies which dictate how application components are
 * initialized.
 *
 * @author <a href="mailto:johannesd@torchmind.com">Johannes Donath</a>
 */
public enum InitializationStrategy {

    /**
     * <strong>Lazy Initialization</strong>
     *
     * Instructs the context to hold off on initialization until the component is actively required
     * by one or more components.
     */
    LAZY,

    /**
     * <strong>Eager Initialization</strong>
     *
     * Instructs the context to initialize a component type as soon as the context leaves its
     * scanning phase.
     *
     * <strong>Note:</strong> This strategy is not valid for non-singleton components since their
     * construction is bound to their use in other types rather than context scope.
     */
    EAGER
}
