package io.github.jwdeveloper.spigot.commands.builder;

import io.github.jwdeveloper.spigot.commands.builder.arguments.ArgumentBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;

import java.util.function.Consumer;

/**
 * Interface for building command arguments.
 *
 * @param <T> The type of the builder implementing this interface.
 */
public interface CommandArgumentsBuilder<T> {

    /**
     * Provides direct access to the builder of a specified argument.
     * If the argument does not exist, it creates a new builder and returns it.
     *
     * @param name the name of the argument.
     * @return the builder for the specified argument.
     */
    ArgumentBuilder argument(String name);

    /**
     * Creates a new argument and provides a consumer with the builder for the specified argument.
     *
     * @param name    the name of the argument.
     * @param builder the consumer that builds the argument.
     * @return the builder instance.
     */
    T addArgument(String name, Consumer<ArgumentBuilder> builder);

    /**
     * Adds a new text argument with a default type of "Text".
     *
     * @param name the name of the argument.
     * @return the builder instance.
     */
    default T addArgument(String name) {
        return addArgument(name, "Text", (x) -> {
        });
    }

    /**
     * Adds a new argument with the specified type.
     *
     * @param name         the name of the argument.
     * @param argumentType the type of the argument.
     * @return the builder instance.
     */
    default T addArgument(String name, String argumentType) {
        return addArgument(name, argumentType, (x) -> {
        });
    }

    /**
     * Adds a new argument with the specified type and a consumer for additional configuration.
     *
     * @param name         the name of the argument.
     * @param argumentType the type of the argument.
     * @param action       the consumer for additional configuration.
     * @return the builder instance.
     */
    default T addArgument(String name, String argumentType, Consumer<ArgumentBuilder> action) {
        return addArgument(name, argumentBuilder -> {
            argumentBuilder.withType(argumentType);
            action.accept(argumentBuilder);
        });
    }

    /**
     * Adds a new number argument.
     * Number argument holds the Double value
     *
     * @param argumentName the name of the argument.
     * @return the builder instance.
     */
    default T addNumberArgument(String argumentName) {
        return addArgument(argumentName, "Number");
    }

    /**
     * Adds a new number argument with additional configuration.
     * Number argument holds the Double value
     *
     * @param argumentName the name of the argument.
     * @param action       the consumer for additional configuration.
     * @return the builder instance.
     */
    default T addNumberArgument(String argumentName, Consumer<ArgumentBuilder> action) {
        return addArgument(argumentName, "Number", action);
    }

    /**
     * Adds a new text argument.
     *
     * @param argumentName the name of the argument.
     * @return the builder instance.
     */
    default T addTextArgument(String argumentName) {
        return addArgument(argumentName, "Text");
    }

    /**
     * Adds a new text argument with additional configuration.
     *
     * @param argumentName the name of the argument.
     * @param action       the consumer for additional configuration.
     * @return the builder instance.
     */
    default T addTextArgument(String argumentName, Consumer<ArgumentBuilder> action) {
        return addArgument(argumentName, "Text", action);
    }

    /**
     * Adds a new enum argument with additional configuration.
     *
     * @param type   the class of the enum type.
     * @param action the consumer for additional configuration.
     * @return the builder instance.
     */
    default T addEnumArgument(Class<? extends Enum> type, Consumer<ArgumentBuilder> action) {
        return addArgument(type.getSimpleName(), "Enum", argumentBuilder -> {
            argumentBuilder.withProperty(argumentProperties -> {
                argumentProperties.properties().put("enum-type", type);
            });
            action.accept(argumentBuilder);
        });
    }

    /**
     * Adds a new enum argument.
     *
     * @param type the class of the enum type.
     * @return the builder instance.
     */
    default T addEnumArgument(Class<? extends Enum> type) {
        return addEnumArgument(type, x -> {
        });
    }

    /**
     * Adds a new enum argument with a specified name and type.
     *
     * @param argumentName the name of the argument.
     * @param type         the class of the enum type.
     * @return the builder instance.
     */
    default T addEnumArgument(String argumentName, Class<? extends Enum> type) {
        return addEnumArgument(type, x -> x.withName(argumentName));
    }

    /**
     * Adds a new boolean argument.
     *
     * @param argumentName the name of the argument.
     * @return the builder instance.
     */
    default T addBoolArgument(String argumentName) {
        return addArgument(argumentName, "Bool");
    }

    /**
     * Adds a new boolean argument with additional configuration.
     *
     * @param argumentName the name of the argument.
     * @param action       the consumer for additional configuration.
     * @return the builder instance.
     */
    default T addBoolArgument(String argumentName, Consumer<ArgumentBuilder> action) {
        return addArgument(argumentName, "Bool", action);
    }

    /**
     * Adds a new player argument.
     *
     * @param argumentName the name of the argument.
     * @return the builder instance.
     */
    default T addPlayerArgument(String argumentName) {
        return addArgument(argumentName, "Player");
    }

    /**
     * Adds a new player argument with additional configuration.
     *
     * @param argumentName the name of the argument.
     * @param action       the consumer for additional configuration.
     * @return the builder instance.
     */
    default T addPlayerArgument(String argumentName, Consumer<ArgumentBuilder> action) {
        return addArgument(argumentName, "Player", action);
    }

    /**
     * Adds a new location argument with a default name of "location".
     *
     * @return the builder instance.
     */
    default T addLocationArgument() {
        return addArgument("location", "Location");
    }

    /**
     * Adds a new location argument with a specified name.
     *
     * @param name the name of the argument.
     * @return the builder instance.
     */
    default T addLocationArgument(String name) {
        return addArgument(name, "Location");
    }

    /**
     * Adds a new entity argument using the EntityType enum.
     *
     * @return the builder instance.
     */
    default T addEntityArgument() {
        return addEnumArgument(EntityType.class);
    }

    /**
     * Adds a new sound argument using the Sound enum.
     *
     * @return the builder instance.
     */
    default T addSoundArgument() {
        return addEnumArgument(Sound.class);
    }

    /**
     * Adds a new color argument using the ChatColor enum.
     *
     * @return the builder instance.
     */
    default T addColorArgument() {
        return addEnumArgument(ChatColor.class);
    }

    /**
     * Adds a new particle argument using the Particle enum.
     *
     * @return the builder instance.
     */
    default T addParticleArgument() {
        return addEnumArgument(Particle.class);
    }
}