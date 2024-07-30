package io.github.jwdeveloper.spigot.commands;

import io.github.jwdeveloper.spigot.commands.argumetns.ArgumentTypes;
import io.github.jwdeveloper.spigot.commands.builder.CommandBuilder;
import io.github.jwdeveloper.spigot.commands.patterns.Patterns;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Stream;


/**
 * Interface for managing commands in a Bukkit plugin.
 */
public interface Commands {

    Patterns patterns();

    /**
     * Argument types registry object
     *
     * @return a new instance of {@link ArgumentTypes}.
     */
    ArgumentTypes argumentTypes();

    /**
     * Creates a new command builder for a command with the specified pattern.
     *
     * @param pattern the pattern for the command.
     * @return a new instance of {@link CommandBuilder}.
     */
    CommandBuilder create(String pattern);

    /**
     * Creates a new command builder for a command using the specified template object.
     *
     * @param templateObject the template object for the command.
     * @return a new instance of {@link CommandBuilder}.
     */
    CommandBuilder create(Object templateObject);

    /**
     * Adds the specified command to the command manager.
     *
     * @param command the command to be added.
     */
    void register(Command command);

    /**
     * Removes the specified command from the command manager.
     *
     * @param command the command to be removed.
     */
    void unregister(Command command);

    /**
     * Removes all commands from the command manager.
     */
    void removeAll();

    /**
     * Finds and returns all commands managed by the command manager.
     *
     * @return a list of all commands.
     */
    List<Command> findAll();

    /**
     * Finds and returns commands that match the specified predicate.
     *
     * @param predicate the predicate to filter commands.
     * @return a stream of commands that match the predicate.
     */
    Stream<Command> findBy(Predicate<Command> predicate);

    /**
     * Finds and returns a command with the specified name.
     *
     * @param commandName the name of the command.
     * @return an optional containing the command if found, or empty if not found.
     */
    Optional<Command> findByName(String commandName);
}