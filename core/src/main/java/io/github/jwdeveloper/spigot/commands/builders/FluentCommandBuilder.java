package io.github.jwdeveloper.spigot.commands.builders;

import io.github.jwdeveloper.dependance.api.DependanceContainer;
import io.github.jwdeveloper.spigot.commands.Command;
import io.github.jwdeveloper.spigot.commands.CommandsRegistry;
import io.github.jwdeveloper.spigot.commands.FluentCommand;
import io.github.jwdeveloper.spigot.commands.builder.CommandBuilder;
import io.github.jwdeveloper.spigot.commands.builder.arguments.ArgumentBuilder;
import io.github.jwdeveloper.spigot.commands.data.CommandProperties;
import io.github.jwdeveloper.spigot.commands.data.argumetns.ArgumentProperties;
import io.github.jwdeveloper.spigot.commands.functions.CommandEventAction;
import io.github.jwdeveloper.spigot.commands.services.*;
import lombok.Getter;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

@Accessors(fluent = true)
public class FluentCommandBuilder implements CommandBuilder {

    @Getter
    private CommandProperties properties;
    private final EventsService eventsService;
    private final List<Consumer<ArgumentBuilder>> argumentBuilders;
    private final List<CommandBuilder> childrenBuilders;
    private final CommandsRegistry registry;
    private final DependanceContainer container;

    public FluentCommandBuilder(CommandsRegistry registry, DependanceContainer container) {
        this.properties = new CommandProperties();
        this.argumentBuilders = new ArrayList<>();
        this.eventsService = new EventsService();
        this.childrenBuilders = new ArrayList<>();
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
    public CommandBuilder onEvent(Class<?> senderType, CommandEventAction<?> action) {
        eventsService.subscribe(senderType, action);
        return self();
    }

    @Override
    public Command build() {

        var counter = 0;
        var arguments = new ArrayList<ArgumentProperties>();
        for (var argumentBuilder : argumentBuilders) {
            var builder = new ArgumentBuilderImpl(new ArgumentProperties());
            argumentBuilder.accept(builder);
            var argument = builder.build();
            argument.index(counter++);

            arguments.add(argument);
        }

        var children = childrenBuilders.stream()
                .map(CommandBuilder::build)
                .toList();

        var commandContainer = container.createChildContainer()
                .registerSingleton(EventsService.class, eventsService)
                .registerSingleton(CommandServices.class)
                .registerSingleton(ValidationService.class)
                .registerSingleton(ExecuteService.class)
                .registerSingleton(MessagesService.class)
                .registerSingleton(ArgumentsService.class)
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
    public CommandBuilder addSubCommand(Consumer<CommandBuilder> builderConsumer) {
        var builder = addSubCommand();
        builderConsumer.accept(builder);
        return this;
    }

    @Override
    public CommandBuilder addSubCommand() {
        var builder = container.find(CommandBuilder.class);
        childrenBuilders.add(builder);
        return builder;
    }
}
