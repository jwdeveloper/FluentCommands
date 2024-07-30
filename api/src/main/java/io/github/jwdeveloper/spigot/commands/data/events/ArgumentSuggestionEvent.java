package io.github.jwdeveloper.spigot.commands.data.events;

import io.github.jwdeveloper.spigot.commands.Command;
import io.github.jwdeveloper.spigot.commands.argumetns.ArgumentProperties;
import lombok.Data;
import lombok.experimental.Accessors;
import org.bukkit.command.CommandSender;

@Data
@Accessors(fluent = true)
public class ArgumentSuggestionEvent {
    private Command command;
    private ArgumentProperties argument;
    private CommandSender sender;
    private String rawValue;
    private Object value;
}