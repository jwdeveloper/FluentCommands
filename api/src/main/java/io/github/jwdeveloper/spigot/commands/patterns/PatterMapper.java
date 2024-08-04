package io.github.jwdeveloper.spigot.commands.patterns;

import io.github.jwdeveloper.spigot.commands.builder.arguments.ArgumentBuilder;

import java.util.function.BiConsumer;

public interface PatterMapper {

    void map(String value, ArgumentBuilder builder, Object source) throws Exception;
}
