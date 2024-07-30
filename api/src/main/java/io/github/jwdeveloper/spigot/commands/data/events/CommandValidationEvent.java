package io.github.jwdeveloper.spigot.commands.data.events;

import io.github.jwdeveloper.spigot.commands.Command;
import io.github.jwdeveloper.spigot.commands.data.ActionResult;
import org.bukkit.command.CommandSender;

public interface CommandValidationEvent {
    ActionResult<Command> validate(Command command, CommandSender sender, String... args);
}
