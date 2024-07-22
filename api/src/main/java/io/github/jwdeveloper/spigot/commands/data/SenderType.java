package io.github.jwdeveloper.spigot.commands.data;

import org.bukkit.command.BlockCommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.command.ProxiedCommandSender;
import org.bukkit.command.RemoteConsoleCommandSender;
import org.bukkit.entity.Player;

public enum SenderType {
    PLAYER(Player.class),
    CONSOLE(ConsoleCommandSender.class),
    PROXY(ProxiedCommandSender.class),
    BLOCK(BlockCommandSender.class),
    REMOTE_CONSOLE(RemoteConsoleCommandSender.class);

    private final Class<?> type;

    SenderType(Class<?> type) {
        this.type = type;
    }

    public Class<?> getType() {
        return type;
    }
}
