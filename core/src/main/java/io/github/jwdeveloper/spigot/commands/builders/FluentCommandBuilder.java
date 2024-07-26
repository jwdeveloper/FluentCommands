package io.github.jwdeveloper.spigot.commands.builders;

import io.github.jwdeveloper.dependance.api.DependanceContainer;
import io.github.jwdeveloper.spigot.commands.ArgumentTypesRegistry;
import io.github.jwdeveloper.spigot.commands.Command;
import io.github.jwdeveloper.spigot.commands.CommandsRegistry;
import io.github.jwdeveloper.spigot.commands.FluentCommand;
import io.github.jwdeveloper.spigot.commands.builder.CommandBuilder;
import io.github.jwdeveloper.spigot.commands.builder.arguments.ArgumentBuilder;
import io.github.jwdeveloper.spigot.commands.data.CommandProperties;
import io.github.jwdeveloper.spigot.commands.argumetns.ArgumentProperties;
import io.github.jwdeveloper.spigot.commands.functions.CommandEventAction;
import io.github.jwdeveloper.spigot.commands.services.*;
import io.github.jwdeveloper.spigot.commands.services.CommandParser;
import lombok.Getter;
import lombok.experimental.Accessors;

import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.Consumer;

@Accessors(fluent = true)
public class FluentCommandBuilder implements CommandBuilder {

    @Getter
    private CommandProperties properties;
    private final EventsService eventsService;
    private final CommandsRegistry registry;
    private final DependanceContainer container;
    private final Map<String, CommandBuilder> subCommadnsBuilders;
    private final Map<String, FluentArgumentBuilder> argumentBuilders;
    private final ArgumentTypesRegistry argumentTypesRegistry;

    public FluentCommandBuilder(CommandsRegistry registry,
                                DependanceContainer container,
                                ArgumentTypesRegistry argumentTypesRegistry) {
        this.properties = new CommandProperties();
        this.eventsService = new EventsService();
        this.argumentBuilders = new TreeMap<>();
        this.subCommadnsBuilders = new TreeMap<>();
        this.container = container;
        this.registry = registry;
        this.argumentTypesRegistry = argumentTypesRegistry;
    }

    @Override
    public CommandBuilder withProperties(Consumer<CommandProperties> action) {
        action.accept(properties);
        return self();
    }

    @Override
    public CommandBuilder withProperties(CommandProperties properties) {
        this.properties = properties;
        return self();
    }

    @Override
    public CommandBuilder onEvent(Class<?> senderType, CommandEventAction<?> action) {
        eventsService.subscribe(senderType, action);
        return self();
    }


    @Override
    public Command register() {
        var command = build();
        registry.add(command);
        return command;
    }


    @Override
    public CommandBuilder subCommand(String name) {
        var builder = subCommadnsBuilders.computeIfAbsent(name, s -> container.find(CommandBuilder.class));
        builder.withName(name);
        return builder;
    }

    @Override
    public CommandBuilder addSubCommand(String name, Consumer<CommandBuilder> builderConsumer) {
        var builder = subCommand(name);
        builderConsumer.accept(builder);
        return this;
    }


    @Override
    public ArgumentBuilder argument(String name) {
        var builder = argumentBuilders.computeIfAbsent(name, s ->
                new FluentArgumentBuilder(new ArgumentProperties(), argumentTypesRegistry));
        builder.withName(name);
        builder.withIndex(argumentBuilders.size());
        return builder;
    }

    @Override
    public CommandBuilder addArgument(String name, Consumer<ArgumentBuilder> action) {
        action.accept(argument(name));
        return self();
    }

    @Override
    public CommandBuilder self() {
        return this;
    }


    @Override
    public Command build() {

        var arguments = argumentBuilders.values()
                .stream()
                .sorted(Comparator.comparingInt(a -> a.getProperties().index()))
                .map(FluentArgumentBuilder::build)
                .toList();

        var children = subCommadnsBuilders.values()
                .stream()
                .map(CommandBuilder::build)
                .toList();

        var commandContainer = container.createChildContainer()
                .registerSingleton(EventsService.class, eventsService)
                .registerSingleton(CommandServices.class)
                .registerSingleton(ValidationService.class)
                .registerSingleton(ExpressionService.class)
                .registerSingleton(CommandParser.class)
                .registerSingleton(Command.class, con ->
                {
                    var services = (CommandServices) con.find(CommandServices.class);
                    return new FluentCommand(
                            properties,
                            arguments,
                            children,
                            services);
                })
                .build();
        return commandContainer.find(Command.class);
    }


}
