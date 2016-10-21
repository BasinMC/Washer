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

import org.basinmc.washer.component.ComponentDefinition;

import java.lang.annotation.Annotation;

import javax.annotation.Nonnull;

/**
 * Provides a base interface to constraints which evaluate the initialization of eager singleton
 * components.
 *
 * @author <a href="mailto:johannesd@torchmind.com">Johannes Donath</a>
 */
public interface ComponentConstraint<A extends Annotation> {

    /**
     * Checks whether the passed component matches this constraint and is thus to be initialized.
     *
     * @param annotation a constraint annotation.
     * @param definition a component definition.
     * @return true if matches, false otherwise.
     */
    boolean matches(@Nonnull Annotation annotation, @Nonnull ComponentDefinition<?> definition);
}
