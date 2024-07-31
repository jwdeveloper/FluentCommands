package io.github.jw.spigot.mc.tiktok.example;


import io.github.jwdeveloper.spigot.commands.Commands;
import io.github.jwdeveloper.spigot.commands.CommandsFramework;
import io.github.jwdeveloper.spigot.commands.data.DisplayAttribute;
import org.bukkit.Material;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;
import java.util.List;

public final class Example extends JavaPlugin {

    @Override
    public void onEnable() {
        Commands commands = CommandsFramework.enable(this);


        commands.create("/hello <name:Text(dn,dt)> <age:number(dn)> <job:Text[Miner, Fisherman, Farmer](ds)>")
                .onPlayerExecute(event ->
                {
                    event.sender().sendMessage("You called the hello command");

                    var name = event.getString("name");
                    var age = event.getNumber("age");
                    var job = event.getString("job");

                    event.sender().sendMessage("Name: " + name + " Age: " + age + " Job: " + job);
                }).register();


        commands.argumentTypes()
                .create("siema")
                .onParse(event -> 1)
                .onSuggestion(argumentSuggestionEvent -> List.of("Heloo", "world"))
                .register();

        commands.create(new PluginCommand())
                .register();

        commands.argumentTypes()
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

        commands.create("/test <radios:Number(v:$113123123$,dn)> <name:Text> <age:Number> <gender:Text>")
                .addArgument("gender", argumentBuilder ->
                {
                    argumentBuilder.withDisplayAttribute(DisplayAttribute.NAME);
                })
                .addArgument("age", argumentBuilder ->
                {
                    argumentBuilder.withDisplayAttribute(DisplayAttribute.NAME);
                })
                .onPlayerExecute(event ->
                {
                    var radious = event.getNumber(0);
                    var name = event.getString(1);
                    System.out.println(name);
                    event.sender().sendMessage("Command invoked! " + radious + " " + name);
                })
                .register();
    }
}
