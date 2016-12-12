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
import java.util.function.Predicate;

/**
 * Connects two transit nodes in a <i>one-way</i> manner.
 */
public class BiComponentTransitNode implements TransitNode {
    private final TransitNode from;
    private final TransitNode to;

    public BiComponentTransitNode(TransitNode from, TransitNode to) {
        this.from = from;
        this.to = to;
    }

    @Override
    public boolean connects(@Nonnull Node node) {
        return node.equals(to);
    }

    @Nullable
    @Override
    public <T extends Node, M extends Node> T resolve(@Nonnull Node executor, @Nonnull Predicate<M> matcher) {
        return to.resolve(from, matcher);
    }

    @Override
    public <T extends Node> boolean inScope(@Nonnull Node executor, @Nonnull T node) {
        return to.inScope(from, node);
    }
}
