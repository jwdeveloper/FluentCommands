package io.github.jwdeveloper.spigot.commands.builder;

import io.github.jwdeveloper.spigot.commands.data.events.CommandEvent;
import io.github.jwdeveloper.spigot.commands.data.events.CommandValidationEvent;
import io.github.jwdeveloper.spigot.commands.functions.CommandEventAction;
import org.bukkit.command.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.function.Consumer;

/**
 * Interface for building command events in a Bukkit plugin.
 *
 * @param <T> The type of the builder implementing this interface.
 */
public interface CommandEventsBuilder<T> {

    /**
     * Registers an event action to be executed when a command is run by a ProxiedCommandSender.
     *
     * @param action The action to be executed.
     * @return The builder instance.
     */
    default T onProxyExecute(Consumer<CommandEvent<ProxiedCommandSender>> action) {
        return onEvent(ProxiedCommandSender.class, action);
    }

    /**
     * Registers an event action to be executed when a command is run by a RemoteConsoleCommandSender.
     *
     * @param action The action to be executed.
     * @return The builder instance.
     */
    default T onRemoteConsoleExecute(Consumer<CommandEvent<RemoteConsoleCommandSender>> action) {
        return onEvent(RemoteConsoleCommandSender.class, action);
    }

    /**
     * Registers an event action to be executed when a command is run by a BlockCommandSender.
     *
     * @param action The action to be executed.
     * @return The builder instance.
     */
    default T onBlockExecute(Consumer<CommandEvent<BlockCommandSender>> action) {
        return onEvent(BlockCommandSender.class, action);
    }

    /**
     * Registers an event action to be executed when a command is run by a Player.
     *
     * @param action The action to be executed.
     * @return The builder instance.
     */
    default T onPlayerExecute(Consumer<CommandEvent<Player>> action) {
        return onEvent(Player.class, action);
    }

    /**
     * Registers an event action to be executed when a command is run by a ConsoleCommandSender.
     *
     * @param action The action to be executed.
     * @return The builder instance.
     */
    default T onConsoleExecute(Consumer<CommandEvent<ConsoleCommandSender>> action) {
        return onEvent(ConsoleCommandSender.class, action);
    }

    /**
     * Registers an event action to be executed when a command is run by any CommandSender.
     *
     * @param action The action to be executed.
     * @return The builder instance.
     */
    default T onExecute(Consumer<CommandEvent<CommandSender>> action) {
        return onEvent(CommandSender.class, action);
    }

    /**
     * Registers a validation action to be executed when a command is validated.
     *
     * @param action The action to be executed.
     * @return The builder instance.
     */
    T onValidation(Consumer<CommandValidationEvent> action);

    /**
     * Registers a custom event action for a specific type of command sender.
     *
     * @param senderType The class of the command sender.
     * @param action     The action to be executed.
     * @return The builder instance.
     */
    <E extends CommandSender> T onEvent(Class<?> senderType, Consumer<CommandEvent<E>> action);
}
