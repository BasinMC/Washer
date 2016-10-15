package org.basinmc.washer;

import java.util.Optional;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

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
 * In some special cases, implementations may also choose to expose values from other context
 * instances to their children. In these cases the resolving process may branch. Implementations
 * should still prefer their own cases over their known children in these cases, however, to ensure
 * consistency:
 *
 * <pre>
 *      _____ Root Context ____
 *    /            |           \
 *    |            |           |
 * Child A      Child B     Child C
 * </pre>
 *
 * <h2>Thread Safety</h2>
 *
 * All implementations of this interface are expected to be <strong>thread safe</strong>. This is
 * due to the nature of dependency injection. Applications <strong>will</strong> always require some
 * form of threading and thus injection shall not be affected by thread safety concerns.
 *
 * This shifts the concern of thread safety to the objects which are accessed concurrently.
 *
 * @author <a href="mailto:johannesd@torchmind.com">Johannes Donath</a>
 */
@ThreadSafe
public interface Context {

    /**
     * Binds a supertype to a specific implementation using the default generated qualifier.
     *
     * @param base    a base type such as an abstract class or interface.
     * @param binding an implementation type to bind to.
     */
    default <C> void bind(@Nonnull Class<? super C> base, @Nonnull Class<C> binding) {
        this.bind(base, this.getDefaultQualifier(base), binding);
    }

    /**
     * Binds a super type and qualifier to a specific implementation.
     *
     * @param base      a base type such as an abstract class or interface.
     * @param qualifier a qualifier.
     * @param binding   an implementation type to bind to.
     */
    <C> void bind(@Nonnull Class<? super C> base, @Nonnull String qualifier, @Nonnull Class<C> binding);


    /**
     * Binds a type to a specific provider implementation using the default generated qualifier.
     *
     * @param base            a base type.
     * @param providerBinding a provider implementation type to bind to.
     */
    default <C> void bindProvider(@Nonnull Class<? super C> base, @Nonnull Class<Provider<C>> providerBinding) {
        this.bindProvider(base, this.getDefaultQualifier(base), providerBinding);
    }

    /**
     * Binds a type and qualifier to a specific provider implementation.
     *
     * @param base            a base type.
     * @param qualifier       a qualifier.
     * @param providerBinding a provider implementation type to bind to.
     */
    <C> void bindProvider(@Nonnull Class<? super C> base, @Nonnull String qualifier, @Nonnull Class<Provider<C>> providerBinding);

