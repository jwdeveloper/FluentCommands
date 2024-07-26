package io.github.jwdeveloper.spigot.commands.builders;

import io.github.jwdeveloper.spigot.commands.ArgumentTypesRegistry;
import io.github.jwdeveloper.spigot.commands.builder.arguments.ArgumentBuilder;
import io.github.jwdeveloper.spigot.commands.argumetns.ArgumentProperties;
import io.github.jwdeveloper.spigot.commands.ArgumentType;
import io.github.jwdeveloper.spigot.commands.data.ActionResult;
import io.github.jwdeveloper.spigot.commands.functions.ArgumentParser;
import lombok.Getter;

import java.util.*;
import java.util.function.Consumer;

public class FluentArgumentBuilder implements ArgumentBuilder {

    @Getter
    private final ArgumentProperties properties;
    private final Set<ArgumentType> argumentType;
    private final ArgumentTypesRegistry typesRegistry;

    public FluentArgumentBuilder(ArgumentProperties properties,
                                 ArgumentTypesRegistry argumentTypesRegistry) {
        this.properties = properties;
        argumentType = new TreeSet<>();
        typesRegistry = argumentTypesRegistry;
    }

    @Override
    public ArgumentBuilder withProperty(Consumer<ArgumentProperties> action) {
        action.accept(properties);
        return this;
    }

    @Override
    public ArgumentBuilder withParser(ArgumentParser parserType) {
        withProperty(argumentProperties ->
        {
            argumentProperties.parser(parserType);
        });
        return this;
    }

    public ArgumentProperties build() {
        argumentType.forEach(typesRegistry::add);
        var argumentTypeName = properties.type();
        if (argumentTypeName.isEmpty()) {
            argumentTypeName = "Text";
        }

        var argumentType = typesRegistry
                .find(argumentTypeName)
                .orElseThrow(() -> new RuntimeException("Type not found: " + properties.type()));

        withParser(argumentType);
        if (properties.defaultValue() == null)
            withDefaultValue(argumentType.defaultValue());

        if (properties.suggestions() == null)
            withSuggestions(argumentType);

        if (properties.suggestions() == null)
            withSuggestions(event -> ActionResult.success(Collections.emptyList()));

        return properties;
    }
}
