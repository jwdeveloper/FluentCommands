package io.github.jw.spigot.mc.tiktok.example;
import io.github.jwdeveloper.spigot.commands.annotations.FCommand;

@FCommand(pattern = "/spawn <!name:text> <action[up,under,over]>")
public class PluginCommand {

    @FCommand
    public String builder(String name, String action)
    {
        return "sorry this command was performed badly!";
    }

}
