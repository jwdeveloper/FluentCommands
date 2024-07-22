package io.github.jwdeveloper.spigot.commands.data;

import io.github.jwdeveloper.spigot.commands.Command;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CommandTarget {
    private Command command;
    private String[] arguments;
}
