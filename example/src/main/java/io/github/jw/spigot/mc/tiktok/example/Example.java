package io.github.jw.spigot.mc.tiktok.example;


import io.github.jwdeveloper.spigot.commands.CommandsApi;
import io.github.jwdeveloper.spigot.commands.CommandsFramework;
import io.github.jwdeveloper.spigot.commands.data.argumetns.ArgumentType;
import org.bukkit.plugin.java.JavaPlugin;

public final class Example extends JavaPlugin {

    @Override
    public void onEnable() {
        CommandsApi commandsApi = CommandsFramework.enable(this);


        commandsApi.create("hello-world")
                //Properties
                .withPermissions("use.hello.world")
                .withDescription("This command say hello world to player")
                //Arguments.
              /*  .addTextArgument("one")
                .addNumberArgument("two")
                .addBoolArgument("three")*/
                //SubCommands
                //Events
                .addSubCommand(commandBuilder ->
                {
                    commandBuilder.withName("sub");
                    commandBuilder.onPlayerExecute((command, event) ->
                    {
                        System.out.println("Hello from sub command");
                    });
                })
                .onPlayerExecute((command, event) ->
                {
                    event.sender().sendMessage(event.arguments().length + "");
                })
                .buildAndRegister();
    }
}
