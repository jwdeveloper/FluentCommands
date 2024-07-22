package io.github.jwdeveloper.spigot.commands.builder.arguments;

import io.github.jwdeveloper.spigot.commands.data.ActionResult;
import io.github.jwdeveloper.spigot.commands.data.argumetns.ArgumentDisplay;
import io.github.jwdeveloper.spigot.commands.data.argumetns.ArgumentProperties;
import io.github.jwdeveloper.spigot.commands.data.argumetns.ArgumentType;
import io.github.jwdeveloper.spigot.commands.data.argumetns.parsing.ArgumentEvent;
import io.github.jwdeveloper.spigot.commands.data.argumetns.parsing.ArgumentParser;

import java.util.Arrays;
import java.util.function.Consumer;
import java.util.function.Function;

public interface ArgumentBuilder {


    ArgumentBuilder withProperty(Consumer<ArgumentProperties> action);

    default ArgumentBuilder withIndex(int index) {
        return withProperty(e -> e.index(index));
    }

    default ArgumentBuilder withDescription(String description) {
        return withProperty(e -> e.description(description));
    }

    default ArgumentBuilder withName(String name) {
        return withProperty(e -> e.name(name));
    }

    default ArgumentBuilder withType(ArgumentType argumentType) {
        return withProperty(e -> e.type(argumentType));
    }

    default ArgumentBuilder withDisplayMode(ArgumentDisplay displayMode) {
        return withProperty(e -> e.displayMode(displayMode));
    }

    default ArgumentBuilder withParserResult(ArgumentParser parser) {
        return withProperty(e -> e.parsers().add(parser));
    }

    default ArgumentBuilder withParser(Function<ArgumentEvent, Object> parser) {
        return withProperty(e -> e.parsers().add(event ->
        {
            try {
                return ActionResult.success(parser.apply(event));
            } catch (Exception ex) {
                return ActionResult.failed(ex.getMessage());
            }
        }));
    }

    default ArgumentBuilder withIsRequired(boolean required) {
        return withProperty(e -> e.required(required));
    }


    default ArgumentBuilder withDefaultValue(Object required) {
        return withProperty(e -> e.defaultValue(required));
    }

}
