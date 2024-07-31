package io.github.jwdeveloper.spigot.commands;

import io.github.jwdeveloper.dependance.api.DependanceContainer;
import io.github.jwdeveloper.spigot.commands.argumetns.ArgumentTypes;
import io.github.jwdeveloper.spigot.commands.builder.CommandBuilder;
import io.github.jwdeveloper.spigot.commands.patterns.PatternService;
import io.github.jwdeveloper.spigot.commands.patterns.Patterns;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class FluentCommands implements Commands {
    private final CommandsRegistry commandsRegistry;
    private final DependanceContainer container;
    private final TemplateCommand commandsTemplate;
    private final ArgumentTypes argumentTypesRegistry;
    private final PatternService patternService;

    public FluentCommands(CommandsRegistry commandsRegistry,
                          DependanceContainer container,
                          ArgumentTypes argumentTypesRegistry,
                          TemplateCommand commandsTemplate,
                          PatternService patternService) {
        this.commandsRegistry = commandsRegistry;
        this.container = container;
        this.commandsTemplate = commandsTemplate;
        this.patternService = patternService;
        this.argumentTypesRegistry = argumentTypesRegistry;
    }



    @Override
    public Patterns patterns() {
        return null;
    }

    @Override
    public ArgumentTypes argumentTypes() {
        return argumentTypesRegistry;
    }

    @Override
    public CommandBuilder create(String pattern) {
        var result = patternService.getCommandBuilder(pattern, create());
        if (result.isFailed()) {
            throw new RuntimeException(result.getMessage());
        }
        return result.getValue();
    }

    @Override
    public CommandBuilder create(Object templateObject) {
        return commandsTemplate.templateToBuilder(templateObject, create());
    }

    public CommandBuilder create() {
        return container.find(CommandBuilder.class);
    }

    @Override
    public void register(Command command) {
        commandsRegistry.add(command);
    }

    @Override
    public void unregister(Command command) {
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
