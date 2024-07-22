package io.github.jwdeveloper.spigot.commands.services;

import io.github.jwdeveloper.spigot.commands.data.ActionResult;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class PermissionsService {
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
}
