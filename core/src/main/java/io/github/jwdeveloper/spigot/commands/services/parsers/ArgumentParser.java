package io.github.jwdeveloper.spigot.commands.services.parsers;

import io.github.jwdeveloper.spigot.commands.Command;
import io.github.jwdeveloper.spigot.commands.data.ActionResult;
import io.github.jwdeveloper.spigot.commands.data.argumetns.ArgumentProperties;
import io.github.jwdeveloper.spigot.commands.data.argumetns.parsing.ArgumentEvent;
import io.github.jwdeveloper.spigot.commands.data.expressions.ArgumentNode;
import org.bukkit.command.CommandSender;

public class ArgumentParser {
    private final Command command;
    private final CommandSender sender;
    private final String[] args;

    public ArgumentParser(Command command, CommandSender sender, String[] args) {
        this.command = command;
        this.sender = sender;
        this.args = args;
    }

    public ActionResult<ArgumentNode> parseArgument(ArgumentProperties argument, String raw, int index) {
        var argumentNode = new ArgumentNode();
        argumentNode.setArgument(argument);
        argumentNode.setRawValue(raw);

        if (argument == null) {
            argumentNode.setValue(raw);
            return ActionResult.success(argumentNode);
        }

        if (raw.equals("~")) {
            argumentNode.setValue(argument.defaultValue());
            return ActionResult.success(argumentNode);
        }

        var event = new ArgumentEvent();
        event.previousValue(raw);
        event.argument(argument);
        event.sender(sender);
        event.args(args);
        event.arg(raw);
        event.command(command);
        event.index(index);

        Object output = null;
        for (var parser : argument.parsers()) {
            try {
                var result = parser.parse(event);
                if (result.isFailed()) {
                    return ActionResult.failed(result.getMessage());
                }
                output = result.getValue();
                event.previousValue(output);
            } catch (Exception e) {
                return ActionResult.failed("Parsing error " + e.getMessage());
            }
        }
        if (output == null) {
            return ActionResult.failed("Parsed value is null");
        }
        argumentNode.setValue(output);
        return ActionResult.success(argumentNode);
    }

}
