package io.github.jwdeveloper.spigot.commands.builder;

import java.util.function.Consumer;

public interface SubCommandsBuilder<T> {

    CommandBuilder subCommand(String name);

    T addSubCommand(String name, Consumer<CommandBuilder> builderConsumer);

}
