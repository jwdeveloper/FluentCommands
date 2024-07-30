package io.github.jwdeveloper.spigot.commands.argumetns;

import java.util.List;
import java.util.Optional;

public interface ArgumentTypesRegistry {
    List<ArgumentType> findAll();

    Optional<ArgumentType> findByName(String name);

    ArgumentTypeBuilder create(String name);

    void register(ArgumentType parsers);

    void unRegister(ArgumentType parsers);
}
