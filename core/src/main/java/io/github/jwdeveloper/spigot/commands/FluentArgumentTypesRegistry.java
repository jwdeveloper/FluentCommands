package io.github.jwdeveloper.spigot.commands;

import io.github.jwdeveloper.spigot.commands.argumetns.ArgumentType;
import io.github.jwdeveloper.spigot.commands.argumetns.ArgumentTypeBuilder;
import io.github.jwdeveloper.spigot.commands.argumetns.ArgumentTypesRegistry;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class FluentArgumentTypesRegistry implements ArgumentTypesRegistry {

    private final Map<String, ArgumentType> parsers = new HashMap<>();

    @Override
    public List<ArgumentType> findAll() {
        return parsers.values().stream().toList();
    }

    @Override
    public Optional<ArgumentType> findByName(String name) {
        return Optional.ofNullable(parsers.get(name.toLowerCase()));
    }

    @Override
    public ArgumentTypeBuilder create(String name) {
        return null;
    }

    @Override
    public void register(ArgumentType parser) {
        parsers.put(parser.name().toLowerCase(), parser);
    }

    @Override
    public void unRegister(ArgumentType parser) {
        parsers.remove(parser.name());
    }
}
