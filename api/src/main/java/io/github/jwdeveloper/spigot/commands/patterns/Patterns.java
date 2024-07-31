package io.github.jwdeveloper.spigot.commands.patterns;

import io.github.jwdeveloper.spigot.commands.builder.arguments.ArgumentBuilder;

public interface Patterns {
    Patterns mapProperty(String property, PatterMapper mapper);
    boolean applyMapping(String key, String value, ArgumentBuilder argumentBuilder);
}
