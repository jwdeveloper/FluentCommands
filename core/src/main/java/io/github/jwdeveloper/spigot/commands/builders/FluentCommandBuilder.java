package io.github.jwdeveloper.spigot.commands.builders;

import io.github.jwdeveloper.dependance.api.DependanceContainer;
import io.github.jwdeveloper.spigot.commands.argumetns.ArgumentTypes;
import io.github.jwdeveloper.spigot.commands.Command;
import io.github.jwdeveloper.spigot.commands.CommandsRegistry;
import io.github.jwdeveloper.spigot.commands.FluentCommand;
import io.github.jwdeveloper.spigot.commands.builder.CommandBuilder;
import io.github.jwdeveloper.spigot.commands.builder.arguments.ArgumentBuilder;
import io.github.jwdeveloper.spigot.commands.data.CommandProperties;
import io.github.jwdeveloper.spigot.commands.argumetns.ArgumentProperties;
import io.github.jwdeveloper.spigot.commands.data.events.CommandEvent;
import io.github.jwdeveloper.spigot.commands.data.events.CommandValidationEvent;
import io.github.jwdeveloper.spigot.commands.services.*;
import io.github.jwdeveloper.spigot.commands.services.CommandParser;
import lombok.Getter;
import lombok.experimental.Accessors;
import org.bukkit.command.CommandSender;

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
    private final ArgumentTypes argumentTypesRegistry;

    public FluentCommandBuilder(CommandsRegistry registry,
                                DependanceContainer container,
                                ArgumentTypes argumentTypesRegistry) {
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
        return this;
    }

    @Override
    public CommandBuilder onValidation(Consumer<CommandValidationEvent> action) {
        //eventsService.subscribe();
        return this;
    }

    @Override
    public <E extends CommandSender> CommandBuilder onEvent(Class<?> senderType, Consumer<CommandEvent<E>> action) {
        eventsService.subscribe(senderType, action);
        return this;
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
        builder.properties().name(name);
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

        if (argumentBuilders.containsKey(name)) {
            return argumentBuilders.get(name);
        }

        var builder =  new FluentArgumentBuilder(new ArgumentProperties(), argumentTypesRegistry);
        argumentBuilders.put(name, builder);

        builder.withName(name);
        builder.withIndex(argumentBuilders.size());
        return builder;
    }

    @Override
    public CommandBuilder addArgument(String name, Consumer<ArgumentBuilder> builder) {
        builder.accept(argument(name));
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
