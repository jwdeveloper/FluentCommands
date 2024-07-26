package io.github.jwdeveloper.spigot.commands;

import java.util.Optional;

public interface ArgumentTypesRegistry
{
    Optional<ArgumentType> find(String name);

    void add(ArgumentType parsers);

    void remove(ArgumentType parsers);
}
