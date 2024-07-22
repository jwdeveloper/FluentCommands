package io.github.jwdeveloper.spigot.commands.services;

import io.github.jwdeveloper.dependance.api.DependanceContainer;
import io.github.jwdeveloper.spigot.commands.Command;
import io.github.jwdeveloper.spigot.commands.data.ActionResult;
import io.github.jwdeveloper.spigot.commands.data.events.CommandEvent;
import org.bukkit.command.CommandSender;

import java.util.List;

public class ExecuteService {

    private final ValidationService validationService;
    private final MessagesService messagesService;
    private final ArgumentsService argumentsService;
    private final EventsService eventsService;
    private final DependanceContainer container;

    public ExecuteService(ValidationService validationService,
                          MessagesService messagesService,
                          ArgumentsService argumentsService,
                          EventsService eventsService,
                          DependanceContainer container) {
        this.validationService = validationService;
        this.messagesService = messagesService;
        this.argumentsService = argumentsService;
        this.eventsService = eventsService;
        this.container = container;
    }

    /**
     * @param command          target command
     * @param sender           sender
     * @param commandLabel     ?
     * @param commandArguments arguments that belongs to target command
     * @param allArguments     all arguments that was sent by sender
     * @return result determining if command was successful
     */
    public ActionResult<CommandEvent> execute(Command command,
                                              CommandSender sender,
                                              String commandLabel,
                                              String[] commandArguments,
                                              String[] allArguments) {
        if (!command.properties().active()) {
            return ActionResult.failed(messagesService.inactiveCommand(command.name()));
        }

        var accessResult = validationService.isSenderEnabled(sender, command.properties().excludedSenders());
        if (accessResult.isFailed()) {
            return ActionResult.failed(messagesService.noSenderAccess(accessResult.getMessage()));
        }

        var permissionResult = validationService.hasSenderPermissions(sender, command.properties().permissions());
        if (permissionResult.isFailed()) {
            return ActionResult.failed(messagesService.insufficientPermissions(permissionResult.getMessage()));
        }

        var argumentsResult = argumentsService.parseArguments(command, sender, commandArguments, command.arguments());
        if (argumentsResult.isFailed()) {
            return ActionResult.failed(messagesService.invalidArgument(argumentsResult.getMessage()));
        }

        var event = new CommandEvent(
                sender,
                commandArguments,
                allArguments,
                argumentsResult.getObject(),
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
}
