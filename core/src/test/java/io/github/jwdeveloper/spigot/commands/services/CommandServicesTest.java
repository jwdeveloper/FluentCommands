package io.github.jwdeveloper.spigot.commands.services;

import io.github.jwdeveloper.spigot.commands.Command;
import io.github.jwdeveloper.spigot.commands.data.CommandTarget;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CommandServicesTest {
    private CommandServices commandService;
    private Command mainCommand;
    private Command subCommand;
    private Command nestedSubCommand;

    @BeforeEach
    public void setUp() {
        commandService = new CommandServices(null, null);
        mainCommand = mock(Command.class);
        subCommand = mock(Command.class);
        nestedSubCommand = mock(Command.class);

        when(mainCommand.name()).thenReturn("say");
        when(subCommand.name()).thenReturn("hello");
        when(nestedSubCommand.name()).thenReturn("John");
    }

    @Test
    public void testTargetedCommand_NoArguments() {
        String[] args = {};

        when(mainCommand.children()).thenReturn(Collections.emptyList());

        CommandTarget result = commandService.targetedCommand(mainCommand, args);

        assertEquals(mainCommand, result.getCommand());
        assertArrayEquals(args, result.getArguments());
    }

    @Test
    public void testTargetedCommand_NoChildren() {
        String[] args = {"hello"};

        when(mainCommand.children()).thenReturn(Collections.emptyList());

        CommandTarget result = commandService.targetedCommand(mainCommand, args);

        assertEquals(mainCommand, result.getCommand());
        assertArrayEquals(args, result.getArguments());
    }

    @Test
    public void testTargetedCommand_NoMatchingSubCommand() {
        String[] args = {"goodbye"};

        when(mainCommand.children()).thenReturn(List.of(subCommand));
        when(subCommand.name()).thenReturn("hello");

        CommandTarget result = commandService.targetedCommand(mainCommand, args);

        assertEquals(mainCommand, result.getCommand());
        assertArrayEquals(args, result.getArguments());
    }

    @Test
    public void testTargetedCommand_MatchingSubCommand() {
        String[] args = {"hello"};

        when(mainCommand.children()).thenReturn(List.of(subCommand));
        when(subCommand.name()).thenReturn("hello");

        CommandTarget result = commandService.targetedCommand(mainCommand, args);

        assertEquals(subCommand, result.getCommand());
        assertArrayEquals(new String[]{}, result.getArguments());
    }

    @Test
    public void testTargetedCommand_RecursiveSubCommand() {
        String[] args = {"hello", "John"};

        when(mainCommand.children()).thenReturn(List.of(subCommand));
        when(subCommand.name()).thenReturn("hello");
        when(subCommand.children()).thenReturn(List.of(nestedSubCommand));
        when(nestedSubCommand.name()).thenReturn("John");

        CommandTarget result = commandService.targetedCommand(mainCommand, args);

        assertEquals(nestedSubCommand, result.getCommand());
        assertArrayEquals(new String[]{}, result.getArguments());
    }

    @Test
    public void testTargetedCommand_WithParameters() {
        String[] args = {"hello", "John", "param1", "param2"};

        when(mainCommand.children()).thenReturn(List.of(subCommand));
        when(subCommand.name()).thenReturn("hello");
        when(subCommand.children()).thenReturn(List.of(nestedSubCommand));
        when(nestedSubCommand.name()).thenReturn("John");

        CommandTarget result = commandService.targetedCommand(mainCommand, args);

        assertEquals(nestedSubCommand, result.getCommand());
        assertArrayEquals(new String[]{"param1", "param2"}, result.getArguments());
    }
}
