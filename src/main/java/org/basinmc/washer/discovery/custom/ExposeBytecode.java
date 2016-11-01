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

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Marks the method as exposing bytecode input streams for classes yet to be loaded. Annotated
 * methods must return an {@link java.util.Iterator} of {@link java.io.InputStream}s from which
 * valid Java bytecode can be read. Each InputStream element should correspond to one class,
 * and each class should not already be loaded by the classloader.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface ExposeBytecode {
}