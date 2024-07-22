package io.github.jwdeveloper.spigot.commands.data.events;

import io.github.jwdeveloper.dependance.api.DependanceContainer;
import io.github.jwdeveloper.spigot.commands.Command;
import io.github.jwdeveloper.spigot.commands.data.argumetns.ArgumentsResult;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;


@Getter
@Accessors(fluent = true)
public class CommandEvent<T extends CommandSender> {


    private T sender;
    private String[] arguments;
    private String[] allArguments;
    private ArgumentsResult argumentsResult;
    private DependanceContainer container;
    private Command command;
    @Setter
    private Object output;

    public CommandEvent(T sender,
                        String[] arguments,
                        String[] allArguments,
                        ArgumentsResult argumentsResult,
                        DependanceContainer container,
                        Command command) {
        this.sender = sender;
        this.arguments = arguments;
        this.allArguments = allArguments;
        this.argumentsResult = argumentsResult;
        this.container = container;
        this.command = command;
    }


    public int argumentCount() {
        return arguments.length;
    }

    public String argument(int argument) {
        if (argument > argumentCount() - 1) {
            return "";
        }
        return arguments[argument];
    }

    public <T> T argument(int argument, Class<T> type) {
        if (argument > argumentCount() - 1) {
            return null;
        }
        return (T) argumentsResult.get(argument);
    }

    public Integer getInt(int argument) {
        return argument(argument, Integer.TYPE);
    }

    public String getString(int argument) {
        return argument(argument, String.class);
    }

    public Float getFloat(int argument) {
        return argument(argument, Float.class);
    }

    public Double getDouble(int argument) {
        return argument(argument, Double.class);
    }

    public Player getPlayer(int argument) {
        return argument(argument, Player.class);
    }

    public Entity getEntity(int argument) {
        return argument(argument, Entity.class);
    }

    public Location getLocation(int argument) {
        return argument(argument, Location.class);
    }
}
