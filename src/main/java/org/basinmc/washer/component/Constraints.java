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
package org.basinmc.washer.component;

/**
 * Named constraints that might apply to a component
 */
public final class Constraints {

    // Applies to a component's construction and destruction.
    /**
     * Component is a singleton. This means that one single instance is created and shared with
     * all linked contexts. If a context family is unable to retrieve the instance, then it is
     * constructed again for that context family.
     */
    public static final int SINGLETON = 1<<0;

    /**
     * The component has an arbitrary limit on the number of instances that can be created. No
     * current implementation for this exists, this is for future-proofing.
     */
    public static final int LIMIT = 1<<1;

    /**
     * Constructs the component when it is required, injects it, then immediately disposes it
     * without any caching.
     */
    public static final int DISPOSE = 1<<2;

    /**
     * Will not, under any circumstances, automatically dispose of instances of this component.
     * Note that this component will still be destroyed if done manually.
     */
    public static final int RETAIN = 1<<3;

    // Limit modifiers - not yet implemented. Mostly here so I don't forget someday.
    public static final int LIM_PUSH = 1<<4;
    public static final int LIM_DROP = 1<<5;
    public static final int LIM_LAST = 1<<6;
    public static final int LIM_FRONT = 1<<7;
    public static final int LIM_REL = 1<<8;
}
