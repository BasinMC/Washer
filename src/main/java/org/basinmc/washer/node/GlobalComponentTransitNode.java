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
package org.basinmc.washer.node;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * The global context, serving as parent to every other in the application. One instance should exist per application.
 * Wraps a {@link org.basinmc.washer.Context}
 */
public class GlobalComponentTransitNode implements TransitNode {
    private Set<TransitNode> children;
    private Cache<Predicate<? extends Node>, Node> cache;

    public GlobalComponentTransitNode() {
        this.children = new HashSet<>();
        this.cache = CacheBuilder.newBuilder()
                .concurrencyLevel(4)
                .expireAfterAccess(60L, TimeUnit.SECONDS)
                .initialCapacity(128)
                .maximumSize(128)
                .build();
    }

    @Override
    public boolean connects(@Nonnull Node node) {
        return true;
    }

    @Nullable
    @Override
    @SuppressWarnings("unchecked")
    public <T extends Node, M extends Node> T resolve(@Nonnull Node executor, @Nonnull Predicate<M> matcher) {
        try {
            return (T) cache.get(matcher, () -> {
                for (TransitNode node : children.stream().filter(n -> !n.equals(executor)).collect(Collectors.toSet())) {
                    Optional<? extends Node> opt = Optional.ofNullable(node.resolve(executor, matcher));
                    if (opt.isPresent()) {
                        return opt.get();
                    }
                }
                return null;
            });
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }
}
