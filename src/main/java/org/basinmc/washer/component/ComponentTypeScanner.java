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

import org.basinmc.washer.annotation.ComponentType;

import java.lang.annotation.Annotation;

import javax.annotation.Nonnull;

/**
 * A component scanner which is notified of the construction of all annotated types.
 *
 * @param <A> the annotation type this scanner shall respond to.
 * @param <C> the type this scanner shall exclusively respond to.
 * @author <a href="mailto:johannesd@torchmind.com">Johannes Donath</a>
 * @see ComponentType for methods of declaring component type scanners.
 */
public interface ComponentTypeScanner<A extends Annotation, C> {

    /**
     * Handles the discovery of a component type.
     *
     * @param annotation an instance of the annotation which caused this scanner to be notified.
     * @param type       a reference to the component type.
     */
    default void onDiscovery(@Nonnull A annotation, @Nonnull Class<? extends C> type) {
    }

    /**
     * Handles the pre-construction (e.g. when it is decided that the component is to be constructed
     * but has yet to be constructed).
     *
     * @param annotation an instance of the annotation which caused this scanner to be notified.
     * @param type       a reference to the component type.
     */
    default void onPreConstruct(@Nonnull A annotation, @Nonnull Class<? extends C> type) {
    }

    /**
     * Handles the construction of a new component instance.
     *
     * @param annotation an instance of the annotation which caused this scanner to be notified.
     * @param instance   an instance of the constructed component.
     */
    default void onConstruct(@Nonnull A annotation, @Nonnull C instance) {
    }

    /**
     * Handles the destruction of a new component instance.
     *
     * @param annotation an instance of the annotation which caused this scanner to be notified.
     * @param instance   an instance of the constructed component.
     */
    default void onDestruct(@Nonnull A annotation, @Nonnull C instance) {
    }

    /**
     * Handles the post-destruction (e.g. when an instance has been removed from the context).
     *
     * @param annotation an instance of the annotation which caused this scanner to be notified.
     * @param type       a reference to the component type.
     */
    default void onPostDestruct(@Nonnull A annotation, @Nonnull Class<? extends C> type) {
    }
}
