package io.github.jwdeveloper.spigot.commands.templates;

import io.github.jwdeveloper.spigot.commands.data.ActionResult;
import io.github.jwdeveloper.spigot.commands.data.argumetns.ArgumentType;

import java.util.ArrayList;
import java.util.List;

public class PatternService {
    public record CommandPattern(String commandName, List<ArgumentPattern> arguments)
    {
    }

    public record ArgumentPattern(String name, String type, boolean required) {

    }

    /**
     * Example pattern:
     * /hello-word <!name:text> <age:number> <last-name:text>
     *
     * When argument behind its name contains ! symbol, it means it is required!
     * @param pattern
     * @return
     */
    public ActionResult<CommandPattern> resolvePattern(String pattern) {
        if (pattern == null || pattern.trim().isEmpty()) {
            return ActionResult.failed("Pattern is empty or null");
        }

        // Split the pattern into parts by spaces
        String[] parts = pattern.trim().split("\\s+");

        // The first part should be the command name
        if (parts.length == 0 || !parts[0].startsWith("/")) {
            return ActionResult.failed("Invalid command pattern format");
        }

        String commandName = parts[0].substring(1); // Remove the leading slash

        List<ArgumentPattern> arguments = new ArrayList<>();

        // Iterate over the remaining parts to parse arguments
        for (int i = 1; i < parts.length; i++) {
            String part = parts[i];
            // Check if the part is an argument
            if (part.startsWith("<") && part.endsWith(">")) {
                String argumentDefinition = part.substring(1, part.length() - 1); // Remove the enclosing <>
                boolean required = false;

                // Check if the argument is required
                if (argumentDefinition.startsWith("!")) {
                    required = true;
                    argumentDefinition = argumentDefinition.substring(1); // Remove the leading !
                }

                // Split the argument definition into name and type
                String[] argParts = argumentDefinition.split(":");
                if (argParts.length != 2) {
                    return ActionResult.failed("Invalid argument format: " + part);
                }

                String name = argParts[0];
                String type = argParts[1];

                // Add the argument to the list
                arguments.add(new ArgumentPattern(name, type, required));
            } else {
                return ActionResult.failed("Invalid argument format: " + part);
            }
        }

        CommandPattern commandPattern = new CommandPattern(commandName, arguments);
        return ActionResult.success(commandPattern);
    }

}
