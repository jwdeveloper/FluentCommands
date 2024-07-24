package io.github.jw.spigot.mc.tiktok.example;

import io.github.jwdeveloper.spigot.commands.annotations.FArgument;
import io.github.jwdeveloper.spigot.commands.annotations.FCommand;
import org.bukkit.entity.Player;


@FCommand(pattern = "/join <name:text> <age:name>")
public class PluginCommand {

    @FCommand
    @FArgument(name = "name")
    public void builder(Player player, String name, int age) {

    }

}
