package io.github.jwdeveloper.spigot.commands.builders;

import io.github.jwdeveloper.dependance.api.DependanceContainer;
import io.github.jwdeveloper.spigot.commands.Command;
import io.github.jwdeveloper.spigot.commands.CommandsRegistry;
import io.github.jwdeveloper.spigot.commands.FluentCommand;
import io.github.jwdeveloper.spigot.commands.builder.CommandBuilder;
import io.github.jwdeveloper.spigot.commands.builder.arguments.ArgumentBuilder;
import io.github.jwdeveloper.spigot.commands.data.CommandProperties;
import io.github.jwdeveloper.spigot.commands.events.CommandEventsImpl;
import io.github.jwdeveloper.spigot.commands.functions.CommandEventAction;
import io.github.jwdeveloper.spigot.commands.services.CommandServices;
import io.github.jwdeveloper.spigot.commands.services.EventsService;
import lombok.Getter;
import lombok.experimental.Accessors;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Supplier;

@Accessors(fluent = true)
public class CommandsBuilderImpl implements CommandBuilder {

    @Getter
    private CommandProperties properties;
    private final EventsService commandEvents;
    private final Set<Consumer<ArgumentBuilder>> argumentBuilders;
    private final Set<CommandBuilder> childrenBuilders;
    private final CommandsRegistry registry;
    private final DependanceContainer container;

    public CommandsBuilderImpl(CommandsRegistry registry, DependanceContainer container) {
        this.properties = new CommandProperties();
        this.argumentBuilders = new HashSet<>();
        this.commandEvents = new EventsService();
        this.childrenBuilders = new HashSet<>();
        this.container = container;
        this.registry = registry;
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
    public CommandBuilder onEvent(CommandEventAction<?> action) {
        commandEvents.subscribe(action);
        return self();
    }

    @Override
    public Command build() {

        var arguments = argumentBuilders.stream().map(e ->
        {
            var builder = new ArgumentBuilderImpl();
            e.accept(builder);
            return builder.build();
        }).toList();
        var children = childrenBuilders.stream()
                .map(CommandBuilder::build)
                .toList();

        var services = container.find(CommandServices.class);

        var command = new FluentCommand(
                properties,
                arguments,
                children,
                services);

        var commandContainer = container.createChildContainer()
                .registerSingleton(Command.class, command)
                .registerSingleton(EventsService.class, command)
                .build();

        return commandContainer.find(Command.class);
    }


    @Override
    public Command buildAndRegister() {
        var command = build();
        registry.add(command);
        return command;
    }


    @Override
    public CommandBuilder addArgument(Consumer<ArgumentBuilder> action) {
        argumentBuilders.add(action);
        return self();
    }

    @Override
    public CommandBuilder self() {
        return this;
    }

    @Override
    public CommandBuilder addChildren(Consumer<CommandBuilder> builderConsumer) {
        var builder = addChildren();
        builderConsumer.accept(builder);
        return this;
    }

    @Override
    public CommandBuilder addChildren() {
        var builder = container.find(CommandBuilder.class);
        childrenBuilders.add(builder);
        return builder;
    }
}
