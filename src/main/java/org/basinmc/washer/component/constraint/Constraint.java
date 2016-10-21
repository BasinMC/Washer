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
package org.basinmc.washer.component.constraint;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.annotation.Nonnull;

/**
 * Annotations this annotation is applied to will be considered as constraints which are evaluated
 * upon all annotated component types using the constraint declared by {@link #value()}.
 *
 * <strong>Note:</strong> The value passed to {@link #value()} will be searched for in the local
 * context and thus can be annotated with any of the component annotations provided by the
 * application in order to enforce their context within the application.
 *
 * @author <a href="mailto:johannesd@torchmind.com">Johannes Donath</a>
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.ANNOTATION_TYPE)
public @interface Constraint {

    /**
     * Declares the constraint implementation which will evaluate components on behalf of the
     * annotated annotation.
     */
    @Nonnull
    Class<? extends ComponentConstraint> value();
}
