package io.github.jwdeveloper.spigot.commands.examples;

import io.github.jwdeveloper.spigot.commands.Command;
import io.github.jwdeveloper.spigot.commands.CommandTests;
import io.github.jwdeveloper.spigot.commands.Commands;
import io.github.jwdeveloper.spigot.commands.CommandsTestBase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class Example1Tests extends CommandsTestBase {


    @Test
    public void shouldParseTab() {
        create("/test <age:Number> <name:Text>").register();
        var commandResult = executeSuggestions("test", "1");

        Assertions.assertTrue(commandResult.isSuccess());

        var value = commandResult.getValue();
        Assertions.assertEquals(1, value.size());
        Assertions.assertEquals("<NUMBER>", value.get(0));
    }


}
