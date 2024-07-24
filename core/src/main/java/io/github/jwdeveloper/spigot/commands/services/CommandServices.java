package io.github.jwdeveloper.spigot.commands.services;

import io.github.jwdeveloper.dependance.api.DependanceContainer;
import io.github.jwdeveloper.spigot.commands.Command;
import io.github.jwdeveloper.spigot.commands.CommandsRegistry;
import io.github.jwdeveloper.spigot.commands.data.ActionResult;
import io.github.jwdeveloper.spigot.commands.data.CommandTarget;
import io.github.jwdeveloper.spigot.commands.data.events.CommandEvent;
import lombok.Getter;
import lombok.experimental.Accessors;
import org.bukkit.command.CommandSender;

import java.util.Arrays;
import java.util.List;


@Accessors(fluent = true)
@Getter
public class CommandServices {

    private final CommandsRegistry registry;
    private final ExpressionService expressionService;
    private final DependanceContainer container;
    private final EventsService eventsService;

    public CommandServices(
                           CommandsRegistry registry,
                           DependanceContainer container,
                           ExpressionService expressionService,
                           EventsService eventsService) {
        this.eventsService = eventsService;
        this.registry = registry;
        this.container = container;
        this.expressionService = expressionService;
    }

    /**
     * @param command          target command
     * @param sender           sender
     * @param commandLabel     ?
     * @param commandArguments arguments that belongs to target command
     * @return result determining if command was successful
     */
    public ActionResult<CommandEvent> execute(Command command,
                                              CommandSender sender,
                                              String commandLabel,
                                              String[] commandArguments) {
        var expressionResult = expressionService.parseArguments(command, sender, commandArguments);
        if (expressionResult.isFailed()) {
            return expressionResult.cast();
        }

        var event = new CommandEvent(
                sender,
                commandArguments,
                expressionResult.getValue(),
                container,
                command);
        return eventsService.invoke(command, event);
    }

    public List<String> executeTab(Command command,
                                   CommandSender sender,
                                   String alias,
                                   String[] commandsArgs,
                                   String[] allArgs) {
        var arguments = command.arguments();
        var children = command.children();

        if (arguments.isEmpty()) {
            return children.stream()
                    .filter(e -> !e.properties().hideFromTabDisplay())
                    .map(Command::name)
                    .toList();
        }

        var argumentIndex = commandsArgs.length - 1;
        if (arguments.size() - 1 < argumentIndex) {
            return List.of();
        }

        var args = commandsArgs[argumentIndex];
        var argument = arguments.get(argumentIndex);
        return switch (argument.displayMode()) {
            case NAME -> List.of(argument.name());
            case TYPE -> List.of("<" + argument.type().name().toLowerCase() + ">");
            case SUGGESTIONS -> argument.suggestions().apply(args);
        };
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
}
