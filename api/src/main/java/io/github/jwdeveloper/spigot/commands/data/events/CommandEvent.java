package io.github.jwdeveloper.spigot.commands.data.events;

import io.github.jwdeveloper.dependance.api.DependanceContainer;
import io.github.jwdeveloper.spigot.commands.Command;
import io.github.jwdeveloper.spigot.commands.data.expressions.CommandExpression;
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


    private final T sender;
    private final Command command;
    private final CommandExpression expression;
    private final DependanceContainer container;

    @Setter
    private Object output;


    public CommandEvent(T sender,
                        CommandExpression argumentsResult,
                        DependanceContainer container,
                        Command command) {
        this.sender = sender;
        this.expression = argumentsResult;
        this.container = container;
        this.command = command;
    }


    public int argumentCount() {
        return expression.invokedCommand().getArguments().size();
    }


    public <T> T getArgument(int argument, Class<T> type) {
        if (argument > argumentCount() - 1) {
            return null;
        }
        return (T) expression.invokedCommand().getArgument(argument).getValue();
    }

    public Integer getInt(int argument) {
        return getArgument(argument, Integer.TYPE);
    }

    public String getString(int argument) {
        return getArgument(argument, String.class);
    }

    public Float getFloat(int argument) {
        return getArgument(argument, Float.class);
    }

    public Double getDouble(int argument) {
        return getArgument(argument, Double.class);
    }

    public <T extends Enum<?>> T getEnum(int argument, Class<? extends T> type) {
        return getArgument(argument, type);
    }

    public Player getPlayer(int argument) {
        return getArgument(argument, Player.class);
    }

    public Entity getEntity(int argument) {
        return getArgument(argument, Entity.class);
    }

    public Location getLocation(int argument) {
        return getArgument(argument, Location.class);
    }


}
