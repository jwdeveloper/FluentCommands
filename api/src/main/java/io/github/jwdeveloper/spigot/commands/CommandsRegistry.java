package io.github.jwdeveloper.spigot.commands;

import java.util.List;

public interface CommandsRegistry {
    boolean add(Command command);

    boolean remove(Command fluentCommand);

    List<Command> commands();

    void removeAll();
}
