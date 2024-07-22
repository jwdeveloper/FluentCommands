package io.github.jwdeveloper.spigot.commands.services;

import io.github.jwdeveloper.spigot.commands.Command;
import io.github.jwdeveloper.spigot.commands.data.ActionResult;
import io.github.jwdeveloper.spigot.commands.data.events.CommandEvent;
import org.bukkit.command.CommandSender;

import java.util.List;

public class ExecuteService {

    private final PermissionsService permissionsService;
    private final MessagesService messagesService;
    private final ArgumentsService argumentsService;
    private final EventsService eventsService;

    public ExecuteService(PermissionsService permissionsService,
                          MessagesService messagesService,
                          ArgumentsService argumentsService,
                          EventsService eventsService) {
        this.permissionsService = permissionsService;
        this.messagesService = messagesService;
        this.argumentsService = argumentsService;
        this.eventsService = eventsService;
    }

    /**
     * @param command          target command
     * @param sender           sender
     * @param commandLabel     ?
     * @param commandArguments arguments that belongs to target command
     * @param allArguments     all arguments that was sent by sender
     * @return result determining if command was successful
     */
    public ActionResult<Command> execute(Command command,
                                         CommandSender sender,
                                         String commandLabel,
                                         String[] commandArguments,
                                         String[] allArguments) {
        if (!command.properties().active()) {
            return ActionResult.failed(command, messagesService.inactiveCommand(command.name()));
        }

     /*   if (!commandService.hasSenderAccess(sender, command.properties().getCommandAccesses())) {
            return ActionResult.failed(command,messagesService.noAccess(sender));
        }*/

        var permissionResult = permissionsService.hasSenderPermissions(sender, command.properties().permissions());
        if (permissionResult.isFailed()) {
            return ActionResult.failed(command, messagesService.insufficientPermissions(permissionResult.getMessage()));
        }

        var argumentsResult = argumentsService.parseArguments(sender, commandArguments, command.arguments());
        if (argumentsResult.isFailed()) {
            return ActionResult.failed(command, messagesService.invalidArgument(argumentsResult.getMessage()));
        }

        var event = new CommandEvent(sender, commandArguments, allArguments, argumentsResult.getObject());
        var eventResult = eventsService.invoke(event);
        return ActionResult.cast(eventResult, command);
    }



    public ActionResult<List<String>> executeTab(Command command, CommandSender sender, String alias, String[] arguments) {
    }
}
