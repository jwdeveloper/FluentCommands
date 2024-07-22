package io.github.jwdeveloper.spigot.commands.builder;

import io.github.jwdeveloper.dependance.implementation.DependanceContainerBuilder;
import io.github.jwdeveloper.spigot.commands.Command;

import java.util.function.Consumer;

public interface CommandBuilder extends
        CommandEventsBuilder<CommandBuilder>,
        CommandPropsBuilder<CommandBuilder>,
        CommandArgumentsBuilder<CommandBuilder>,
        CommandChildrenBuilder<CommandBuilder> {

    Command buildAndRegister();

    Command build();
}
