package io.github.jwdeveloper.spigot.commands.builder;

import java.util.function.Consumer;

public interface CommandChildrenBuilder<T> {
    T addChildren(Consumer<CommandBuilder> builderConsumer);

    CommandBuilder addChildren();
}
