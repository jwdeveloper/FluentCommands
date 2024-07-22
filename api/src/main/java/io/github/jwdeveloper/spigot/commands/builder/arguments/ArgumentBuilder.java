package io.github.jwdeveloper.spigot.commands.builder.arguments;

import io.github.jwdeveloper.spigot.commands.data.argumetns.ArgumentType;

public interface ArgumentBuilder {
    ArgumentBuilder withIndex(int index);

    ArgumentBuilder withArgumentName(String name);
    ArgumentBuilder withType(ArgumentType argumentType);
}
