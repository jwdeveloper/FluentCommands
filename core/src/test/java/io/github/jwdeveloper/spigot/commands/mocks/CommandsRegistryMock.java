package io.github.jwdeveloper.spigot.commands.mocks;

import io.github.jwdeveloper.spigot.commands.Command;
import io.github.jwdeveloper.spigot.commands.CommandsRegistry;

import java.util.List;

public class CommandsRegistryMock implements CommandsRegistry {
    @Override
    public boolean add(Command command) {
        return false;
    }

    @Override
    public boolean remove(Command fluentCommand) {
        return false;
    }

    @Override
    public List<Command> commands() {
        return List.of();
    }

    @Override
    public void removeAll() {

    }
}
