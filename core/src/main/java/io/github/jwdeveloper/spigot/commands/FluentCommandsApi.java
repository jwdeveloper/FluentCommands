package io.github.jwdeveloper.spigot.commands;

import io.github.jwdeveloper.dependance.api.DependanceContainer;
import io.github.jwdeveloper.spigot.commands.builder.CommandBuilder;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class FluentCommandsApi implements Commands {
    private final CommandsRegistry commandsRegistry;
    private final DependanceContainer container;
    private final TemplateCommand commandsTemplate;

    public FluentCommandsApi(CommandsRegistry commandsRegistry,
                             DependanceContainer container,
                             TemplateCommand commandsTemplate) {
        this.commandsRegistry = commandsRegistry;
        this.container = container;
        this.commandsTemplate = commandsTemplate;
    }

    @Override
    public CommandBuilder create(String pattern) {
        return create().withName(pattern);
    }

    @Override
    public CommandBuilder create(Object templateObject) {
        return commandsTemplate.templateToBuilder(templateObject, create());
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
