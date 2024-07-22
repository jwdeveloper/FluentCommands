package io.github.jwdeveloper.spigot.commands;

import io.github.jwdeveloper.spigot.commands.builder.CommandBuilder;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Stream;

public interface CommandsApi {
    CommandBuilder create(String commandName);

    CommandBuilder create();

    void add(Command command);

    void remove(Command command);

    void removeAll();

    List<Command> findAll();

    Stream<Command> findBy(Predicate<Command> predicate);

    Optional<Command> findByName(String commandName);
}
