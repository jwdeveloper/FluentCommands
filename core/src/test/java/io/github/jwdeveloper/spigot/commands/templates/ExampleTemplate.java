package io.github.jwdeveloper.spigot.commands.templates;


import io.github.jwdeveloper.spigot.commands.annotations.FCommand;
import io.github.jwdeveloper.spigot.commands.annotations.FCommandBuilder;
import io.github.jwdeveloper.spigot.commands.builder.CommandBuilder;
import org.bukkit.entity.Player;

@FCommand(pattern = "/test <!arg1:text> <arg2:bool> <arg3:number>")
public class ExampleTemplate {

    @FCommandBuilder
    public void build(CommandBuilder builder) {
        System.out.println("Hello from the builder");
    }

    @FCommandBuilder(name = "sub1")
    public void build2(CommandBuilder builder) {
        System.out.println(builder.properties().name());
    }

    @FCommand
    public void onDefault(Player sender, String[] arg, String arg1, boolean arg2, float arg3) {
        System.out.println("Default command invoked! " + sender + " " + arg1 + " " + arg2 + " " + arg3);
    }

    @FCommand(name = "sub1")
    public void onSubCommand(Player sender) {
        System.out.println("sub command invoked!");
    }
}
