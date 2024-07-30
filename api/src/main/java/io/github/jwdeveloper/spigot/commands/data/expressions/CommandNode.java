package io.github.jwdeveloper.spigot.commands.data.expressions;

import io.github.jwdeveloper.spigot.commands.Command;
import io.github.jwdeveloper.spigot.commands.data.ActionResult;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Data
public class CommandNode {
    private Command command;
    private String[] raw;
    private List<ArgumentNode> arguments = new ArrayList<>();

    public ActionResult<ArgumentNode> getLastResolvedArgument() {
        ArgumentNode argumentNode = null;
        for (var arg : arguments) {
            if (arg.isDefaultValue()) {
                continue;
            }
            argumentNode = arg;
        }

        if (argumentNode == null) {
            return ActionResult.failed();
        }

        return ActionResult.success(argumentNode);
    }


    public boolean hasEnded() {
        if (arguments.isEmpty()) {
            return true;
        }
        var lastArg = arguments.get(arguments.size() - 1);
        return lastArg.isEnd();
    }

    public ArgumentNode getArgument(int index) {
        return arguments.get(index);
    }

}
