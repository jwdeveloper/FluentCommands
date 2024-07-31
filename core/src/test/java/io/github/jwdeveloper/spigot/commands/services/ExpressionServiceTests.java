package io.github.jwdeveloper.spigot.commands.services;

import io.github.jwdeveloper.spigot.commands.Command;
import io.github.jwdeveloper.spigot.commands.builder.arguments.ArgumentBuilder;
import io.github.jwdeveloper.spigot.commands.common.CommandsTestBase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;

public class ExpressionServiceTests extends CommandsTestBase {

    @Test
    public void should_not_find_sub_command() {

        var command = getCommand();
        var expressionService = command.container().find(ExpressionService.class);
        var args = List.of("1", "2", "3", "4");
        var result = expressionService.getCommandsAndParams(command, args.toArray(new String[0]));

        Assertions.assertEquals(1, result.size());

        var first = result.get(0);
        Assertions.assertEquals(command, first.getKey());
        Assertions.assertEquals(4, first.getValue().length);
    }

    @Test
    public void should_get_proper_commands() {
        var command = getCommand();
        var expressionService = command.container().find(ExpressionService.class);

        var args = List.of("1", "2", "sub1", "3", "sub2", "4", "5");
        var result = expressionService.getCommandsAndParams(command, args.toArray(new String[0]));


        Assertions.assertEquals(3, result.size());

        var first = result.get(0);
        Assertions.assertEquals(command, first.getKey());
        Assertions.assertEquals(2, first.getValue().length);

        var second = result.get(1);
        Assertions.assertEquals(command.child("sub1").get(), second.getKey());
        Assertions.assertEquals(1, second.getValue().length);

        var third = result.get(2);
        Assertions.assertEquals(command.child("sub1").get().child("sub2").get(), third.getKey());
        Assertions.assertEquals(2, third.getValue().length);
    }

    @Test
    public void should_make_arguments_string_when_argument_is_not_defined() {
        var command = getCommand();
        var expressionService = command.container().find(ExpressionService.class);
        var args = List.of("1", "2", "sub1", "3", "sub2", "4", "5");
        var result = expressionService.parse(command, sender, args.toArray(new String[0]));

        Assertions.assertTrue(result.isSuccess());
    }

    private Command getCommand() {
        var command = api.create("test")
                .addArgument("arg1")
                .addArgument("arg2", ArgumentBuilder::withRequired)
                .addSubCommand("sub1", commandBuilder ->
                {
                    commandBuilder.addArgument("arg1");
                    commandBuilder.addSubCommand("sub2", commandBuilder1 ->
                    {
                        commandBuilder1.addArgument("arg1");
                        commandBuilder1.addArgument("arg2");
                    });
                })
                .build();
        return command;
    }

}


