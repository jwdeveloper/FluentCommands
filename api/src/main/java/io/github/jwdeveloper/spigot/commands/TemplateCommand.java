package io.github.jwdeveloper.spigot.commands;

import io.github.jwdeveloper.spigot.commands.builder.CommandBuilder;

public interface TemplateCommand {

     CommandBuilder templateToBuilder(Object template, CommandBuilder builder);
}
