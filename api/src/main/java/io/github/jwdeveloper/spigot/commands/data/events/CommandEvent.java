package io.github.jwdeveloper.spigot.commands.data.events;

import io.github.jwdeveloper.spigot.commands.Command;
import io.github.jwdeveloper.spigot.commands.data.expressions.CommandExpression;
import io.github.jwdeveloper.spigot.commands.exceptions.ArgumentException;
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

    @Setter
    private Object output;

    public CommandEvent(T sender,
                        CommandExpression argumentsResult,
                        Command command) {
        this.sender = sender;
        this.expression = argumentsResult;
        this.command = command;
    }

    public int argumentCount() {
        return expression.invokedCommand().getArguments().size();
    }


    public boolean hasArgument(int argumentIndex) {
        return argumentCount() > argumentIndex;
    }

    public boolean hasArgument(String argumentName) {
        var cmd = expression.invokedCommand();
        var arg = cmd.getArguments()
                .stream()
                .filter(e -> e.getArgument().name().equalsIgnoreCase(argumentName))
                .findFirst();

        return arg.isPresent();
    }

    public <T> T getArgument(int argumentIndex, Class<T> type) {
        if (argumentIndex > argumentCount() - 1) {
            throw new ArgumentException("Argument for index " + argumentIndex + " not exists!");
        }

        var value = expression.invokedCommand().getArgument(argumentIndex).getValue();
        if (!type.isAssignableFrom(value.getClass())) {
            throw new ArgumentException("Type mishmash: Argument has type: " + value.getClass().getSimpleName() + " but you are trying to get " + type.getSimpleName());
        }
        return (T) value;
    }

    public <T> T getArgument(String name, Class<T> type) {
        var cmd = expression.invokedCommand();
        var args = cmd.getArguments().stream().filter(e -> e.getArgument().name().equalsIgnoreCase(name)).findFirst();
        if (args.isEmpty()) {
            throw new ArgumentException("Argument with the name " + name + " not exists!");
        }
        var value = args.get().getValue();
        if (!type.isAssignableFrom(value.getClass())) {
            throw new ArgumentException("Type mishmash: Argument has type: " + value.getClass().getSimpleName() + " but you are trying to get " + type.getSimpleName());
        }

        return (T) value;
    }

    public String getString(String argumentName) {
        return getArgument(argumentName, String.class);
    }

    public String getString(int argument) {
        return getArgument(argument, String.class);
    }

    public Double getNumber(String argument) {
        return getArgument(argument, Double.class);
    }

    public Double getNumber(int argument) {
        return getArgument(argument, Double.class);
    }

    public <T extends Enum<?>> T getEnum(String argument, Class<? extends T> type) {
        return getArgument(argument, type);
    }

    public <T extends Enum<?>> T getEnum(int argument, Class<? extends T> type) {
        return getArgument(argument, type);
    }

    public Player getPlayer(String argument) {
        return getArgument(argument, Player.class);
    }

    public Player getPlayer(int argument) {
        return getArgument(argument, Player.class);
    }

    public Entity getEntity(String argument) {
        return getArgument(argument, Entity.class);
    }

    public Entity getEntity(int argument) {
        return getArgument(argument, Entity.class);
    }


    public Location getLocation(String argument) {
        return getArgument(argument, Location.class);
    }

    public Location getLocation(int argument) {
        return getArgument(argument, Location.class);
    }
}
