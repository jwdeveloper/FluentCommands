package io.github.jwdeveloper.spigot.commands;

import io.github.jwdeveloper.spigot.commands.argumetns.ArgumentType;
import io.github.jwdeveloper.spigot.commands.argumetns.ArgumentTypeBuilder;
import io.github.jwdeveloper.spigot.commands.argumetns.ArgumentTypesRegistry;
import io.github.jwdeveloper.spigot.commands.data.ActionResult;
import io.github.jwdeveloper.spigot.commands.data.events.ArgumentParseEvent;
import io.github.jwdeveloper.spigot.commands.data.events.ArgumentSuggestionEvent;
import io.github.jwdeveloper.spigot.commands.functions.ArgumentParser;
import io.github.jwdeveloper.spigot.commands.functions.ArgumentSuggestions;

import java.util.List;

public class FluentArgumentTypeBuilder implements ArgumentTypeBuilder {

    private final String name;

    private final ArgumentTypesRegistry argumentTypesRegistry;

    private ArgumentParser parser;
    private ArgumentSuggestions suggestions;


    public FluentArgumentTypeBuilder(String name, ArgumentTypesRegistry argumentTypesRegistry) {
        this.name = name;
        this.argumentTypesRegistry = argumentTypesRegistry;
        suggestions = (x) -> ActionResult.success(List.of());
        parser = (x) -> ActionResult.success(x.iterator().next());
    }

    @Override
    public ArgumentTypeBuilder onSuggestionAction(ArgumentSuggestions suggestions) {
        this.suggestions = suggestions;
        return this;
    }

    @Override
    public ArgumentTypeBuilder onParseAction(ArgumentParser parser) {
        this.parser = parser;
        return this;
    }

    @Override
    public ArgumentType register() {
        var argumentType = new ArgumentType() {
            @Override
            public String name() {
                return name;
            }

            @Override
            public ActionResult<List<String>> onSuggestion(ArgumentSuggestionEvent event) {
                return suggestions.onSuggestion(event);
            }

            @Override
            public ActionResult<Object> onParse(ArgumentParseEvent event) {
                return parser.onParse(event);
            }
        };

        argumentTypesRegistry.register(argumentType);
        return argumentType;
    }

}
