package io.github.jwdeveloper.spigot.commands.data.expressions;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CommandExpression {


    private String[] rawValue;

    private List<CommandNode> commandNodes = new ArrayList<>();

    public CommandNode invokedCommand()
    {
        return commandNodes.get(commandNodes.size() - 1);
    }


}
