package io.github.jwdeveloper.spigot.commands.builder;

import io.github.jwdeveloper.spigot.commands.Command;

public interface CommandBuilder extends
        CommandEventsBuilder<CommandBuilder>,
        CommandPropsBuilder<CommandBuilder>,
        CommandArgumentsBuilder<CommandBuilder>,
        SubCommandsBuilder<CommandBuilder> {

    Command register();

    Command build();
}
