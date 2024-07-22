package io.github.jwdeveloper.spigot.commands.services;

import io.github.jwdeveloper.spigot.commands.Command;
import io.github.jwdeveloper.spigot.commands.CommandsRegistry;
import io.github.jwdeveloper.spigot.commands.data.CommandTarget;
import lombok.Getter;
import lombok.experimental.Accessors;

import java.util.Arrays;


@Accessors(fluent = true)
@Getter
public class CommandServices {

    private final ExecuteService executeService;
    private final CommandsRegistry registry;

    public CommandServices(ExecuteService invokeService, CommandsRegistry registry) {
        this.executeService = invokeService;
        this.registry = registry;
    }


    /**
     * Example: /say hello John
     * <p>
     * If main command is called say has subcommand hello
     * then hello should be output of the method
     * <p>
     * Looks for the Target invoked command based of the arguments
     * If no command was found then returns empty Optional
     * If child was found then returns child
     *
     * @param command main contain
     * @param args    list of the arguments
     * @return target command and arguments that belongs to it
     */

    public CommandTarget targetedCommand(Command command, String[] args) {
        if (args.length == 0 && command.children().isEmpty()) {
            return new CommandTarget(command, args);
        }

        //test <text> <text>
        //test tree -> tree is child
        var arg = args[0];
        if (command.hasChild(arg)) {
            var childOptional = command.child(arg);
            var child = childOptional.get();

            var subCommandArgs = Arrays.copyOfRange(args, 1, args.length);
            return targetedCommand(child, subCommandArgs);
        }

        var arguments = command.arguments();
        var argumentsSize = arguments.size();

        //test <text> <text>
        //test one
        if (args.length <= argumentsSize) {
            return new CommandTarget(command, args);
        }

        //test <text> <text>
        //test one two three -> three is child
        var childName = args[argumentsSize];
        if (command.hasChild(childName)) {
            var childOptional = command.child(childName);
            var child = childOptional.get();

            var subCommandArgs = Arrays.copyOfRange(args, argumentsSize + 1, args.length);
            return targetedCommand(child, subCommandArgs);
        }

        //test <text> <text>
        //test one two three -> three is not child
        return new CommandTarget(command, args);
    }

    public CommandTarget targetedCommandOld(Command command, String[] args) {
        if (args.length == 0 || command.children().isEmpty()) {
            return new CommandTarget(command, args);
        }
        var arguments = command.arguments();
        var subCommandIndex = arguments.size() + 1;

        if (subCommandIndex > args.length) {
            return new CommandTarget(command, args);
        }

        var subCommandName = args[subCommandIndex - 1];
        var subCommandOptional = command.children()
                .stream()
                .filter(c -> c.name().equalsIgnoreCase(subCommandName))
                .findFirst();

        if (subCommandOptional.isEmpty()) {
            return new CommandTarget(command, args);
        }
        var subCommandArgs = Arrays.copyOfRange(args, subCommandIndex, args.length);
        return targetedCommand(subCommandOptional.get(), subCommandArgs);
    }
}
