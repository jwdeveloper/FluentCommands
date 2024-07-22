package io.github.jwdeveloper.spigot.commands.services;

import io.github.jwdeveloper.spigot.commands.data.ActionResult;
import io.github.jwdeveloper.spigot.commands.data.SenderType;
import org.bukkit.command.*;
import org.bukkit.entity.Player;

import java.util.List;

public class ValidationService {
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
}
