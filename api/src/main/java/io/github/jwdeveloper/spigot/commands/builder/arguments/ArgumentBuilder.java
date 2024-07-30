package io.github.jwdeveloper.spigot.commands.builder.arguments;

import io.github.jwdeveloper.spigot.commands.data.DisplayAttribute;
import io.github.jwdeveloper.spigot.commands.argumetns.ArgumentProperties;
import io.github.jwdeveloper.spigot.commands.data.ActionResult;
import io.github.jwdeveloper.spigot.commands.functions.ArgumentParser;
import io.github.jwdeveloper.spigot.commands.functions.ArgumentSuggestions;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

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

    default ArgumentBuilder withType(String argumentType) {
        return withProperty(e -> e.type(argumentType));
    }

    default ArgumentBuilder withDisplayAttribute(DisplayAttribute... displayMode) {
        return withProperty(e -> e.displayAttributes().addAll(Arrays.stream(displayMode).toList()));
    }

    default ArgumentBuilder withDisplayDescription() {
        return withDisplayAttribute(DisplayAttribute.DESCRIPTION);
    }


    default ArgumentBuilder withDisplayName() {
        return withDisplayAttribute(DisplayAttribute.NAME);
    }

    default ArgumentBuilder withDisplayType() {
        return withDisplayAttribute(DisplayAttribute.TYPE);
    }

    default ArgumentBuilder withDisplayNone() {
        return withDisplayAttribute(DisplayAttribute.NONE);
    }

    default ArgumentBuilder withDisplayError() {
        return withDisplayAttribute(DisplayAttribute.ERROR);
    }

    default ArgumentBuilder withDisplaySuggestions() {
        return withDisplayAttribute(DisplayAttribute.SUGGESTIONS);
    }

    default ArgumentBuilder withSuggestions(ArgumentSuggestions suggestions) {
        return withProperty(e -> e.suggestion(suggestions));
    }

    default ArgumentBuilder withSuggestions(String... suggestions) {
        return withSuggestions((e) -> ActionResult.success(List.of(suggestions)));
    }

    default ArgumentBuilder withSuggestions(List<String> suggestions) {
        return withSuggestions((e) -> ActionResult.success(suggestions));
    }

    default ArgumentBuilder withParser(ArgumentParser parser) {
        return withProperty(e -> e.parser(parser));
    }

    default ArgumentBuilder withRequired(boolean required) {
        return withProperty(e -> e.required(required));
    }

    default ArgumentBuilder withRequired() {
        return withProperty(e -> e.required(true));
    }

    default ArgumentBuilder withDefaultValue(Object required) {
        return withProperty(e -> e.defaultValue(required.toString()));
    }

}
