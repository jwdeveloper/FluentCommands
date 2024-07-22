package io.github.jwdeveloper.spigot.commands.builder;

import io.github.jwdeveloper.spigot.commands.builder.arguments.ArgumentBuilder;
import io.github.jwdeveloper.spigot.commands.data.argumetns.ArgumentType;

import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public interface CommandArgumentsBuilder<T> {

    T addArgument(Consumer<ArgumentBuilder> action);

    default T addArgument(String name) {
        return addArgument(name, ArgumentType.TEXT, (x) -> {
        });
    }

    default T addArgument(String name, ArgumentType argumentType) {
        return addArgument(name, argumentType, (x) -> {
        });
    }

    default T addArgument(String name, Consumer<ArgumentBuilder> action) {
        return addArgument(name, ArgumentType.TEXT, action);
    }

    default T addArgument(String name, ArgumentType argumentType, Consumer<ArgumentBuilder> action) {
        return addArgument(argumentBuilder ->
        {
            argumentBuilder.withName(name);
            argumentBuilder.withType(argumentType);
            action.accept(argumentBuilder);
        });
    }

    default T addNumberArgument(String argumentName) {
        return addArgument(argumentName, ArgumentType.NUMBER);
    }

    default T addNumberArgument(String argumentName, Consumer<ArgumentBuilder> action) {
        return addArgument(argumentName, ArgumentType.NUMBER, action);
    }

    default T addTextArgument(String argumentName) {
        return addArgument(argumentName, ArgumentType.TEXT);
    }

    default T addTextArgument(String argumentName, Consumer<ArgumentBuilder> action) {
        return addArgument(argumentName, ArgumentType.TEXT, action);
    }


    default T addEnumArgument(Class<? extends Enum> type, String name, Consumer<ArgumentBuilder> action) {
        return addArgument(name, ArgumentType.CUSTOM, argumentBuilder ->
        {
            var enumValues = Stream.of(type.getEnumConstants())
                    .map(Enum::name)
                    .collect(Collectors.toList());
            argumentBuilder.withSuggestions(enumValues);
            argumentBuilder.withParser(argumentEvent -> Enum.valueOf(type, argumentEvent.arg()));
            action.accept(argumentBuilder);
        });
    }

    default T addEnumArgument(Class<? extends Enum> type, Consumer<ArgumentBuilder> action) {
        return addEnumArgument(type, type.getSimpleName(), action);
    }

    default T addEnumArgument(Class<? extends Enum> type) {
        return addEnumArgument(type, type.getSimpleName(), x -> {
        });
    }

    default T addBoolArgument(String argumentName) {
        return addArgument(argumentName, ArgumentType.BOOL);
    }

    default T addBoolArgument(String argumentName, Consumer<ArgumentBuilder> action) {
        return addArgument(argumentName, ArgumentType.BOOL, action);
    }

    default T addPlayerArgument(String argumentName) {
        return addArgument(argumentName, ArgumentType.PLAYER);
    }

    default T addPlayerArgument(String argumentName, Consumer<ArgumentBuilder> action) {
        return addArgument(argumentName, ArgumentType.PLAYER, action);
    }
}
