package io.github.jwdeveloper.spigot.commands.builder;

import io.github.jwdeveloper.spigot.commands.data.events.CommandEvent;
import io.github.jwdeveloper.spigot.commands.data.events.PermissionCommandEvent;
import io.github.jwdeveloper.spigot.commands.functions.CommandEventAction;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public interface CommandEventsBuilder<T> {

    default T onPlayerExecute(CommandEventAction<CommandEvent<Player>> action) {
        return onEvent(action);
    }

    default T onServerExecute(CommandEventAction<CommandEvent<ConsoleCommandSender>> action) {
        return onEvent(action);
    }

    default T onExecute(CommandEventAction<CommandEvent<CommandSender>> action)
    {
        return onEvent(action);
    }

    default T onPermissions(CommandEventAction<PermissionCommandEvent<Player>> action){
        return onEvent(action);
    }

    T onEvent(CommandEventAction<?> action);
}
