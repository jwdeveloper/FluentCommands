package io.github.jwdeveloper.spigot.commands;

import io.github.jwdeveloper.dependance.Dependance;
import io.github.jwdeveloper.dependance.api.DependanceContainer;
import io.github.jwdeveloper.dependance.implementation.DependanceContainerBuilder;
import io.github.jwdeveloper.spigot.commands.builder.CommandBuilder;
import io.github.jwdeveloper.spigot.commands.builders.FluentCommandBuilder;
import io.github.jwdeveloper.spigot.commands.listeners.DisableCommandsApiListener;
import io.github.jwdeveloper.spigot.commands.services.*;
import org.bukkit.Bukkit;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.plugin.Plugin;

import java.util.function.Consumer;

public class CommandsFramework {

    private static DependanceContainer container;

    public static boolean isEnabled() {
        return container != null;
    }

    public static CommandsApi enable(Plugin plugin) {
        return enable(plugin, x -> {
        });
    }

    public static CommandsApi enable(Plugin plugin, Consumer<DependanceContainerBuilder> action) {

        if (isEnabled()) {
            throw new RuntimeException("Fluent commands has already been enabled");
        }

        var builder = Dependance.newContainer();
        builder.registerSingleton(Plugin.class, plugin);
        builder.registerSingleton(CommandsApi.class, FluentCommandsApi.class);
        builder.registerSingleton(CommandsRegistry.class, FluentCommandsRegistry.class);
        builder.registerTransient(CommandBuilder.class, FluentCommandBuilder.class);



        builder.registerSingleton(DisableCommandsApiListener.class);


        action.accept(builder);

        container = builder.build();

        var listener = container.find(DisableCommandsApiListener.class);
        Bukkit.getPluginManager().registerEvents(listener, plugin);
        return api();
    }

    public static CommandsApi api() {
        if (!isEnabled()) {
            throw new RuntimeException("Fluent commands has not been enabled");
        }
        return container.find(CommandsApi.class);
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
