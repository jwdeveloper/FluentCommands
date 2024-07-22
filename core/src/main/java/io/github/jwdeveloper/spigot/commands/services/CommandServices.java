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
     * Looks for the Target invoked command based of the arguments
     * If no command was found then returns empty Optional
     * If child was found then returns child
     *
     * @param command main contain
     * @param args    list of the arguments
     * @return target command and arguments that belongs to it
     */
    public CommandTarget targetedCommand(Command command, String[] args) {
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
