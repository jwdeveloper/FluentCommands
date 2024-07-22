package io.github.jwdeveloper.spigot.commands.builders;

import io.github.jwdeveloper.spigot.commands.builder.arguments.ArgumentBuilder;
import io.github.jwdeveloper.spigot.commands.data.ActionResult;
import io.github.jwdeveloper.spigot.commands.data.argumetns.ArgumentProperties;
import io.github.jwdeveloper.spigot.commands.data.argumetns.parsing.ArgumentEvent;
import org.bukkit.Bukkit;
import org.checkerframework.checker.units.qual.A;

import java.util.function.Consumer;

public class ArgumentBuilderImpl implements ArgumentBuilder {

    private final ArgumentProperties properties;

    public ArgumentBuilderImpl(ArgumentProperties properties) {
        this.properties = properties;
    }

    @Override
    public ArgumentBuilder withProperty(Consumer<ArgumentProperties> action) {
        action.accept(properties);
        return this;
    }

    public ArgumentProperties build()
    {
        switch (properties.type()) {
            case TEXT -> withParser(ArgumentEvent::arg);
            case INT -> withParser(argumentEvent -> Integer.parseInt(argumentEvent.arg()));
            case FLOAT, NUMBER -> withParser(argumentEvent -> Float.parseFloat(argumentEvent.arg()));
            case PLAYER -> withParser(argumentEvent -> Bukkit.getPlayer(argumentEvent.arg()));
            case BOOL -> withParser(argumentEvent -> Boolean.parseBoolean(argumentEvent.arg()));
        }
        return properties;
    }
}
