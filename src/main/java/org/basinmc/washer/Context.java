package org.basinmc.washer;

import java.util.Optional;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.ThreadSafe;
import javax.inject.Provider;

/**
 * Represents a context consisting of one or more instances and their respective life cycles.
 *
 * Generally all context implementations are expected to prioritize their local context over any
 * parent instances that may exist. The process of resolving an instance or provider is thus:
 *
 * Local Context -&gt; Parent Context -&gt; Parent Parent Context -&gt; ...
 *
 * In addition all implementations of this interface are expected to be <strong>thread safe</strong>
 * due to the nature of dependency injection.
 *
 * @author <a href="mailto:johannesd@torchmind.com">Johannes Donath</a>
 */
@ThreadSafe
public interface Context {

    /**
     * Checks whether the context or one of its parents can produce an instance of a certain type
     * (either through a provider or by invoking its constructor).
     *
     * @param type a type.
     * @return true if an instance can be produced, false otherwise.
     */
    default boolean canProduceInstance(@Nonnull Class<?> type) {
        return this.canProduceInstanceLocally(type) || this.getParent().map((c) -> c.canProduceInstance(type)).orElse(false);
    }

    /**
     * Checks whether the context or one of its parents can produce an instance of a certain type
     * and a certain qualifier (either through a provider or by invoking its constructor).
     *
     * @param type      a type.
     * @param qualifier a qualifier.
     * @return true if an instance can be produced, false otherwise.
     */
    default boolean canProduceInstance(@Nonnull Class<?> type, @Nonnull String qualifier) {
        return this.canProduceInstanceLocally(type, qualifier) || this.getParent().map((c) -> c.canProduceInstance(type, qualifier)).orElse(false);
    }

    /**
     * Checks whether the context can produce an instance of a certain type locally (e.g. a provider
     * is locally available or its type is considered part of this context while providing a viable
     * constructor).
     *
     * @param type a type.
     * @return true if an instance can be produced locally, false otherwise.
     */
    boolean canProduceInstanceLocally(@Nonnull Class<?> type);

    /**
     * Checks whether the context can produce an instance of a certain type locally (e.g. a provider
     * is locally available or its type is considered part of this context while providing a viable
     * constructor).
     *
     * @param type      a type.
     * @param qualifier a qualifier.
     * @return true if an instance can be produced locally, false otherwise.
     */
    boolean canProduceInstanceLocally(@Nonnull Class<?> type, @Nonnull String qualifier);

    /**
     * Retrieves an instance of a certain type from the context.
     *
     * @param type a type.
     * @return an instance or, if no such component could be located, an empty optional.
     */
    @Nonnull
    <T> Optional<T> get(@Nonnull Class<T> type);

    /**
     * Retrieves an instance with a certain qualifier and type from the context.
     *
     * @param type      a type.
     * @param qualifier a qualifier.
     * @return an instance or, if no such component could be located, an empty optional.
     */
    @Nonnull
    <T> Optional<T> get(@Nonnull Class<T> type, @Nonnull String qualifier);

    /**
     * Retrieves the parent context which will be used to resolve instances when values cannot be
     * located in the local context.
     *
     * @return a context or, if no parent was set, an empty optional.
     */
    @Nonnull
    Optional<Context> getParent();

    /**
     * Retrieves the provider (a factory for) the specified type.
     *
     * @param type a type.
     * @return a provider implementation or, if no such provider could be located, an empty
     * optional.
     */
    @Nonnull
    <T> Optional<Provider<T>> getProvider(@Nonnull Class<T> type);

    /**
     * Checks whether this context or one of its parents contains an instance of a specific
     * component type.
     *
     * @param type a type.
     * @return true if an instance exists, false otherwise.
     *
     * @see #canProduceInstance(Class) for verifying whether this context or one of its parents is
     * capable of constructing an instance.
     */
    default boolean hasInstance(@Nonnull Class<?> type) {
        return this.hasLocalInstance(type) || this.getParent().map((c) -> c.hasInstance(type)).orElse(false);
    }

    /**
     * Checks whether this context or one of its parents contains an instance of a specific
     * component type and qualifier.
     *
     * @param type      a type.
     * @param qualifier a qualifier.
     * @return true if an instance exists, false otherwise.
     *
     * @see #canProduceInstance(Class, String) for verifying whether this context or one of its
     * parents is capable of constructing an instance.
     */
    default boolean hasInstance(@Nonnull Class<?> type, @Nonnull String qualifier) {
        return this.hasLocalInstance(type, qualifier) || this.getParent().map((c) -> c.hasInstance(type, qualifier)).orElse(false);
    }

    /**
     * Checks whether this context contains an instance for a specific component type.
     *
     * @param type a type.
     * @return true if an instance exists, false otherwise.
     *
     * @see #canProduceInstanceLocally(Class) for verifying whether this context is capable of
     * constructing an instance.
     */
    boolean hasLocalInstance(@Nonnull Class<?> type);

    /**
     * Checks whether this context contains an instance of a specific component type and qualifier.
     *
     * @param type      a type.
     * @param qualifier a qualifier.
     * @return true if an instance exists, false otherwise.
     *
     * @see #canProduceInstanceLocally(Class, String) for verifying whether this context is capable
     * of constructing an instance.
     */
    boolean hasLocalInstance(@Nonnull Class<?> type, @Nonnull String qualifier);

    /**
     * Injects all annotated fields in an object using the objects and providers present within this
     * context.
     *
     * @param object an object.
     */
    void inject(@Nonnull Object object);
}
