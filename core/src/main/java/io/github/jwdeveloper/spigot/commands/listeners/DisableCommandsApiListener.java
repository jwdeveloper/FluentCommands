package io.github.jwdeveloper.spigot.commands.listeners;

import io.github.jwdeveloper.spigot.commands.CommandsApi;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.plugin.Plugin;

public class DisableCommandsApiListener implements Listener {

    private final Plugin plugin;
    private final CommandsApi commandsApi;

    public DisableCommandsApiListener(Plugin plugin, CommandsApi commandsApi) {
        this.plugin = plugin;
        this.commandsApi = commandsApi;
    }

    @EventHandler
    public void onDisable(PluginDisableEvent event) {
        if (!event.getPlugin().equals(plugin)) {
            return;
        }
        commandsApi.removeAll();
    }
}
