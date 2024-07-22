package io.github.jwdeveloper.spigot.commands.functions;

import io.github.jwdeveloper.spigot.commands.Command;
import io.github.jwdeveloper.spigot.commands.data.events.CommandEvent;

@FunctionalInterface
public interface CommandEventAction<E extends CommandEvent<?>> {
    void execute(Command command, E event);
}
