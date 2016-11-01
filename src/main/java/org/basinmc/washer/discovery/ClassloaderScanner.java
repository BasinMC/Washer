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
package org.basinmc.washer.discovery;

import org.basinmc.washer.discovery.custom.CustomClassloader;
import org.basinmc.washer.discovery.custom.ExposeBytecode;
import org.basinmc.washer.discovery.custom.ExposeClasses;

import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.invoke.CallSite;
import java.lang.invoke.LambdaMetafactory;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Method;
import java.net.URLClassLoader;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Optional;
import java.util.Set;
import java.util.function.Supplier;

@SuppressWarnings("unchecked")
public class ClassloaderScanner {
    private ClassLoader classLoader;
    private Class<? extends ClassLoader> classLoaderClass;
    private Set<Class<?>> classes;
    private Optional<Supplier<Collection<Class<?>>>> loadedClassSupplier;
    private Optional<Supplier<Iterator<InputStream>>> bytecodeSupplier;

    public ClassloaderScanner(ClassLoader classLoader) {
        this.classLoader = classLoader;
        this.classLoaderClass = classLoader.getClass();
        this.classes = new HashSet<>();
        boolean findLoadedSupplier = false, findBytecodeSupplier = false;
        if (classLoaderClass.isAnnotationPresent(CustomClassloader.class)) {
            CustomClassloader data = classLoaderClass.getAnnotation(CustomClassloader.class);
            for (Class<? extends Annotation> a : data.value()) {
                if (a.equals(ExposeClasses.class)) {
                    findLoadedSupplier = true;
                } else if (a.equals(ExposeBytecode.class)) {
                    findBytecodeSupplier = true;
                }
            }
            for (Method method : classLoaderClass.getMethods()) {
                if (findLoadedSupplier && method.isAnnotationPresent(ExposeClasses.class)) {
                    if (Collection.class.isAssignableFrom(method.getReturnType())) {
                        try {
                            loadedClassSupplier = Optional.of(lookupSupplierVirtual(classLoaderClass, method.getName(), Collection.class));
                        } catch (Throwable t) {
                            t.printStackTrace();
                            loadedClassSupplier = Optional.empty();
                        }
                    }
                } else if (findBytecodeSupplier && method.isAnnotationPresent(ExposeBytecode.class)) {
                    if (Iterator.class.isAssignableFrom(method.getReturnType())) {
                        try {
                            bytecodeSupplier = Optional.of(lookupSupplierVirtual(classLoaderClass, method.getName(), Iterator.class));
                        } catch (Throwable t) {
                            t.printStackTrace();
                            bytecodeSupplier = Optional.empty();
                        }
                    }
                }

                if (loadedClassSupplier != null && bytecodeSupplier != null) {
                    break;
                }
            }

        } else if (classLoaderClass.equals(URLClassLoader.class)) {
            // TODO From a default URLClassLoader, we actually have enough information to proceed.
        }
    }

    /**
     * Gets a {@link Supplier} from reflective information
     * @param owner The owning class
     * @param name The method name
     * @param type The type which the supplier returns
     * @param <T> The type which the supplier returns
     *
     * @return A supplier
     * @throws Throwable If a reflective operation fails, or if the call site throws an exception.
     */
    <T> Supplier<T> lookupSupplierVirtual(Class<?> owner, String name, Class<? extends T> type) throws Throwable {
        MethodHandles.Lookup lookup = MethodHandles.lookup();
        MethodType returnType = MethodType.methodType(type);
        MethodType objectType = MethodType.methodType(Object.class);
        MethodType supplierType = MethodType.methodType(Supplier.class);
        CallSite callSite = LambdaMetafactory.metafactory(lookup, "get", objectType, returnType,
                lookup.findVirtual(owner, name, supplierType), objectType);
        MethodHandle handle = callSite.getTarget();
        return (Supplier<T>) handle.invoke();
    }
}
