package io.github.jwdeveloper.spigot.commands.data.events;

import io.github.jwdeveloper.spigot.commands.Command;
import io.github.jwdeveloper.spigot.commands.argumetns.ArgumentProperties;
import io.github.jwdeveloper.spigot.commands.iterators.ArgumentIterator;
import lombok.Data;
import lombok.experimental.Accessors;
import org.bukkit.command.CommandSender;

@Data
@Accessors(fluent = true)
public class ArgumentParseEvent
{
    private Command command;
    private ArgumentProperties argument;
    private ArgumentIterator iterator;
    private CommandSender sender;
}
