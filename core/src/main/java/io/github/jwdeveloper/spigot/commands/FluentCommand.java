package io.github.jwdeveloper.spigot.commands;

import io.github.jwdeveloper.spigot.commands.data.ActionResult;
import io.github.jwdeveloper.spigot.commands.data.CommandProperties;
import io.github.jwdeveloper.spigot.commands.argumetns.ArgumentProperties;
import io.github.jwdeveloper.spigot.commands.data.events.CommandEvent;
import io.github.jwdeveloper.spigot.commands.services.CommandServices;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.bukkit.command.CommandSender;

import java.util.*;
import java.util.stream.Collectors;

@Getter
@Accessors(fluent = true)
public class FluentCommand extends org.bukkit.command.Command implements Command {

    private final CommandProperties properties;
    private final List<ArgumentProperties> arguments;
    private final List<Command> children;
    private final Map<String, Command> childrenByName;
    private final CommandServices commandService;

    @Setter
    private Command parent;

    public FluentCommand(CommandProperties properties,
                         List<ArgumentProperties> argumentProperties,
                         List<Command> children,
                         CommandServices services) {
        super(properties.name());
        this.properties = properties;
        this.arguments = argumentProperties;
        this.commandService = services;
        this.children = children;
        this.childrenByName = children.stream().collect(Collectors.toMap(Command::name, e -> e));
    }

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] arguments) {
        return this.executeCommand(sender, commandLabel, arguments).isSuccess();
    }

    @Override
    public boolean hasChild(String name) {
        return childrenByName.containsKey(name);
    }

    @Override
    public Optional<Command> child(String name) {
        return Optional.ofNullable(childrenByName.get(name));
    }

    public ActionResult<CommandEvent> executeCommand(CommandSender sender, String commandLabel, String[] arguments) {
        var result = commandService.execute(this, sender, commandLabel, arguments);
        if (result.isFailed()) {
            sender.sendMessage(result.getMessage());
        }
        return result;
    }

    @Override
    public ActionResult<List<String>> executeHint(CommandSender sender, String alias, String... arguments) {
        var result = commandService.executeTab(this, sender, alias,  arguments);
        return ActionResult.success(result);
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String alias, String[] arguments) {
        var result = executeHint(sender, alias, arguments);
        if (result.isFailed()) {
            return Collections.emptyList();
        }
        return result.getValue();
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
