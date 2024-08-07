package io.github.jwdeveloper.spigot.commands.templates;

import io.github.jwdeveloper.spigot.commands.Commands;
import io.github.jwdeveloper.spigot.commands.common.CommandsTestBase;
import org.junit.jupiter.api.Test;

public class TemplateTests extends CommandsTestBase {

    @Test
    public void shouldParseTempalte() {
        var template = new ExampleTemplate();
        var command = api.create(template).build();

        var result = command.executeCommand(sender, "", "hello", "12");

    }

    @Override
    protected void onBefore(Commands commands) {

    }
}
