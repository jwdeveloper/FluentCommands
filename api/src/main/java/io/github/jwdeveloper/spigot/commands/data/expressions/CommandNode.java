package io.github.jwdeveloper.spigot.commands.data.expressions;

import io.github.jwdeveloper.spigot.commands.Command;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CommandNode {
    private Command command;
    private String[] raw;
    private List<ArgumentNode> arguments = new ArrayList<>();

    public ArgumentNode getArgument(int index) {
        return arguments.get(index);
    }
}
