package io.github.jwdeveloper.spigot.commands;

import io.github.jwdeveloper.dependance.api.DependanceContainer;
import io.github.jwdeveloper.spigot.commands.builder.CommandBuilder;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class FluentCommandsApi implements CommandsApi {
    private final CommandsRegistry commandsRegistry;
    private final DependanceContainer container;

    public FluentCommandsApi(CommandsRegistry commandsRegistry, DependanceContainer container) {
        this.commandsRegistry = commandsRegistry;
        this.container = container;
    }

    @Override
    public CommandBuilder create(String commandName) {
        return create().withName(commandName);
    }

    @Override
    public CommandBuilder create() {
        return container.find(CommandBuilder.class);
    }

    @Override
    public void add(Command command) {
        commandsRegistry.add(command);
    }

    @Override
    public void remove(Command command) {
        commandsRegistry.remove(command);
    }

    @Override
    public void removeAll() {
        commandsRegistry.removeAll();
    }

    @Override
    public List<Command> findAll() {
        return commandsRegistry.commands();
    }

    @Override
    public Stream<Command> findBy(Predicate<Command> predicate) {
        return commandsRegistry.commands().stream().filter(predicate);
    }

    @Override
    public Optional<Command> findByName(String commandName) {
        return findBy(command -> command.properties().name().equals(commandName)).findFirst();
    }

}
