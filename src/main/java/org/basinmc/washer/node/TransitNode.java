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
import javax.inject.Provider;
import java.util.function.Predicate;

/**
 * Represents a node in the dependency tree that can serve as a connection between two things.
 */
public interface TransitNode extends Node {
    /**
     * Determines if this node can connect to a specified other node. Note that such connections are not necessarily
     * bidirectional.
     *
     * @param node The node being connected to
     * @return Whether the connection is permitted
     */
    boolean connects(@Nonnull Node node);

    /**
     * Attempts to resolve a node in a linear matter. For a linear transit node, this would always delegate to its child,
     * while a parent transit node would search through each child it has. The only order guarantee is that the "closer"
     * a context is to this one, the first it is to be checked.
     *
     * @param executor The node executing the resolution request
     * @param matcher  A predicate to determine which node is suitable. The first matching node will be returned.
     * @param <T>      The type of node this is
     * @return A node connected to this one
     */
    @Nullable
    <T extends Node, M extends Node> T resolve(@Nonnull Node executor, @Nonnull Predicate<M> matcher);

    /**
     * Determines if a node is resolvable using the same logic as {@link #resolve(Node, Predicate)}
     *
     * @param executor The node executing the request
     * @param node     The node to search for
     * @param <T>      The type of node
     * @return whether the node is present
     */
    default <T extends Node> boolean inScope(@Nonnull Node executor, @Nonnull T node) {
        return resolve(executor, node::equals) != null;
    }

    /**
     * Delegate call for {@link org.basinmc.washer.Context#bind(Class, Class)}, for use internally.
     *
     * @return A new component node to be added to the tree
     */
    default <T> ComponentNode<T> nodify(@Nonnull Class<? super T> base, @Nonnull String qualifier, @Nonnull Class<Provider<T>> providerBinding) {
        return null; // TODO
    }

    /**
     * Where the magic happens. Generates a provider to create instances of components.
     *
     * @return The provider for the specific binding.
     */
    default <T> Class<Provider<T>> createProvider(Class<T> binding) {
        return null; // TODO
    }
}
