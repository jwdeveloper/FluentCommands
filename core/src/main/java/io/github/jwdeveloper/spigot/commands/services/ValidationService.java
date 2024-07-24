package io.github.jwdeveloper.spigot.commands.services;

import io.github.jwdeveloper.spigot.commands.Command;
import io.github.jwdeveloper.spigot.commands.data.ActionResult;
import io.github.jwdeveloper.spigot.commands.data.SenderType;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

import java.util.List;

public class ValidationService {

    private final MessagesService messagesService;

    public ValidationService(MessagesService messagesService) {
        this.messagesService = messagesService;
    }

    public ActionResult<Command> validateCommand(Command command, CommandSender sender, String[] args) {
        var argumentsCheck = checkRequiredArguments(command, args);
        if (argumentsCheck.isFailed()) {
            return ActionResult.failed(argumentsCheck.getMessage());
        }

        if (!command.properties().active()) {
            return ActionResult.failed(messagesService.inactiveCommand(command.name()));
        }
        var accessResult = isSenderEnabled(sender, command.properties().excludedSenders());
        if (accessResult.isFailed()) {
            return ActionResult.failed(messagesService.noSenderAccess(accessResult.getMessage()));
        }

        var permissionResult = hasSenderPermissions(sender, command.properties().permissions());
        if (permissionResult.isFailed()) {
            return ActionResult.failed(messagesService.insufficientPermissions(permissionResult.getMessage()));
        }
        return ActionResult.success(command);
    }

    public ActionResult<CommandSender> hasSenderPermissions(CommandSender sender, List<String> permissions) {
        if (!(sender instanceof Player player)) {
            return ActionResult.success();
        }

        for (String permission : permissions) {
            if (!player.hasPermission(permission)) {
                return ActionResult.failed(sender, permission);
            }
        }

        return ActionResult.success(sender);
    }

    public ActionResult<CommandSender> isSenderEnabled(CommandSender sender, List<SenderType> senderTypes) {

        for (var accessType : senderTypes) {
            var isDisabled = switch (accessType) {
                case PLAYER -> sender instanceof Player;
                case CONSOLE -> sender instanceof ConsoleCommandSender;
                case PROXY -> sender instanceof ProxiedCommandSender;
                case BLOCK -> sender instanceof BlockCommandSender;
                case REMOTE_CONSOLE -> sender instanceof RemoteConsoleCommandSender;
            };
            if (isDisabled) {
                return ActionResult.failed(accessType.name());
            }
        }
        return ActionResult.success(sender);
    }

    public ActionResult<String> checkRequiredArguments(Command command, String[] args) {
        var arguments = command.arguments();
        var inputSize = args.length - 1;
        for (var index = 0; index < arguments.size(); index++) {
            var argument = arguments.get(index);
            if (argument.required() && index > inputSize) {
                return ActionResult.failed("Argument " + argument.name() + " is required!");
            }
        }
        return ActionResult.success();
    }
}
