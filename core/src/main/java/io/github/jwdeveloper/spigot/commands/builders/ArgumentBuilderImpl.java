package io.github.jwdeveloper.spigot.commands.builders;

import io.github.jwdeveloper.spigot.commands.builder.arguments.ArgumentBuilder;
import io.github.jwdeveloper.spigot.commands.data.argumetns.ArgumentProperties;
import io.github.jwdeveloper.spigot.commands.data.argumetns.ArgumentType;

public class ArgumentBuilderImpl implements ArgumentBuilder {
    @Override
    public ArgumentBuilder withIndex(int index) {
        return null;
    }

    @Override
    public ArgumentBuilder withArgumentName(String name) {
        return null;
    }

    @Override
    public ArgumentBuilder withType(ArgumentType argumentType) {
        return null;
    }

    public ArgumentProperties build() {
        return new ArgumentProperties();
    }
}
