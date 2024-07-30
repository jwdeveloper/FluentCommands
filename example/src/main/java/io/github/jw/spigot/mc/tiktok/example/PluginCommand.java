package io.github.jw.spigot.mc.tiktok.example;

import io.github.jwdeveloper.spigot.commands.annotations.FCommand;
import io.github.jwdeveloper.spigot.commands.annotations.FCommandArgument;
import io.github.jwdeveloper.spigot.commands.data.events.ArgumentParseEvent;
import io.github.jwdeveloper.spigot.commands.data.events.ArgumentSuggestionEvent;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import java.util.List;

public class PluginCommand {
    @FCommand(pattern = "/dupa <name:text[handleSuggestion]> <age:number[handleSuggestion]>")
    public void spawnCommand(Player player, String name, double age) {

        player.sendMessage("It works!");
    }

    @FCommand(name = "/jump")
    public void jumpCommand(Player player, double height) {


        player.sendMessage("Jump works!");
        var velocity = player.getVelocity().setX(0).setY(1).setZ(0).multiply(height);
        player.setVelocity(velocity);
    }


    public List<String> handleSuggestion(ArgumentSuggestionEvent event) {
        return List.of("asd", "asd", "asd");
    }

    public Object handleParse(ArgumentParseEvent event) {
        return 0;
    }

}
