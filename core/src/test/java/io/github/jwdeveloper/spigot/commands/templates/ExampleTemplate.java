package io.github.jwdeveloper.spigot.commands.templates;


import io.github.jwdeveloper.spigot.commands.annotations.FCommand;
import io.github.jwdeveloper.spigot.commands.annotations.FCommandBuilder;
import io.github.jwdeveloper.spigot.commands.builder.CommandBuilder;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

@FCommand(
        pattern = "/spawn <!name:text> <x:number?1.0> <color:Color?WHITE>",
        permission = "jw.github.join!",
        description = "this is pretty simple command!",
        shortDescription = "short command")
public class ExampleTemplate {
    @FCommandBuilder(name = "spawn")
    public void build(CommandBuilder builder) {

    }

    @FCommand(name = "spawn")
    public String onDefault(Player sender, String name, double coins, ChatColor entityType) {
        return "Default command invoked! " + sender + " " + name + " " + coins + " " + entityType.name();
    }
}
