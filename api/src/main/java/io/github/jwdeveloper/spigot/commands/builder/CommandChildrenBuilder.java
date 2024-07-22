package io.github.jwdeveloper.spigot.commands.builder;

import java.util.function.Consumer;

public interface CommandChildrenBuilder<T> {
    T addSubCommand(Consumer<CommandBuilder> builderConsumer);

    default T addSubCommand(String name, Consumer<CommandBuilder> builderConsumer) {
        return addSubCommand(commandBuilder -> {
            commandBuilder.withName(name);
            builderConsumer.accept(commandBuilder);
        });
    }

    CommandBuilder addSubCommand();
}
