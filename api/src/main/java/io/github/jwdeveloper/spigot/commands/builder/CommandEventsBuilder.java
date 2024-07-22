package io.github.jwdeveloper.spigot.commands.builder;

import io.github.jwdeveloper.spigot.commands.data.events.CommandEvent;
import io.github.jwdeveloper.spigot.commands.functions.CommandEventAction;
import org.bukkit.command.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public interface CommandEventsBuilder<T> {

    default T onProxyExecute(CommandEventAction<CommandEvent<ProxiedCommandSender>> action) {
        return onEvent(ProxiedCommandSender.class, action);
    }

    default T onRemoteConsoleExecute(CommandEventAction<CommandEvent<RemoteConsoleCommandSender>> action) {
        return onEvent(RemoteConsoleCommandSender.class, action);
    }

    default T onBlockExecute(CommandEventAction<CommandEvent<BlockCommandSender>> action) {
        return onEvent(BlockCommandSender.class, action);
    }

    default T onPlayerExecute(CommandEventAction<CommandEvent<Player>> action) {
        return onEvent(Player.class, action);
    }

    default T onConsoleExecute(CommandEventAction<CommandEvent<ConsoleCommandSender>> action) {
        return onEvent(ConsoleCommandSender.class, action);
    }

    default T onExecute(CommandEventAction<CommandEvent<CommandSender>> action) {
        return onEvent(CommandSender.class, action);
    }

    T onEvent(Class<?> senderType, CommandEventAction<?> action);
}
