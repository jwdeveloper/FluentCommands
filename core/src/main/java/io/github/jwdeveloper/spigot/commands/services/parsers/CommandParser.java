package io.github.jwdeveloper.spigot.commands.services.parsers;

import io.github.jwdeveloper.spigot.commands.Command;
import io.github.jwdeveloper.spigot.commands.data.ActionResult;
import io.github.jwdeveloper.spigot.commands.data.expressions.CommandNode;
import org.bukkit.command.CommandSender;

public class CommandParser {


    public ActionResult<CommandNode> parseCommand(Command command, CommandSender sender, String[] args) {

        var result = new CommandNode();
        result.setCommand(command);
        result.setRaw(args);

        var argumentParser = createParser(command, sender, args);
        var arguments = command.arguments();
        var maxIndex = Math.max(arguments.size(), args.length);
        for (var i = 0; i < maxIndex; i++) {
            var arg = args.length > i ? args[i] : "";
            var argument = arguments.size() > i ? arguments.get(i) : null;

            var argumentResult = argumentParser.parseArgument(argument, arg, i);
            if (argumentResult.isFailed()) {
                return argumentResult.cast();
            }
            result.getArguments().add(argumentResult.getValue());
        }
        return ActionResult.success(result);
    }

    private ArgumentParser createParser(Command command, CommandSender sender, String[] args) {
        return new ArgumentParser(command, sender, args);
    }
}
