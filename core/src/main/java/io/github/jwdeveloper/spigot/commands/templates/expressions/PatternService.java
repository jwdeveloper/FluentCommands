package io.github.jwdeveloper.spigot.commands.templates.expressions;

import io.github.jwdeveloper.dependance.api.DependanceContainer;
import io.github.jwdeveloper.spigot.commands.Commands;
import io.github.jwdeveloper.spigot.commands.CommandsRegistry;
import io.github.jwdeveloper.spigot.commands.builder.CommandBuilder;
import io.github.jwdeveloper.spigot.commands.data.ActionResult;

public class PatternService {

    private final PatternParserService parser;
    public PatternService(PatternParserService patternExpressionService) {
        this.parser = patternExpressionService;
    }

    public ActionResult<CommandBuilder> getCommandBuilder(String pattern, CommandBuilder builder)
    {
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
        }
        return ActionResult.success(builder);
    }


}
