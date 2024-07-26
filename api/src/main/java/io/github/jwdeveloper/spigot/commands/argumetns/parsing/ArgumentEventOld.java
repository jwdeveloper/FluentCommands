package io.github.jwdeveloper.spigot.commands.argumetns.parsing;

import io.github.jwdeveloper.spigot.commands.Command;
import io.github.jwdeveloper.spigot.commands.argumetns.ArgumentProperties;
import lombok.Data;
import lombok.experimental.Accessors;
import org.bukkit.command.CommandSender;

@Data
@Accessors(fluent = true)
public class ArgumentEventOld {

    Object previousValue;
    CommandSender sender;
    Command command;
    ArgumentProperties argument;
    int index;
    String arg;
    String args[];

    public String nextArg() {
        if (index + 1 > args.length) {
            return "";
        }
        return args[index + 1];
    }

    public String previousArg() {
        if (index - 1 < args.length) {
            return "";
        }
        return args[index - 1];
    }
}
