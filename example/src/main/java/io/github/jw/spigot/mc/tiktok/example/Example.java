package io.github.jw.spigot.mc.tiktok.example;


import io.github.jwdeveloper.spigot.commands.Commands;
import io.github.jwdeveloper.spigot.commands.CommandsFramework;
import org.bukkit.plugin.java.JavaPlugin;

public final class Example extends JavaPlugin {

    public static enum Flowers {
        BIG, SMALL, MEDIUM, LEFT
    }

    @Override
    public void onEnable() {
        Commands commandsApi = CommandsFramework.enable(this);
        commandsApi.create(new PluginCommand());
        commandsApi.create("test")
                .addPlayerArgument("main-player")
                .addNumberArgument("x")
                .addNumberArgument("y")
                .addNumberArgument("z")
                .addEnumArgument(Flowers.class)
                .onExecute((command, event) ->
                {
                    var player = event.getPlayer(0);
                    var option = event.getString(1);
                    var arg = event.getString(2);
                    var flower = event.getEnum(3, Flowers.class);
                })
                .register();
    }
}
