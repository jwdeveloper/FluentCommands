package io.github.jw.spigot.mc.tiktok.example;


import io.github.jwdeveloper.spigot.commands.Commands;
import io.github.jwdeveloper.spigot.commands.CommandsFramework;
import io.github.jwdeveloper.spigot.commands.data.SuggestionMode;
import org.bukkit.Material;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;

public final class Example extends JavaPlugin {

    public static enum Flowers {
        BIG, SMALL, MEDIUM, LEFT
    }

    @Override
    public void onEnable() {
        Commands commandsApi = CommandsFramework.enable(this);

        commandsApi.create(new PluginCommand())
                .register();

        commandsApi.argumentTypes()
                .create("BlockType")
                .onParse(event ->
                {
                    var material = Material.getMaterial(event.iterator().next());
                    if (!material.isBlock()) {
                        throw new RuntimeException("Provided value is not block!");
                    }
                    return material;
                })
                .onSuggestion(event ->
                {
                    var input = event.rawValue();
                    return Arrays.stream(Material.values())
                            .filter(Material::isBlock)
                            .filter(e -> e.name().contains(input))
                            .limit(10)
                            .map(Enum::name)
                            .toList();
                })
                .register();

        commandsApi.create("/test <radios:Number> <name:BlockType>")
                .onPlayerExecute(event ->
                {
                    var radious = event.getDouble(0);
                    var name = event.getString(1);
                    System.out.println("Hello world " + radious + " " + name);
                })
                .register();
    }
}