    /**
     * Checks whether the context or one of its parents can produce an instance of a certain type
     * (either through a provider or by invoking its constructor).
     *
     * @param type a type.
     * @return true if an instance can be produced, false otherwise.
     */
    default boolean canProduceInstance(@Nonnull Class<?> type) {
        return this.canProduceInstance(type, this.getDefaultQualifier(type));
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
    default boolean canProduceInstanceLocally(@Nonnull Class<?> type) {
        return this.canProduceInstanceLocally(type, this.getDefaultQualifier(type));
    }

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
     * Evicts all stored instances in this context.
     *
     * <strong>Note:</strong> It is strongly encouraged to call this method before discarding a
     * context reference in order to ensure that parent contexts are notified of object destructions
     * accordingly.
     */
    void clear();

    /**
     * Clears all introduced bindings.
     */
    void clearAllBindings();

    /**
     * Clears all bindings for a certain base type.
     *
     * @param base a base type such as an abstract class or interface.
     */
    void clearAllBindings(@Nonnull Class<?> base);

    /**
     * Clears the default binding (attributed to the generated qualifier).
     *
     * @param base a base type such as an abstract class or interface.
     */
    default void clearBinding(@Nonnull Class<?> base) {
        this.clearBinding(base, this.getDefaultQualifier(base));
    }

    /**
     * Clears all bindings which match the specified filter predicate.
     *
     * @param base            a base type such as an abstract class or interface.
     * @param filterPredicate a filter predicate which accepts a bound implementation type.
     */
    <C> void clearBinding(@Nonnull Class<C> base, @Nonnull Predicate<Class<? extends C>> filterPredicate);

    /**
     * Clears all bindings which match the specified filter predicate.
     *
     * @param base            a base type such as an abstract class or interface.
     * @param filterPredicate a filter predicate which accepts a qualifier and a bound
     *                        implementation type.
     */
    <C> void clearBinding(@Nonnull Class<C> base, @Nonnull BiPredicate<String, Class<? extends C>> filterPredicate);

    /**
     * Clears a binding for a certain type and qualifier.
     *
     * @param base      a base type such as an abstract class or interface.
     * @param qualifier a qualifier.
     */
    void clearBinding(@Nonnull Class<?> base, @Nonnull String qualifier);

    /**
     * Clears all provider bindings which match the specified filter predicate.
     *
     * @param base            a base type.
     * @param filterPredicate a filter predicate which accepts a bound provider implementation
     *                        type.
     */
    <C> void clearProviderBinding(@Nonnull Class<C> base, @Nonnull Predicate<Class<Provider<? extends C>>> filterPredicate);

    /**
     * Clears all provider bindings which match the specified filter predicate.
     *
     * @param base            a base type.
     * @param filterPredicate a filter predicate which accepts a qualifier and bound provider
     *                        implementation type.
     */
    <C> void clearProviderBinding(@Nonnull Class<C> base, @Nonnull BiPredicate<String, Class<Provider<? extends C>>> filterPredicate);

    /**
     * Retrieves an instance of a certain type from the context.
     *
     * @param type a type.
     * @return an instance or, if no such component could be located, an empty optional.
     */
    @Nonnull
    default <T> Optional<T> get(@Nonnull Class<T> type) {
        return this.get(type, this.getDefaultQualifier(type));
    }

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
     * Retrieves the generated default qualifier for a certain type.
     *
     * @param type a type.
     * @return a qualifier.
     */
    @Nonnull
    String getDefaultQualifier(@Nonnull Class<?> type);

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
    default <C> Optional<Provider<C>> getProvider(@Nonnull Class<C> type) {
        return this.getProvider(type, this.getDefaultQualifier(type));
    }

    /**
     * Retrieves the provider (a factory for) the specified type and qualifier.
     *
     * @param type      a type.
     * @param qualifier a qualifier.
     * @return a provider implementation or, if no such provider could be located, an empty
     * optional.
     */
    @Nonnull
    <C> Optional<Provider<C>> getProvider(@Nonnull Class<C> type, @Nonnull String qualifier);

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
        return this.hasInstance(type, this.getDefaultQualifier(type));
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
    default boolean hasLocalInstance(@Nonnull Class<?> type) {
        return this.hasLocalInstance(type, this.getDefaultQualifier(type));
    }

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

    /**
     * Removes all component instances of a specific type from the context.
     *
     * <strong>Note:</strong> This will not account for non-singleton instances since they are not
     * actively stored in the context. These objects can be safely discarded by removing all strong
     * references to their instance.
     *
     * @param type an instance type.
     */
    void removeAllInstances(@Nonnull Class<?> type);

    /**
     * Removes a specific component instance from the context.
     *
     * <strong>Note:</strong> This will not account for non-singleton instances since they are not
     * actively stored in the context. These objects can be safely discarded by removing all strong
     * references to their instance.
     *
     * @param type an instance type.
     */
    default void removeInstance(@Nonnull Class<?> type) {
        this.removeInstance(type, this.getDefaultQualifier(type));
    }

    /**
     * Removes all instances of a certain type that match the supplied filter function.
     *
     * <strong>Note:</strong> This will not account for non-singleton instances since they are not
     * actively stored in the context. These objects can be safely discarded by removing all strong
     * references to their instance.
     *
     * @param type            a type.
     * @param filterPredicate a filter which accepts an instance.
     */
    <C> void removeInstance(@Nonnull Class<C> type, @Nonnull Predicate<C> filterPredicate);

    /**
     * Removes all instance of a certain type that match the supplied filter function.
     *
     * <strong>Note:</strong> This will not account for non-singleton instances since they are not
     * actively stored in the context. These objects can be safely discarded by removing all strong
     * references to their instance.
     *
     * @param type            a type.
     * @param filterPredicate a filter predicate which accepts the qualifier and instance.
     */
    <C> void removeInstance(@Nonnull Class<C> type, @Nonnull BiPredicate<String, C> filterPredicate);

    /**
     * Removes a specific component instance with a certain qualifier from the context.
     *
     * <strong>Note:</strong> This will not account for non-singleton instances since they are not
     * actively stored in the context. These objects can be safely discarded by removing all strong
     * references to their instance.
     *
     * @param type      an instance type.
     * @param qualifier an instance qualifier.
     */
    void removeInstance(@Nonnull Class<?> type, @Nonnull String qualifier);

    /**
     * Removes an instance of a certain type from the context.
     *
     * @param instance an instance.
     * @see #removeInstance(Class, Object) to remove instances of interfaces which have been
     * introduced by a {@link Provider} implementation or using {@link
     * org.basinmc.washer.component.ProvidedBy}.
     */
    @SuppressWarnings("unchecked")
    default void removeInstance(@Nonnull Object instance) {
        this.removeInstance((Class) instance.getClass(), instance);
    }

    /**
     * Removes an instance of a certain type from the context.
     *
     * @param type     a type.
     * @param instance an instance.
     */
    <C> void removeInstance(@Nonnull Class<? super C> type, @Nonnull C instance);

    /**
     * Stores an instance in the context using its core type and the default qualifier.
     *
     * @param instance an instance.
     */
    default void setInstance(@Nonnull Object instance) {
        this.setInstance(instance.getClass(), instance);
    }

    /**
     * Stores an instance using a specified qualifier and its core type.
     *
     * @param qualifier a qualifier.
     * @param instance  an instance.
     */
    default void setInstance(@Nonnull String qualifier, @Nonnull Object instance) {
        this.setInstance(instance.getClass(), qualifier, instance);
    }

    /**
     * Stores an instance using a specified base type and a generated default qualifier.
     *
     * @param base     a base type such as an abstract class or interface.
     * @param instance an instance.
     */
    default <C> void setInstance(@Nonnull Class<? super C> base, @Nonnull Object instance) {
        this.setInstance(base, this.getDefaultQualifier(base), instance);
    }

    /**
     * Stores an instance using a specified base type and qualifier.
     *
     * @param base      a base type such as an abstract class or interface.
     * @param qualifier a qualifier.
     * @param instance  an instance.
     */
    <C> void setInstance(@Nonnull Class<? super C> base, @Nonnull String qualifier, @Nonnull Object instance);
}
