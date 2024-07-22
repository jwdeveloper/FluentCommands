package io.github.jwdeveloper.spigot.commands;

import io.github.jwdeveloper.spigot.commands.data.CommandProperties;
import io.github.jwdeveloper.spigot.commands.data.argumetns.ArgumentProperties;
import org.bukkit.command.CommandSender;

import java.util.List;
import java.util.Optional;

public interface Command {
    CommandProperties properties();

    List<ArgumentProperties> arguments();

    List<Command> children();

    boolean execute(CommandSender sender, String commandLabel, String... arguments);

    List<String> tabComplete(CommandSender sender, String alias, String... args);

    Optional<Command> parent();
    boolean hasParent();

    String name();
}
