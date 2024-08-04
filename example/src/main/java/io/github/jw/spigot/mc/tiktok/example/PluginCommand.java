package io.github.jw.spigot.mc.tiktok.example;

import io.github.jwdeveloper.spigot.commands.CommandsFramework;
import io.github.jwdeveloper.spigot.commands.annotations.FCommand;
import io.github.jwdeveloper.spigot.commands.data.events.ArgumentParseEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.List;

public class PluginCommand
{

    static
    {
        CommandsFramework.api().create(new PluginCommand())
                .onPlayerExecute(playerCommandEvent ->
                {

                })
                .onProxyExecute(proxiedCommandSenderCommandEvent ->
                {

                })

                .register();
    }

    @FCommand(pattern = "/jump <playerName:text[playersNames()]> <height:number>")
    public void spawnCommand(Player sender, String playerName, double jumpHeight) {
        sender.sendMessage("Command activated!");

        var player = Bukkit.getPlayer(playerName);
        player.setVelocity(new Vector(0, jumpHeight, 0));
    }

    public List<String> playersNames() {
        return Bukkit.getOnlinePlayers().stream().map(HumanEntity::getName).toList();
    }
}
