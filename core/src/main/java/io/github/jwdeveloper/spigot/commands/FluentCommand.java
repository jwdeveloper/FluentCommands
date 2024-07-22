package io.github.jwdeveloper.spigot.commands;

import io.github.jwdeveloper.spigot.commands.data.CommandProperties;
import io.github.jwdeveloper.spigot.commands.data.argumetns.ArgumentProperties;
import io.github.jwdeveloper.spigot.commands.services.CommandServices;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;

import java.util.List;
import java.util.Optional;

@Getter
@Accessors(fluent = true)
public class FluentCommand extends org.bukkit.command.Command implements Command {

    private final CommandProperties properties;
    private final List<ArgumentProperties> arguments;
    private final List<Command> children;
    private final CommandServices services;

    @Setter
    private Command parent;

    public FluentCommand(CommandProperties properties,
                         List<ArgumentProperties> argumentProperties,
                         List<Command> children,
                         CommandServices services) {
        super(properties.name());
        this.properties = properties;
        this.arguments = argumentProperties;
        this.children = children;
        this.services = services;
    }

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] arguments) {
        var target = services.targetedCommand(this, arguments);
        var result = services.executeService().execute(target.getCommand(), sender, commandLabel, target.getArguments(), arguments);
        if (result.isFailed()) {
            sender.sendMessage(result.getMessage());
        }
        return result.isSuccess();
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String alias, String[] arguments) {
        var target = services.targetedCommand(this, arguments);
        var result = services.executeService().executeTab(target.getCommand(), sender, alias, arguments);
        if (result.isFailed()) {
            sender.sendMessage(result.getMessage());
        }
        return result.getObject();
    }

    @Override
    public Optional<Command> parent() {
        return Optional.ofNullable(parent);
    }

    public boolean hasParent() {
        return parent != null;
    }

    @Override
    public String name() {
        return properties.name();
    }

}
