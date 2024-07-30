package io.github.jwdeveloper.spigot.commands.patterns;

import io.github.jwdeveloper.spigot.commands.builder.CommandBuilder;
import io.github.jwdeveloper.spigot.commands.data.ActionResult;
import io.github.jwdeveloper.spigot.commands.data.SuggestionMode;

public class PatternService {

    private final PatternParserService parser;

    public PatternService(PatternParserService patternExpressionService) {
        this.parser = patternExpressionService;
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


            if (argument.hasProperty("dd")) {
                argumentBuilder.withDisplayMode(SuggestionMode.DESCRIPTION);
            }
            if (argument.hasProperty("ds")) {
                argumentBuilder.withDisplayMode(SuggestionMode.SUGGESTIONS);
            }
            if (argument.hasProperty("dt")) {
                argumentBuilder.withDisplayMode(SuggestionMode.TYPE);
            }
            if (argument.hasProperty("dn")) {
                argumentBuilder.withDisplayMode(SuggestionMode.NAME);
            }
            if (argument.hasProperty("de")) {
                //THISPLAY everything
                argumentBuilder.withDisplayMode(SuggestionMode.NAME);
            }
        }
        return ActionResult.success(builder);
    }


}
