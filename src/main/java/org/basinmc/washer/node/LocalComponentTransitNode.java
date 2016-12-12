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

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;

/**
 * Represents a local context that stores only components.
 */
public class LocalComponentTransitNode implements TransitNode {
    private final Set<ComponentNode> children;
    private final TransitNode parent;

    public LocalComponentTransitNode(@Nonnull TransitNode parent) {
        this.parent = parent;
        this.children = new HashSet<>();
    }

    @Override
    public boolean connects(@Nonnull Node node) {
        return node instanceof ComponentNode;
    }

    @Nullable
    @Override
    @SuppressWarnings("unchecked")
    public <T extends Node, M extends Node> T resolve(@Nonnull Node executor, @Nonnull Predicate<M> matcher) {
        for (ComponentNode child : children) {
            ComponentNode<?> componentNode = (ComponentNode<?>) child;
            if (matcher.test((M) componentNode)) {
                return (T) componentNode;
            }
        }
        return null;
    }
}
