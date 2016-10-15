package org.basinmc.washer.component;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.annotation.Nonnull;

/**
 * Marks the annotation this annotation is applied to as a component type annotation.
 *
 * This annotation allows scanners to be implemented in order to specially handle initialized
 * instances of annotated components and possibly register them with a management component of
 * sorts.
 *
 * @author <a href="mailto:johannesd@torchmind.com">Johannes Donath</a>
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.ANNOTATION_TYPE})
public @interface ComponentType {

    /**
     * Defines the scanner which is in charge of handling the discovery, construction and
     * destruction of instances within this component type.
     *
     * @return a scanner implementation.
     */
    @Nonnull
    Class<? extends ComponentTypeScanner> scanner();

    /**
     * Defines the type a component has to implement in order to be considered valid.
     *
     * @return a component type.
     */
    @Nonnull
    Class<?> type() default Object.class;
}
