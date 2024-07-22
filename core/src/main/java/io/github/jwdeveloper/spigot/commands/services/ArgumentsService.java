package io.github.jwdeveloper.spigot.commands.services;

import io.github.jwdeveloper.spigot.commands.Command;
import io.github.jwdeveloper.spigot.commands.data.ActionResult;
import io.github.jwdeveloper.spigot.commands.data.argumetns.ArgumentsResult;
import io.github.jwdeveloper.spigot.commands.data.argumetns.ArgumentProperties;
import io.github.jwdeveloper.spigot.commands.data.argumetns.parsing.ArgumentEvent;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

public class ArgumentsService {
    public ActionResult<ArgumentsResult> parseArguments(
            Command command,
            CommandSender sender,
            String[] args,
            List<ArgumentProperties> arguments) {

        var parsedArguments = new ArrayList<>();
        for (int i = 0; i < args.length; i++) {
            var arg = args[i];
            if(arguments.size() < args.length)
            {
                parsedArguments.add(arg);
                continue;
            }

            var argument = arguments.get(i);
            var event = new ArgumentEvent();
            event.previousValue(arg);
            event.argument(argument);
            event.sender(sender);
            event.args(args);
            event.arg(arg);
            event.command(command);
            event.index(i);

            Object output = null;
            for (var parser : argument.parsers()) {
                try {
                    var result = parser.parse(event);
                    if (result.isFailed()) {
                        return ActionResult.failed(result.getMessage());
                    }
                    output = result.getObject();
                    event.previousValue(output);
                } catch (Exception e) {
                    return ActionResult.failed("Parsing error " + e.getMessage());
                }
            }
            if (output == null) {
                return ActionResult.failed("Parsed value is null");
            }

            parsedArguments.add(output);
        }

        var result = new ArgumentsResult(parsedArguments);
        return ActionResult.success(result);
    }

}
