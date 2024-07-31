package io.github.jwdeveloper.spigot.commands.patterns;

import io.github.jwdeveloper.spigot.commands.builder.CommandBuilder;
import io.github.jwdeveloper.spigot.commands.data.ActionResult;

public class PatternService {

    private final PatternParser parser;
    private final Patterns patterns;

    public PatternService(PatternParser patternExpressionService, Patterns patterns) {
        this.parser = patternExpressionService;
        this.patterns = patterns;
    }

    public ActionResult<CommandBuilder> getCommandBuilder(String pattern, CommandBuilder builder) {
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
                    .withSuggestions(argument.suggestions())
                    .withDefaultValue(argument.defaultValue())
                    .withRequired(argument.required())
                    .withType(argument.type());


            for (var property : argument.properties()) {
                var key = property.getKey();
                var value = property.getValue();

                patterns.applyMapping(key, value, argumentBuilder);
            }
        }
        return ActionResult.success(builder);
    }


}
