package io.github.jwdeveloper.spigot.commands.examples;

import io.github.jwdeveloper.spigot.commands.common.CommandsTestBase;
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
        Assertions.assertEquals("<Number>", value.get(0));
    }


    @Test
    public void shouldParseCommand() {
        create("/test <age:Number> <name:Text>").register();
        var commandResult = execute("test", "1", "'", "this", "is", "bad", "idea", "'");

        Assertions.assertTrue(commandResult.isSuccess());

        var value = commandResult.getValue();
        Assertions.assertEquals(1, value.getDouble(0));
        Assertions.assertEquals("this is bad idea", value.getString(1));
    }


    @Test
    public void shouldParseWithError() {
        create("/test <age:Number> <name:Text>").register();
        var commandResult = executeSuggestions("test", "1.ad");

        Assertions.assertTrue(commandResult.isSuccess());

        var value = commandResult.getValue();
        Assertions.assertEquals(1, value.size());
        Assertions.assertEquals("<Number(It's number, not text!)>", value.get(0));
    }


    @Test
    public void should_error_when_no_color_closed() {
        create("/test <age:Number> <name:Text>").register();
        var commandResult = executeSuggestions("test", "1", "'", "my", "name");

        Assertions.assertTrue(commandResult.isSuccess());

        var value = commandResult.getValue();
        Assertions.assertEquals(1, value.size());
        Assertions.assertEquals("<Text>", value.get(0));
    }

}
