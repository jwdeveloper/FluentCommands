package io.github.jw.spigot.mc.tiktok.example;


import io.github.jwdeveloper.spigot.commands.CommandsApi;
import io.github.jwdeveloper.spigot.commands.CommandsFramework;
import org.bukkit.plugin.java.JavaPlugin;

public final class Example extends JavaPlugin {

    public static enum Flowers {
        BIG, SMALL, MEDIUM, LEFT
    }

    @Override
    public void onEnable() {
        CommandsApi commandsApi = CommandsFramework.enable(this);
        commandsApi.create(new PluginCommand());
        commandsApi.create("test")
                .addPlayerArgument("main-player")
                .addTextArgument("option", argumentBuilder ->
                {
                    argumentBuilder.withSuggestions("Kick", "Join", "Move");
                })
                .addNumberArgument("x", argumentBuilder ->
                {
                    argumentBuilder.withSuggestions("0.1", "x", "copy");
                })
                .addNumberArgument("y")
                .addNumberArgument("z")
                .addTextArgument("arg")
                .addEnumArgument(Flowers.class)
                .addSubCommand("give-item", commandBuilder -> {
                })
                .addSubCommand("give-mana", commandBuilder -> {
                })
                .onExecute((command, event) ->
                {
                    var player = event.getPlayer(0);
                    var option = event.getString(1);
                    var arg = event.getString(2);
                    var flower = event.getEnum(3, Flowers.class);
                })
                .buildAndRegister();
    }
}
