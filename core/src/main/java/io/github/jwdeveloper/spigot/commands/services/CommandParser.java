package io.github.jwdeveloper.spigot.commands.services;

import io.github.jwdeveloper.spigot.commands.Command;
import io.github.jwdeveloper.spigot.commands.data.ActionResult;
import io.github.jwdeveloper.spigot.commands.data.events.ArgumentEvent;
import io.github.jwdeveloper.spigot.commands.data.expressions.ArgumentNode;
import io.github.jwdeveloper.spigot.commands.data.expressions.CommandNode;
import io.github.jwdeveloper.spigot.commands.iterators.ArgumentIterator;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;

public class CommandParser {


    public ActionResult<CommandNode> parseCommand(Command command, CommandSender sender, String[] args) {

        var argumentsOutput = new ArrayList<ArgumentNode>();

        var iterator = new ArgumentIterator(args);
        var event = new ArgumentEvent();
        event.command(command);
        event.iterator(iterator);
        event.sender(sender);
        for (var argument : command.arguments()) {
            event.argument(argument);
            if (!iterator.hasNext()) {
                if (argument.required()) {
                    return ActionResult.failed("This argument is required but value is not provided!");
                }
                iterator.append(argument.defaultValue());
            }

            Object output = null;
            var parser = argument.parser();
            if (parser != null) {
                var parseResult = parser.onParse(event);
                if (parseResult.isFailed()) {
                    return ActionResult.failed(parseResult.getMessage());
                }
                output = parseResult.getValue();
            }

            if (output == null) {
                return ActionResult.failed("Argument " + argument.name() + " parsing value is Null!");
            }

            argumentsOutput.add(new ArgumentNode(argument, output));
        }

        var result = new CommandNode();
        result.setRaw(args);
        result.setCommand(command);
        result.setArguments(argumentsOutput);
        return ActionResult.success(result);
    }


}
