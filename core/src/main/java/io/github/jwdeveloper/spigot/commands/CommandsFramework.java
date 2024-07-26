package io.github.jwdeveloper.spigot.commands;

import io.github.jwdeveloper.dependance.Dependance;
import io.github.jwdeveloper.dependance.api.DependanceContainer;
import io.github.jwdeveloper.dependance.implementation.DependanceContainerBuilder;
import io.github.jwdeveloper.spigot.commands.builder.CommandBuilder;
import io.github.jwdeveloper.spigot.commands.builders.FluentCommandBuilder;
import io.github.jwdeveloper.spigot.commands.listeners.DisableCommandsApiListener;
import io.github.jwdeveloper.spigot.commands.parsers.*;
import io.github.jwdeveloper.spigot.commands.services.*;
import io.github.jwdeveloper.spigot.commands.templates.FluentTemplateCommand;
import io.github.jwdeveloper.spigot.commands.templates.expressions.PatternExpressionService;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.plugin.Plugin;

import java.util.List;
import java.util.function.Consumer;

public class CommandsFramework {

    private static DependanceContainer container;

    public static boolean isEnabled() {
        return container != null;
    }

    public static Commands enable(Plugin plugin) {
        return enable(plugin, x -> {
        });
    }

    public static Commands enable(Plugin plugin, Consumer<DependanceContainerBuilder> action) {

        if (isEnabled()) {
            throw new RuntimeException("Fluent commands has already been enabled");
        }

        var builder = Dependance.newContainer();
        builder.registerSingleton(Plugin.class, plugin);
        builder.registerSingleton(Commands.class, FluentCommandsApi.class);
        builder.registerSingleton(CommandsRegistry.class, FluentCommandsRegistry.class);
        builder.registerSingleton(ArgumentTypesRegistry.class, FluentArgumentTypesRegistry.class);
        builder.registerTransient(CommandBuilder.class, FluentCommandBuilder.class);
        builder.registerTransient(MessagesService.class, FluentMessageService.class);
        builder.registerTransient(TemplateCommand.class, FluentTemplateCommand.class);

        builder.registerSingleton(PatternExpressionService.class);
        builder.registerSingleton(DisableCommandsApiListener.class);


        action.accept(builder);

        container = builder.build();

        var listener = container.find(DisableCommandsApiListener.class);
        Bukkit.getPluginManager().registerEvents(listener, plugin);


        var defaultArgumentTypes =
                List.of(BoolParser.class,
                        EnumParser.class,
                        LocationParser.class,
                        NumberParser.class,
                        PlayerParser.class,
                        TextParser.class
                );
        var containerBuilder = Dependance.newContainer();
        defaultArgumentTypes.forEach(containerBuilder::registerTransient);
        var typesContainer = containerBuilder.build();

        var argumentTypesRegistry = container.find(ArgumentTypesRegistry.class);

        argumentTypesRegistry.add(new EnumTypeParser(EntityType.class,"entity"));
        argumentTypesRegistry.add(new EnumTypeParser(Sound.class,"sound"));
        argumentTypesRegistry.add(new EnumTypeParser(Particle.class,"particle"));
        argumentTypesRegistry.add(new EnumTypeParser(ChatColor.class,"color"));
        defaultArgumentTypes.forEach((e) ->
        {
            argumentTypesRegistry.add(typesContainer.find(e));
        });

        return api();
    }

    public static Commands api() {
        if (!isEnabled()) {
            throw new RuntimeException("Fluent commands has not been enabled");
        }
        return container.find(Commands.class);
    }

    public static void disable() {
        if (!isEnabled()) {
            throw new RuntimeException("Fluent commands has not been enabled");
        }

        var listener = container.find(DisableCommandsApiListener.class);
        PluginDisableEvent.getHandlerList().unregister(listener);

        api().removeAll();
    }
}
