package io.github.jwdeveloper.spigot.commands;

import io.github.jwdeveloper.spigot.commands.builder.CommandBuilder;

public interface CommandsTemplate {

     CommandBuilder templateToBuilder(Object template, CommandBuilder builder);
}
