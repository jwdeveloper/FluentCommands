package io.github.jwdeveloper.spigot.commands.builders;

import io.github.jwdeveloper.spigot.commands.builder.arguments.ArgumentBuilder;
import io.github.jwdeveloper.spigot.commands.data.argumetns.ArgumentDisplay;
import io.github.jwdeveloper.spigot.commands.data.argumetns.ArgumentProperties;
import io.github.jwdeveloper.spigot.commands.data.argumetns.parsing.ArgumentEvent;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.function.Consumer;

public class ArgumentBuilderImpl implements ArgumentBuilder {

    @Getter
    private final ArgumentProperties properties;

    public ArgumentBuilderImpl(ArgumentProperties properties) {
        this.properties = properties;
    }

    @Override
    public ArgumentBuilder withProperty(Consumer<ArgumentProperties> action) {
        action.accept(properties);
        return this;
    }

    public ArgumentProperties build() {

        switch (properties.type()) {
            case TEXT -> {
                withParser(ArgumentEvent::arg);
                if (properties.defaultValue() == null)
                    withDefaultValue("");
            }
            case INT -> {
                withParser(argumentEvent -> Integer.parseInt(argumentEvent.arg()));
                if (properties.suggestions() == null)
                    withSuggestions("0", properties.name());
                if (properties.defaultValue() == null)
                    withDefaultValue(0);
            }
            case FLOAT, NUMBER -> {
                withParser(argumentEvent -> Float.parseFloat(argumentEvent.arg()));
                if (properties.suggestions() == null)
                    withSuggestions("1.0", properties.name());
                if (properties.defaultValue() == null)
                    withDefaultValue(1.0);
            }
            case PLAYER -> {
                withParser(argumentEvent -> Bukkit.getPlayer(argumentEvent.arg()));
                withSuggestions(input -> Bukkit.getOnlinePlayers().stream()
                        .filter(e -> e.getName().contains(input))
                        .limit(10)
                        .map(Player::getName)
                        .toList());
            }
            case BOOL -> {
                withParser(argumentEvent -> Boolean.parseBoolean(argumentEvent.arg()));
                if (properties.suggestions() == null)
                    withSuggestions("true", "false");
                if (properties.defaultValue() == null)
                    withDefaultValue(false);
            }
        }

        if (properties.suggestions() == null) {
            withSuggestions(List::of);
        } else {
            withDisplayMode(ArgumentDisplay.SUGGESTIONS);
        }

        return properties;
    }
}
