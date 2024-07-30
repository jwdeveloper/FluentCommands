package io.github.jwdeveloper.spigot.commands.templates;

import io.github.jwdeveloper.spigot.commands.Commands;
import io.github.jwdeveloper.spigot.commands.CommandsTestBase;
import org.junit.jupiter.api.Test;

public class TemplateTests extends CommandsTestBase {

    @Test
    public void shouldParseTempalte() {
        var template = new ExampleTemplate();
        var command = api.create(template).build();


        var result = command.executeCommand(sender, "", "hello", "12");
        System.out.println(result.getValue().output());
        var i = 0;
    }

    @Override
    protected void onBefore(Commands commands) {

    }
}
