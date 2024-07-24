package io.github.jwdeveloper.spigot.commands.services;

import io.github.jwdeveloper.spigot.commands.Command;
import io.github.jwdeveloper.spigot.commands.data.ActionResult;
import io.github.jwdeveloper.spigot.commands.data.expressions.CommandExpression;
import io.github.jwdeveloper.spigot.commands.services.parsers.CommandParser;
import org.bukkit.command.CommandSender;


import java.util.Arrays;

public class ExpressionService {

    private static final String ignoreSymbol = "~";
    private final ValidationService validationService;
    private final CommandParser parser;

    public ExpressionService(ValidationService validationService, CommandParser commandParser) {
        this.validationService = validationService;
        this.parser = commandParser;
    }

    public ActionResult<CommandExpression> parseArguments(
            Command command,
            CommandSender sender,
            String[] args) {

        var expression = new CommandExpression();
        expression.setRawValue(args);
        var commandStartIndex = 0;
        for (var index = 0; index < args.length; index++) {
            var arg = args[index];
            if (arg.equals(ignoreSymbol)) {
                continue;
            }
            if (!command.hasChild(arg)) {
                if (index != args.length - 1) {
                    continue;
                }
                index = args.length;
            }

            var commandArgs = Arrays.copyOfRange(args, commandStartIndex, index);
            var validationCheck = validationService.validateCommand(command, sender, commandArgs);
            if (validationCheck.isFailed()) {
                return validationCheck.cast();
            }

            var result = parser.parseCommand(command, sender, commandArgs);
            if (result.isFailed()) {
                return result.cast();
            }
            var parsedValue = result.getValue();
            expression.getCommandNodes().add(parsedValue);

            if (index < args.length - 1) {
                command = command.child(arg).get();
            }
            commandStartIndex = index + 1;
        }
        return ActionResult.success(expression);
    }
}
