package io.github.jwdeveloper.spigot.commands;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class FluentArgumentTypesRegistry implements ArgumentTypesRegistry {

    private final Map<String, ArgumentType> parsers = new HashMap<>();

    @Override
    public Optional<ArgumentType> find(String name) {
        return Optional.ofNullable(parsers.get(name.toLowerCase()));
    }

    @Override
    public void add(ArgumentType parser) {
        parsers.put(parser.name().toLowerCase(), parser);
    }

    @Override
    public void remove(ArgumentType parser) {
        parsers.remove(parser.name());
    }
}
