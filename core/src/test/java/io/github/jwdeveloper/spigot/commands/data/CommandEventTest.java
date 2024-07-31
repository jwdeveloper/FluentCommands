package io.github.jwdeveloper.spigot.commands.data;

import io.github.jwdeveloper.spigot.commands.Command;
import io.github.jwdeveloper.spigot.commands.Commands;
import io.github.jwdeveloper.spigot.commands.common.CommandsTestBase;
import io.github.jwdeveloper.spigot.commands.data.events.CommandEvent;
import io.github.jwdeveloper.spigot.commands.exceptions.ArgumentException;
import io.github.jwdeveloper.spigot.commands.services.ExpressionService;
import org.bukkit.entity.Player;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CommandEventTest extends CommandsTestBase {


    Command command;
    ExpressionService service;

    @Override
    protected void onBefore(Commands commands) {
        command = create("/hello <name:Text> <age:Number> <job:Text>").register();
        service = command.container().find(ExpressionService.class);
    }

    @Test
    public void should_have_arguments_values() {


        var expresion = service.parse(command, sender, "john", "12", "farmer");

        assertTrue(expresion);

        var event = new CommandEvent<Player>(sender, expresion.getValue(), command);


        Assertions.assertTrue(event.hasArgument(0));
        Assertions.assertTrue(event.hasArgument(1));
        Assertions.assertTrue(event.hasArgument(2));


        Assertions.assertEquals(3, event.argumentCount());
        Assertions.assertEquals("john", event.getString(0));
        Assertions.assertEquals("john", event.getString("name"));

        Assertions.assertEquals(12.0d, event.getNumber(1));
        Assertions.assertEquals(12.0d, event.getNumber("age"));

        Assertions.assertEquals("farmer", event.getString(2));
        Assertions.assertEquals("farmer", event.getString("job"));
    }


    @Test
    public void should_throw_when_not_existst() {

        var expresion = service.parse(command, sender, "john", "12", "farmer");
        assertTrue(expresion);
        var event = new CommandEvent<Player>(sender, expresion.getValue(), command);


        Assertions.assertFalse(event.hasArgument(4));
        Assertions.assertFalse(event.hasArgument("not exists"));
        Assertions.assertThrows(ArgumentException.class, () ->
        {
            event.getString(4);
        });
        Assertions.assertThrows(ArgumentException.class, () ->
        {
            event.getString("not exists");
        });
    }

    @Test
    public void should_throw_when_type_mishmash() {

        var expresion = service.parse(command, sender, "john", "12", "farmer");
        assertTrue(expresion);
        var event = new CommandEvent<Player>(sender, expresion.getValue(), command);


        Assertions.assertThrows(ArgumentException.class, () ->
        {
            event.getNumber(0);
        });
        Assertions.assertThrows(ArgumentException.class, () ->
        {
            event.getEntity(1);
        });
        Assertions.assertThrows(ArgumentException.class, () ->
        {
            event.getNumber(2);
        });
    }
}
