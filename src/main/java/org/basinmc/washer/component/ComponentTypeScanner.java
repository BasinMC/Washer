package org.basinmc.washer.component;

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
