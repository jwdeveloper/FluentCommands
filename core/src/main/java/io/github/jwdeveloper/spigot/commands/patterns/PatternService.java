package io.github.jwdeveloper.spigot.commands.patterns;

import io.github.jwdeveloper.dependance.Dependance;
import io.github.jwdeveloper.spigot.commands.builder.CommandBuilder;
import io.github.jwdeveloper.spigot.commands.data.ActionResult;
import io.github.jwdeveloper.spigot.commands.data.events.ArgumentSuggestionEvent;
import io.github.jwdeveloper.spigot.commands.functions.ArgumentSuggestions;
import org.checkerframework.checker.units.qual.A;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PatternService {

    private final PatternParser parser;
    private final Patterns patterns;

    public PatternService(PatternParser patternExpressionService, Patterns patterns) {
        this.parser = patternExpressionService;
        this.patterns = patterns;
    }

    public ActionResult<CommandBuilder> getCommandBuilder(Object source, String pattern, CommandBuilder builder) {
        var result = parser.resolve(pattern);
        if (result.isFailed()) {
            return result.cast();
        }
        var commandData = result.getValue();
        var namesChain = commandData.namesChain();
        if (!namesChain.isEmpty()) {
            var isFirst = true;
            for (var name : namesChain) {
                if (name.equals(builder.properties().name()) && isFirst) {
                    continue;
                }
                builder = builder.subCommand(name);
                isFirst = false;
            }
        }
        builder.properties().name(commandData.name());
        for (var argument : commandData.arguments()) {
            var argumentBuilder = builder.argument(argument.name());

            argumentBuilder
                    .withDefaultValue(argument.defaultValue())
                    .withRequired(argument.required())
                    .withType(argument.type());

            if (!argument.suggestions().isEmpty()) {
                argumentBuilder.withSuggestions(argument.suggestions());
            }

            for (var property : argument.properties()) {
                var key = property.getKey();
                var value = property.getValue();

                patterns.applyMapping(source, key, value, argumentBuilder);
            }
        }
        return ActionResult.success(builder);
    }

    private ArgumentSuggestions addSuggestionMethod(Object source,
                                                    String methodName) {
        var type = source.getClass();
        Method method = null;
        try {
            method = type.getDeclaredMethod(methodName.replace("()", ""));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        Method finalMethod = method;
        return event -> {
            try {
                var parameters = event.command().container().resolveParameters(finalMethod);
                var result = finalMethod.invoke(parameters);
                if (result instanceof String[] args) {
                    return ActionResult.success(Arrays.stream(args).toList());
                }

                var res = new ArrayList<String>();
                res.add(result.toString());
                return ActionResult.success(res);
            } catch (Exception e) {
                return ActionResult.failed(e.getMessage());
            }
        };
    }

}
