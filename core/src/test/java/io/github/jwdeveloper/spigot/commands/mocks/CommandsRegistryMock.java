package io.github.jwdeveloper.spigot.commands.mocks;

import io.github.jwdeveloper.spigot.commands.Command;
import io.github.jwdeveloper.spigot.commands.CommandsRegistry;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CommandsRegistryMock implements CommandsRegistry {


    List<Command> commands = new ArrayList<>();

    @Override
    public boolean add(Command command) {
        return commands.add(command);
    }

    @Override
    public boolean remove(Command fluentCommand) {
        return commands.remove(fluentCommand);
    }

    @Override
    public List<Command> commands() {
        return commands;
    }

    @Override
    public void removeAll() {
        commands.clear();
    }
}
