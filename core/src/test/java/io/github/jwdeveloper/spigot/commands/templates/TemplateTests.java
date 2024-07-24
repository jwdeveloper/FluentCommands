package io.github.jwdeveloper.spigot.commands.templates;

import io.github.jwdeveloper.spigot.commands.CommandsTestBase;
import org.junit.jupiter.api.Test;

public class TemplateTests extends CommandsTestBase {

    @Test
    public void shouldParseTempalte() {
        var template = new ExampleTemplate();
        var command = api.create(template).build();

        var result = command.executeCommand(sender, "", "hello", "true", "12");

        var i = 0;
    }
}
