package io.github.jwdeveloper.spigot.commands.services;

import io.github.jwdeveloper.spigot.commands.Commands;
import io.github.jwdeveloper.spigot.commands.common.CommandsTestBase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CommandParserTest extends CommandsTestBase {
    CommandParser parser;

    @Override
    protected void onBefore(Commands commands) {
        parser = new CommandParser();
    }

    @Test
    public void should_has_all_values_parsed() {
        var command = create("/test <age:Number> <name:Text>").register();
        var result = parser.parseCommand(command, sender, "123", "\"", "john", "is", "my", "brother", "\"");

        assertTrue(result);
        var value = result.getValue();
        assertEquals(2, value.getArguments().size());

        var arg1 = value.getArgument(0);
        Assertions.assertFalse(arg1.isDefaultValue());
        assertEquals(123.0d, arg1.getValue());

        var arg2 = value.getArgument(1);
        Assertions.assertFalse(arg2.isDefaultValue());
        assertEquals("john is my brother", arg2.getValue());

        Assertions.assertTrue(value.getLastResolvedArgument().isSuccess());
    }

    @Test
    public void should_has_first_value_parsed() {
        var command = create("/test <age:Number> <name:Text>").register();
        var result = parser.parseCommand(command, sender, "123");


        assertTrue(result);
        var value = result.getValue();
        assertEquals(2, value.getArguments().size());

        var arg1 = value.getArgument(0);
        Assertions.assertFalse(arg1.isDefaultValue());
        assertEquals(123.0d, arg1.getValue());

        var arg2 = value.getArgument(1);
        Assertions.assertTrue(arg2.isDefaultValue());
        assertEquals("", arg2.getValue());

        Assertions.assertTrue(value.getLastResolvedArgument().isSuccess());
    }

    @Test
    public void should_has_all_default_values() {
        var command = create("/test <age:Number> <name:Text>").register();
        var result = parser.parseCommand(command, sender);


        assertTrue(result);
        var value = result.getValue();
        assertEquals(2, value.getArguments().size());

        var arg1 = value.getArgument(0);
        Assertions.assertTrue(arg1.isDefaultValue());
        assertEquals(1.0d, arg1.getValue());

        var arg2 = value.getArgument(1);
        Assertions.assertTrue(arg2.isDefaultValue());
        assertEquals("", arg2.getValue());

        Assertions.assertTrue(value.getLastResolvedArgument().isFailed());
    }
}
