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
package org.basinmc.washer.discovery.custom;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Advanced support for classloaders is dependent upon both being able to retrieve a set of 
 * loaded classes and being able to retrieve bytecode of classes yet to be loaded. Use this
 * annotation to signify a type extending {@link ClassLoader} implements at least some of this
 * capability.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface CustomClassloader {

    /**
     * Signifies the capabilities of this classloader with respect to custom component discovery.
     * Each capability specified should be be present on exactly one method within the class.
     * While some functionality is available for classloaders without either functionality
     * present, it is far from usable.
     */
    Class<? extends Annotation>[] value() default {ExposeClasses.class, ExposeBytecode.class};
}
