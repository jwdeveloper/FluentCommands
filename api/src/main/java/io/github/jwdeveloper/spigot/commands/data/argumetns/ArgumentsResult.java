package io.github.jwdeveloper.spigot.commands.data.argumetns;

import java.util.List;

public class ArgumentsResult {


    private final List<Object> parsedArguments;

    public ArgumentsResult(List<Object> parsedArguments) {
        this.parsedArguments = parsedArguments;
    }

    public Object get(int index) {
        return parsedArguments.get(index);
    }
}
