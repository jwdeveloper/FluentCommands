package io.github.jw.spigot.mc.tiktok.example;


import io.github.jwdeveloper.spigot.commands.CommandsApi;
import io.github.jwdeveloper.spigot.commands.CommandsFramework;
import io.github.jwdeveloper.spigot.commands.data.SenderType;
import org.bukkit.plugin.java.JavaPlugin;

public final class Example extends JavaPlugin {

    @Override
    public void onEnable() {
        CommandsApi commandsApi = CommandsFramework.enable(this);

        commandsApi.create("hello-world")
                //Properties
                .withPermissions("use.hello.world")
                .withDescription("This command say hello world to player")

                //Arguments
                .addTextArgument("name")
                .addNumberArgument("number-of-people")
                .addPlayerArgument("main-player")

                //SubCommands
                .addChildren(commandBuilder ->
                {
                    commandBuilder.withName("child");
                    commandBuilder.onPlayerExecute((command, event) ->
                    {
                        event.sender().sendMessage("Hello from the sub command");
                    });
                })

                //Events
                .onPlayerExecute((command, event) ->
                {
                    var sender = event.sender();
                    var player = event.argumentPlayer(0);
                    var size = event.argumentDouble(1);
                })
                .onBlockExecute((command, event) ->
                {

                })
                .onConsoleExecute((command, event) ->
                {
                    event.sender().sendMessage("This command can be only use by players!");
                })
                .buildAndRegister();
    }
}
