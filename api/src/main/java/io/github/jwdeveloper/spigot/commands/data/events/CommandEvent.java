package io.github.jwdeveloper.spigot.commands.data.events;

import io.github.jwdeveloper.dependance.api.DependanceContainer;
import io.github.jwdeveloper.spigot.commands.data.argumetns.ArgumentsResult;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Accessors;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@AllArgsConstructor
@Getter
@Accessors(fluent = true)
public class CommandEvent<T extends CommandSender> {


    private T sender;
    private String[] arguments;
    private String[] allArguments;
    private ArgumentsResult argumentsResult;
    private DependanceContainer container;

    public int argumentCount() {
        return arguments.length;
    }

    public String argument(int index) {
        if (index > argumentCount() - 1) {
            return "";
        }
        return arguments[index];
    }

    public <T> T argument(int index, Class<T> type) {
        if (index > argumentCount() - 1) {
            return null;
        }
        return (T) argumentsResult.get(index);
    }

    public Integer argumentInt(int index) {
        return argument(index, Integer.TYPE);
    }

    public String argumentString(int index) {
        return argument(index, String.class);
    }

    public Float argumentFloat(int index) {
        return argument(index, Float.class);
    }

    public Double argumentDouble(int index) {
        return argument(index, Double.class);
    }

    public Player argumentPlayer(int index) {
        return argument(index, Player.class);
    }
}
